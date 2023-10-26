import {useParams} from "react-router-dom";
import {Transactions} from "../type/types";
import Button from "../elements/button";
import {axiosPrivateReport} from "../../service/axios.service";
import {toast} from "react-toastify";
const TransactionsList = (props:{transactions: Array<Transactions>, accountNumber: string | undefined} ) => {

    const {id} = useParams();
    const generateReport = () => {
        try {
            axiosPrivateReport.get(`/report/bank-statement/${id}`, {
                responseType: 'arraybuffer'
            }).then((res) => {
                toast("Downloaded successfully")
                let blob = new Blob([res.data], { type: 'application/pdf' })
                let link = document.createElement('a')
                link.href = window.URL.createObjectURL(blob)
                link.download = `Report-${id}.pdf`
                link.click()
            })
        } catch (err) {
            toast("Error while downloading report")
            console.error(err);
        }
    }

    return (
        <>
            <div className="w-full h-screen bg-gray-100">
                <div className=" mx-auto sm:px-6 lg:px-8">
                    <div className="flex flex-col">
                        <div className="-mb-2 py-4 flex justify-between mt-5">
                            <h1 className="text-3xl font-bolder leading-tight text-gray-900">Transactions List of Account number: {props.accountNumber}</h1>
                            <Button onClick={generateReport} value="Generate report" className="p-2 h-12 w-60" />
                        </div>
                        <div className="-my-2 py-2 sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
                            <div className="align-middle inline-block w-full shadow overflow-x-auto sm:rounded-lg border-b border-gray-200">
                                <table className="min-w-full">
                                    <thead>
                                    <tr className="bg-gray-50 border-b border-gray-200 text-xs leading-4 text-gray-500 uppercase tracking-wider">
                                        <th className="px-6 py-3 text-left font-medium">
                                            Transaction type
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Date
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Previous balance
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Info
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody className="bg-white">
                                    {props.transactions.map((account) => (
                                        <tr>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                                                <div className="text-sm leading-5 text-gray-900">
                                                    {account.transactionType}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                                                <div className="text-sm leading-5 text-gray-900 capitalize">
                                                    {account.createdDate.substring(0,10)}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">

                                                <div className="flex items-center">
                                                    <div className="flex-shrink-0">
                                                        <div className="text-sm leading-5 text-gray-900">
                                                            $ {account.previousBalance}
                                                        </div>
                                                    </div>
                                                    <div className="ml-4">
                                                        <div className="text-sm leading-5 font-medium text-gray-900">
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                                                <div className="text-sm leading-5 text-gray-900">
                                                    {account.info}
                                                </div>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default TransactionsList;