import DashboardLayout from "../../components/layouts/dashboard-layout";
import ManagersList from "../../components/managers/ManagersList";
import {axiosPrivate} from "../../service/axios.service";
import {useEffect, useState} from "react";
import {UserDetails} from "../../components/type/types";

export type UsersList = Array<UserDetails>;

const HomePage = () => {

    const [users, setUsers] = useState<UserDetails[]>([]);

    const fetchData = () => {
        try {
             axiosPrivate.get("/users/teams").then((res) => {
                 setUsers(res.data);
            })
        } catch (err) {
            console.error(err);
        }
    }

    const enableDisable = async (userId: number):Promise<void> => {
        const user = users.find(user => user.id === userId);
        let val =user?.active ? 'disable' : 'enable'
        try {
            await axiosPrivate.put(`/users/${userId}/${val}`)

            const newUsers:UserDetails[] = users.map(user => {
                if (user.id == userId) {
                    user.active = !user.active
                }
                return user
            })
            setUsers(newUsers)
        } catch (err) {
            console.log(err)
        }

    }

    useEffect(() => {
        fetchData()
    }, []);


    return (
        <DashboardLayout>
            <ManagersList users={users} onUserChanged={enableDisable}/>
        </DashboardLayout>
    )
}

export default HomePage;