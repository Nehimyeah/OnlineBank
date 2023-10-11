import { Link } from "react-router-dom";
import {UserDetails} from "../type/types";
const ManagersList = (props) => {

    return (
        <>
            <div className="w-full h-screen bg-gray-100">
                <div className=" mx-auto sm:px-6 lg:px-8">
                    <div className="flex flex-col">

                        <div className="-mb-2 py-4 flex flex-wrap justify-between flex-grow">
                            <div className="mb-4">
                                <h1 className="text-3xl font-bolder leading-tight text-gray-900 mt-5">Managers List</h1>
                            </div>
                            <div className="flex items-center py-2">
                                <Link to={"/managers/create"}
                                   className="inline-block px-4 py-2 border border-transparent text-sm leading-5 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-500 focus:outline-none focus:shadow-outline">
                                    Create Manager
                                </Link>
                            </div>
                        </div>
                        <div className="-my-2 py-2 sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
                            <div className="align-middle inline-block w-full shadow overflow-x-auto sm:rounded-lg border-b border-gray-200">
                                <table className="min-w-full">
                                    <thead>
                                    <tr className="bg-gray-50 border-b border-gray-200 text-xs leading-4 text-gray-500 uppercase tracking-wider">
                                        <th className="px-6 py-3 text-left font-medium">
                                            Firstname
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Lastname
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Email
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                            Status
                                        </th>
                                        <th className="px-6 py-3 text-left font-medium">
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody className="bg-white">
                                    {props.users.map((user:UserDetails) => (
                                        <tr>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                                                <div className="text-sm leading-5 text-gray-900">
                                                    {user.firstName}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">

                                                <div className="flex items-center">
                                                    <div className="flex-shrink-0 h-10 w-10">
                                                        <div className="text-sm leading-5 text-gray-900">
                                                            {user.lastName}
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
                                                    {user.email}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                                                {user.active ?
                                                    (<span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">Active</span>)
                                                 : (<span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-500 text-white">Disabled</span>)

                                                }
                                            </td>
                                            <td className="px-6 py-4 whitespace-no-wrap text-right border-b border-gray-200 text-sm leading-5 font-medium">
                                                <a href="#"
                                                   className="text-indigo-600 hover:text-indigo-900 focus:outline-none focus:underline" >
                                                    Delete
                                                </a>
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

export default ManagersList;