import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivate} from "../../service/axios.service";
import BranchList from "../../components/branch/branch-list";
import {BranchDetails} from "../../components/type/types";
const BranchPage = () => {

    const [branches, setBranches] = useState([]);

    const fetchData = () => {
        const arr: Array<BranchDetails> = [
            {
                "branchId": 1,
                "branchName": "Fairfield",
                "branchManagerId": 10,
                "address": {
                    "id": 1,
                    "city": "Fairfield",
                    "state": "Iowa",
                    "street1": "1000 N 4th St",
                    "street2": "",
                    "zip": "52557"
                }
            }
        ]
        setBranches(arr);
        // try {
        //     axiosPrivate.get("/branches").then((res) => {
        //         setBranches(res.data);
        //     })
        // } catch (err) {
        //     console.error(err);
        // }
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