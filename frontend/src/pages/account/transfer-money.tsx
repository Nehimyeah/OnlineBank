import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import {useEffect, useState} from "react";
import {axiosPrivateBranch, axiosPrivate, axiosPrivateBank} from "../../service/axios.service";
import ClientInput from "../../components/auth/inputs/client-input";
import FormFieldError from "../../components/auth/form/form-field-error";
import Button from "../../components/elements/button";
import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useNavigate} from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {withdrawSchema} from "../../components/mixins/userRelatedFunctions";

const TransferMoney = () => {
    const [optionUsers, setOptionUsers] = useState([])

    const fetchData = () => {
        try {
            axiosPrivateBank.get(`/account/list`).then((res) => {

                const tempUsers = res.data.map((u) => {
                    return {
                        label: u.accountNumber,
                        value: u.accountNumber,
                        type: u.accountType
                    }
                })
                setOptionUsers(tempUsers)
                setSelectedOption(tempUsers[0].value.toString())
                setSelectedOption1(tempUsers[1].value.toString())
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
    const [selectedOption1, setSelectedOption1] = useState(undefined);
    const navigate = useNavigate();

    const {
        handleSubmit,
        register,
        resetField,
        formState: { errors },
    } = useForm({ resolver: yupResolver(withdrawSchema) });
    const { ref: amountRef, ...amountRest } = register("amount");
    const { ref: toAccountNumRef, ...toAccountNumRest } = register("toAccountNum");
    const notify = () => toast("Money successfully deposited!");


    const onsubmit = async (data) => {
        setSubmissionErrors([]);
        setIsLoading(true);

        data.fromAccountNum = selectedOption;
        data.toAccountNum = selectedOption1;
        try {
            await axiosPrivateBank.post("/account/transfer", data)
                .then(() => {
                    notify();
                    setTimeout(() => {
                        navigate("/accounts")
                    }, 1000);
                })
                .catch((err) => {
                    console.log(err.response.data);
                    toast(err.response.data);
                })
                .finally(() => {
                    setIsLoading(false);
                });

            setSuccess(true);
        } catch (error) {
            if (
                (error as any).response.data.errors &&
                (error as any).response.data.errors.length > 0
            ) {
                const errorsArray = (error as any).response.data.errors.map(
                    (err: { msg: string }) => err.msg
                );
                setSubmissionErrors(errorsArray);
            } else if ((error as any).response.data.message) {
                setSubmissionErrors([(error as any).response.data.message]);
            }
        }
    };

    // @ts-ignore
    return (
        <DashboardLayout>
            <form
                noValidate
                onSubmit={handleSubmit(onsubmit)}
                className="bg-white/20 container mx-auto mt-5"
            >
                <h1 className="text-center font-bold text-3xl mb-4 text-indigo-900">
                    Transfer
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
                    <label>Transfer amount</label>
                    <div className="flex flex-col space-y-3">
                        <ClientInput
                            placeholder="Transfer amount"
                            reference={amountRef}
                            {...amountRest}
                        />
                        {errors.amount?.message ? (
                            <FormFieldError errorMessage={errors.amount.message} />
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="flex flex-col space-y-3 custom-select">
                        <label>Select account</label>
                        <select
                            name="format" id="format"
                            className="round"
                            value={selectedOption}
                            onChange={e => setSelectedOption(e.target.value)}>
                            {optionUsers.map(o => (
                                <option key={o.label} value={o.value}>{o.label}</option>
                            ))}
                        </select>
                        {errors.branchManagerId?.message ? (
                            <FormFieldError errorMessage={errors.branchManagerId.message} />
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="flex flex-col space-y-3">
                        <label>Transfer account number</label>
                        <ClientInput
                            placeholder="Transfer account number"
                            reference={toAccountNumRef}
                            {...toAccountNumRest}
                        />
                        {errors.toAccountNum?.message ? (
                            <FormFieldError errorMessage={errors.toAccountNum.message} />
                        ) : (
                            ""
                        )}
                    </div>
                    <Button className="bg-indigo-500 text-gray-100 p-4 w-full rounded-full tracking-wide
                                font-semibold font-display focus:outline-none focus:shadow-outline hover:bg-indigo-600
                                shadow-lg" isLoading={isLoading} value="Transfer" type="submit">
                        Sing up
                    </Button>
                </div>
            </form>
        </DashboardLayout>
    );
};

export default TransferMoney;