import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivateBranch as axiosPrivate} from "../../service/axios.service";
import Accounts from "../../components/accounts/AccountsList";
const AccountsList = () => {

    const [accounts, setBranches] = useState([]);

    const fetchData = () => {
        try {
            axiosPrivate.get("/accounts").then((res) => {
                setBranches(res.data);
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