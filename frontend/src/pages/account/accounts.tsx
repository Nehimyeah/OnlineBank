import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivateBank} from "../../service/axios.service";
import Accounts from "../../components/accounts/AccountsList";
const AccountsList = () => {

    const [accounts, setBranches] = useState([]);
    const [totalBalance, setTotalBalance] = useState(0);

    const fetchData = () => {
        try {
            axiosPrivateBank.get(`/account/list`).then((res) => {
                setBranches([]);
                if (res.data) {
                    setBranches(res.data);
                }
            })
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        fetchData()
    }, []);

    return (
        <DashboardLayout>
            <Accounts accounts={accounts}/>
        </DashboardLayout>
    )
}

export default AccountsList;