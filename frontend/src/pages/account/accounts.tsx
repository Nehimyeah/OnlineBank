import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivateBank} from "../../service/axios.service";
import Accounts from "../../components/accounts/AccountsList";
import {Account} from "../../components/type/types";
import {toast} from "react-toastify";
const AccountsList = () => {

    const [accounts, setBranches] = useState([]);
    const [totalBalance, setTotalBalance] = useState(0);

    const fetchData = () => {
        try {
            axiosPrivateBank.get(`/account/list`).then((res) => {
                setBranches([]);
                if (res.data) {
                    const sum = res.data.reduce( (a:number,b: Account) => {
                        return a + b.balance
                    },0)
                    setBranches(res.data);
                    setTotalBalance(sum);
                }
            })
        } catch (err) {
            console.error(err);
        }
    }
    const toggleAccountStatus = async (accountNumber:string, status: string):Promise<void> => {
        console.log("here")
        try {
            axiosPrivateBank.post(`/account/status/${accountNumber}`, {
                status: status === 'ACTIVE' ? 'DISABLED' : 'ACTIVE'
            }).then((res) => {
                fetchData();
            })
        } catch (err) {
            console.error(err);
            toast("Error while changing status")
        }
    }

    useEffect(() => {
        fetchData()
    }, []);

    return (
        <DashboardLayout>
            <Accounts accounts={accounts} totalBalance={totalBalance} onStatusChange={toggleAccountStatus} />
        </DashboardLayout>
    )
}

export default AccountsList;