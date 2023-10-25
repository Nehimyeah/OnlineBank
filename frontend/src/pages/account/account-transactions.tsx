import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useEffect, useState} from "react";
import {axiosPrivateBank} from "../../service/axios.service";
import {useParams} from "react-router-dom";
import TransactionsList from "../../components/accounts/TransactionsList";

const AccountTransactions = () => {

    const [accounts, setAccounts] = useState([]);

    const {id} = useParams();

    const fetchData = () => {
        console.log(id)
        try {
            axiosPrivateBank.get(`transaction/list/${id}`).then((res) => {
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
            <TransactionsList accountNumber={id} transactions={accounts} />
        </DashboardLayout>
    )
}

export default AccountTransactions;