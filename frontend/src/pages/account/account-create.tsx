import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import {useEffect, useState} from "react";
import { axiosPrivateBranch, axiosPrivateBank } from "../../service/axios.service";
import FormFieldError from "../../components/auth/form/form-field-error";
import Button from "../../components/elements/button";
import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useNavigate} from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {BranchDetails, UserDetails} from "../../components/type/types";
import {branchSchema} from "../../components/mixins/userRelatedFunctions";

const CreateAccount = () => {
    const [branches,setBranches] = useState([])
    const [accountType, setAccountType] = useState("checking");
    const fetchData = () => {
        try {
            axiosPrivateBranch.get("/branches").then((res) => {
                const tempBranches = res.data.map((el) => {
                    return {
                        label: el.branchName,
                        value: el.branchId
                    }
                })
                setBranches(tempBranches);
                console.log(tempBranches)
                setSelectedOption(tempBranches[0].value.toString())
            })
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        fetchData()
    }, []);

    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [success, setSuccess] = useState<boolean>(false);
    const [submissionErrors, setSubmissionErrors] = useState<string[]>([]);
    const [selectedOption, setSelectedOption] = useState(undefined);
    const navigate = useNavigate();

    const notify = () => toast("Account has successfully been created!");


    const onsubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);

        console.log(selectedOption)

        const data = {
            branchId: parseInt(selectedOption),
            accountType: accountType
        }

        try {
            await axiosPrivateBank.post("/account/create", data)
                .then(() => {
                    notify();
                    setTimeout(() => {
                        navigate("/branches")
                    }, 1000);
                })
                .finally(() => {
                    setIsLoading(false);
                });

            setSuccess(true);
        } catch (error) {
            setIsLoading(false);
            console.error(error);
        }
    };

    // @ts-ignore
    return (
        <DashboardLayout>
            <form
                noValidate
                onSubmit={onsubmit}
                className="bg-white/20 container mx-auto mt-5"
            >
                <h1 className="text-center font-bold text-3xl mb-4 text-indigo-900">
                    Create Account
                </h1>
                {submissionErrors.length > 0 ? (
                    <ul className="p-4 border-[1px] border-red-500 rounded-xl mb-5 max-w-sm flex items-center justify-center flex-col space-y-3">
                        {submissionErrors.map((err, i) => (
                            <li key={i} className="text-red-500 font-bold">
                                {err}
                            </li>
                        ))}
                    </ul>
                ) : (
                    ""
                )}
                <div className="flex flex-col space-y-6">
                    <div className="flex flex-col space-y-1">
                        <div className="radio-btn-container">
                            <div
                                className="radio-btn"
                                onClick={() => {
                                    setAccountType("checking");
                                }}
                            >
                                <input
                                    type="radio"
                                    value={accountType}
                                    name="accountType"
                                    checked={accountType == "checking"}
                                />
                                Checking
                            </div>
                            <div
                                className="radio-btn"
                                onClick={() => {
                                    setAccountType("loan");
                                }}
                            >
                                <input
                                    type="radio"
                                    value={accountType}
                                    name="accountType"
                                    checked={accountType == "loan"}
                                />
                                Loan
                            </div>
                            <div
                                className="radio-btn"
                                onClick={() => {
                                    setAccountType("savings");
                                }}
                            >
                                <input
                                    type="radio"
                                    value={accountType}
                                    name="accountType"
                                    checked={accountType == "savings"}
                                />
                                Savings
                            </div>
                        </div>
                    </div>
                    <div className="flex flex-col space-y-3 custom-select">
                        <select
                            name="format" id="format"
                            className="round"
                            value={selectedOption}
                            onChange={e => setSelectedOption(e.target.value)}>
                            {branches.map(o => (
                                <option key={o.label} value={o.value}>{o.label}</option>
                            ))}
                        </select>
                    </div>
                    <Button className="bg-indigo-500 text-gray-100 p-4 w-full rounded-full tracking-wide
                                font-semibold font-display focus:outline-none focus:shadow-outline hover:bg-indigo-600
                                shadow-lg" isLoading={isLoading} value="Create" type="submit">
                        Sing up
                    </Button>
                </div>
            </form>
            <ToastContainer
                position="top-right"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="light"
            />
        </DashboardLayout>
    );
};

export default CreateAccount;