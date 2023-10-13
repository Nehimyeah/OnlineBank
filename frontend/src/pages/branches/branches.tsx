import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivateBranch as axiosPrivate} from "../../service/axios.service";
import BranchList from "../../components/branch/branch-list";
const BranchPage = () => {

    const [branches, setBranches] = useState([]);

    const fetchData = () => {
        try {
            axiosPrivate.get("/branches").then((res) => {
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
            <BranchList branches={branches}/>
        </DashboardLayout>
    )
}

export default BranchPage;