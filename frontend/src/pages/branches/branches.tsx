import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivate} from "../../service/axios.service";
const BranchPage = () => {

    const [branches, setUsers] = useState([]);

    const fetchData = () => {
        try {
            axiosPrivate.get("/branch/branches").then((res) => {
                setUsers(res.data);
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
            branches
        </DashboardLayout>
    )
}

export default BranchPage;