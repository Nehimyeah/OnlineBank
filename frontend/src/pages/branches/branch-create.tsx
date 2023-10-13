import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import {useEffect, useState} from "react";
import { axiosPrivateBranch, axiosPrivate } from "../../service/axios.service";
import ClientInput from "../../components/auth/inputs/client-input";
import FormFieldError from "../../components/auth/form/form-field-error";
import Button from "../../components/elements/button";
import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useNavigate} from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {BranchDetails, UserDetails} from "../../components/type/types";
import {branchSchema} from "../../components/mixins/userRelatedFunctions";

const CreateBranch = () => {
    const [optionUsers, setOptionUsers] = useState([])

    const fetchData = () => {
        try {
            axiosPrivate.get("/users/teams").then((res) => {

                const tempUsers = res.data.map((u: UserDetails) => {
                    return {
                        label: u.firstName + " " + u.lastName,
                        value: u.id
                    }
                })
                setOptionUsers(tempUsers)
                setSelectedOption(tempUsers[0].value.toString())
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

    const {
        handleSubmit,
        register,
        resetField,
        formState: { errors },
    } = useForm<BranchDetails>({ resolver: yupResolver(branchSchema) });
    const { ref: branchNameRef, ...branchNameRest } = register("branchName");
    const { ref: street1Ref, ...street1Rest } = register("address.street1");
    const { ref: street2Ref, ...street2Rest } = register("address.street2");
    const { ref: cityRef, ...cityRest } = register("address.city");
    const { ref: stateRef, ...stateRest } = register("address.state");
    const { ref: zipcodeRef, ...zipcodeRest } = register("address.zip");

    const notify = () => toast("Branch has successfully been created!");


    const onsubmit = async (data: BranchDetails) => {
        setSubmissionErrors([]);
        setIsLoading(true);

        data.branchManagerId = selectedOption;

        try {
            await axiosPrivateBranch.post("/branches", data)
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
                    Create Branch
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
                    <div className="flex flex-col space-y-3">
                        <ClientInput
                            placeholder="Branch name"
                            reference={branchNameRef}
                            {...branchNameRest}
                        />
                        {errors.branchName?.message ? (
                            <FormFieldError errorMessage={errors.branchName.message} />
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="flex flex-col space-y-3 custom-select">
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
                    <div className="flex flex-col space-y-1">
                        <ClientInput
                            placeholder="Street 1"
                            reference={street1Ref}
                            {...street1Rest}
                        />
                        {errors.address?.street1?.message ? (
                            <FormFieldError errorMessage={errors.address?.street1?.message} />
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="flex flex-col space-y-1">
                        <ClientInput
                            placeholder="Street 2"
                            reference={street2Ref}
                            {...street2Rest}
                        />
                        {errors.address?.street2?.message ? (
                            <FormFieldError errorMessage={errors.address?.street2.message} />
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="flex flex-col space-y-1">
                        <ClientInput
                            placeholder="City"
                            reference={cityRef}
                            {...cityRest}
                        />
                        {errors.address?.city?.message ? (
                            <FormFieldError errorMessage={errors.address.city.message} />
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="flex flex-col space-y-1">
                        <ClientInput
                            placeholder="State"
                            reference={stateRef}
                            {...stateRest}
                        />
                        {errors.address?.state?.message ? (
                            <FormFieldError errorMessage={errors.address?.state.message} />
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="flex flex-col space-y-1">
                        <ClientInput
                            placeholder="zip"
                            reference={zipcodeRef}
                            {...zipcodeRest}
                        />
                        {errors.address?.zip?.message ? (
                            <FormFieldError errorMessage={errors.address.zip.message} />
                        ) : (
                            ""
                        )}
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

export default CreateBranch;