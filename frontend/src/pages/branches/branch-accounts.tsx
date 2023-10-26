import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivateBank, axiosPrivateBranch} from "../../service/axios.service";
import Accounts from "../../components/accounts/AccountsList";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";
const BranchAccounts = () => {

    const [accounts, setBranches] = useState([]);
    const [totalBalance, setTotalBalance] = useState(0);
    const {id} = useParams();

    const fetchData = () => {
        try {
            axiosPrivateBranch.get(`/branches/${id}/accounts`).then((res) => {
                setBranches([]);
                if (res.data) {
                    setBranches(res.data.accountList);
                    setTotalBalance(res.data.total)
                }
            })
        } catch (err) {
            console.error(err);
        }
    }

    const toggleAccountStatus = async (accountNumber:string, status: string):Promise<void> => {
        console.log("here")
        try {
            axiosPrivateBank.put(`/account/status/${accountNumber}`, {
                status: status
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
            <Accounts onStatusChange={toggleAccountStatus} accounts={accounts} totalBalance={totalBalance}/>
        </DashboardLayout>
    )
}

export default BranchAccounts;