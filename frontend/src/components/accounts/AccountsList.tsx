import { Link } from "react-router-dom";
import {Account} from "../type/types";
import {useSelector} from "react-redux";
import {getUserRole} from "../../app/authSlice";
const Accounts = (props:{accounts: Array<Account>, totalBalance?: number} ) => {
    let role = useSelector(getUserRole);

    return (
        <>
            <div className="w-full h-screen bg-gray-100">
                <div className=" mx-auto sm:px-6 lg:px-8">
                    <div className="flex flex-col">
                        <div className="-mb-2 py-4 flex flex-wrap justify-between flex-grow">
                            <div className="mb-4">
                                <h1 className="text-3xl font-bolder leading-tight text-gray-900 mt-5">Accounts List</h1>
                            </div>
                            {(role === 'CUSTOMER') &&
                                (
                                    <div className="flex flex-row-reverse items-center py-2 gap-2">
                                        <Link to={"/accounts/create"}
                                              className="inline-block px-4 py-2 border border-transparent text-sm leading-5 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-500 focus:outline-none focus:shadow-outline">
                                            Create Account
                                        </Link>
                                        <Link to={"/accounts/withdraw"}
                                              className="inline-block px-4 py-2 border border-transparent text-sm leading-5 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-500 focus:outline-none focus:shadow-outline">
                                            Withdraw
                                        </Link>
                                        <Link to={"/accounts/deposit"}
                                              className="inline-block px-4 py-2 border border-transparent text-sm leading-5 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-500 focus:outline-none focus:shadow-outline">
                                            Deposit
                                        </Link>
                                        <Link to={"/accounts/transfer"}
                                              className="inline-block px-4 py-2 border border-transparent text-sm leading-5 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-500 focus:outline-none focus:shadow-outline">
                                            Transfer
                                        </Link>
                                    </div>
                                )
                            }
                        </div>
                        <div className="-my-2 py-2 sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
                            <div className="align-middle inline-block w-full shadow overflow-x-auto sm:rounded-lg border-b border-gray-200">
                                <table className="min-w-full">
                                    <thead>
                                    <tr className="bg-gray-50 border-b border-gray-200 text-xs leading-4 text-gray-500 uppercase tracking-wider">
                                        <th className="px-6 py-3 text-left font-medium">
                                            Account number
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Account type
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Status
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Balance
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody className="bg-white">
                                    {props.accounts.map((account) => (
                                        <tr>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                                                <div className="text-sm leading-5 text-gray-900">
                                                    {account.accountNumber}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                                                <div className="text-sm leading-5 text-gray-900 capitalize">
                                                    {account.accountType}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">

                                                <div className="flex items-center">
                                                    <div className="flex-shrink-0 h-10 w-10">
                                                        <div className="text-sm leading-5 text-gray-900">
                                                            {account.accountStatus}
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
                                                    $ {account.balance}
                                                </div>
                                            </td>
                                            <td>
                                                <Link to={`/accounts/${account.accountNumber}/transactions`} className="text-indigo-600 hover:text-indigo-900 focus:outline-none focus:underline">Transactions</Link>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                                <p className="m-2">Total balance: ${props.totalBalance}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Accounts;