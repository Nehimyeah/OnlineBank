import DashboardLayout from "../../components/layouts/dashboard-layout";
import ManagersList from "../../components/managers/ManagersList";
import {axiosPrivate} from "../../service/axios.service";
import {useEffect, useState} from "react";
import {UserDetails} from "../../components/type/types";

export type UsersList = Array<UserDetails>;

const HomePage = () => {

    const [users, setUsers] = useState([]);

    const fetchData = () => {
        try {
             axiosPrivate.get("/users/teams").then((res) => {
                 setUsers(res.data);
                 console.log(users)
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
            <ManagersList users={users}/>
        </DashboardLayout>
    )
}

export default HomePage;