import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivateBranch as axiosPrivate} from "../../service/axios.service";

const BranchAccounts = () => {

    const [accounts, setAccounts] = useState([]);

    const fetchData = () => {
        try {
            axiosPrivate.get("branches/1/accounts").then((res) => {
                setAccounts(res.data);
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
            <div>
                hello
            </div>
        </DashboardLayout>
    )
}

export default BranchAccounts;