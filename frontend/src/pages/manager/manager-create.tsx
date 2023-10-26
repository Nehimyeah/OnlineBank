import { useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useState } from "react";
import { axiosPrivate} from "../../service/axios.service";
import ClientInput from "../../components/auth/inputs/client-input";
import FormFieldError from "../../components/auth/form/form-field-error";
import Button from "../../components/elements/button";
import DashboardLayout from "../../components/layouts/dashboard-layout";
import {useNavigate} from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {UserDetails} from "../../components/type/types";
import {userSchema} from "../../components/mixins/userRelatedFunctions";

const CreateManager = () => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [success, setSuccess] = useState<boolean>(false);
    const [submissionErrors, setSubmissionErrors] = useState<string[]>([]);
    const [roleType, setRoleType] = useState("CUSTOMER");
    const navigate = useNavigate();

    const {
        handleSubmit,
        register,
        resetField,
        formState: { errors },
    } = useForm<UserDetails>({ resolver: yupResolver(userSchema) });
    const { ref: firstnameRef, ...firstnameRest } = register("firstName");
    const { ref: role, ...roleRest } = register("role");
    const { ref: lastnameRef, ...lastnameRest } = register("lastName");
    const { ref: street1Ref, ...street1Rest } = register("address.street1");
    const { ref: street2Ref, ...street2Rest } = register("address.street2");
    const { ref: cityRef, ...cityRest } = register("address.city");
    const { ref: stateRef, ...stateRest } = register("address.state");
    const { ref: zipcodeRef, ...zipcodeRest } = register("address.zip");
    const { ref: emailRef, ...emailRest } = register("email");
    const { ref: passwordRef, ...passwordRest } = register("password");
    const { ref: confirmPasswordRef, ...confirmPasswordRest } =
        register("confirmPassword");

    const notify = () => toast("User has successfully been created!");

    const onsubmit = async (data: UserDetails) => {
        setSubmissionErrors([]);
        setIsLoading(true);
        
        data.role = roleType;

        try {
            await axiosPrivate.post("/users/teams", data).finally(() => {
                setIsLoading(false);
                resetField("password");
                resetField("confirmPassword");
                notify();
                setTimeout(() => {
                    navigate("/managers")
                }, 1000);
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

    return (
        <DashboardLayout>
            <form
                noValidate
                onSubmit={handleSubmit(onsubmit)}
                className="bg-white/20 container mx-auto mt-5"
            >
                <h1 className="text-center font-bold text-3xl mb-4 text-indigo-900">
                    Create User
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
                            <label>First name</label>
                            <ClientInput
                                placeholder="First name"
                                reference={firstnameRef}
                                {...firstnameRest}
                            />
                            {errors.firstName?.message ? (
                                <FormFieldError errorMessage={errors.firstName.message} />
                            ) : (
                                ""
                            )}
                        </div>
                        <div className="flex flex-col space-y-3">
                            <label>Last name</label>
                            <ClientInput
                                placeholder="Last name"
                                reference={lastnameRef}
                                {...lastnameRest}
                            />
                            {errors.lastName?.message ? (
                                <FormFieldError errorMessage={errors.lastName.message} />
                            ) : (
                                ""
                            )}
                        </div>
                        <div className="flex flex-col space-y-3">
                            <ClientInput
                                placeholder="Email"
                                reference={emailRef}
                                {...emailRest}
                            />
                            {errors.email?.message ? (
                                <FormFieldError errorMessage={errors.email.message} />
                            ) : (
                                ""
                            )}
                        </div>
                        <div className="flex flex-col space-y-1">
                            <ClientInput
                                type={"password"}
                                placeholder="Password"
                                reference={passwordRef}
                                {...passwordRest}
                            />
                            {errors.password?.message ? (
                                <FormFieldError errorMessage={errors.password.message} />
                            ) : (
                                ""
                            )}
                        </div>
                        <div className="flex flex-col space-y-1">
                            <ClientInput
                                type={"password"}
                                placeholder="Confirm Password"
                                reference={confirmPasswordRef}
                                {...confirmPasswordRest}
                            />
                            {errors.confirmPassword?.message ? (
                                <FormFieldError errorMessage={errors.confirmPassword.message} />
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
                        <div className="flex flex-col space-y-1">
                            <div className="radio-btn-container">
                                <div
                                    className="radio-btn"
                                    onClick={() => {
                                        setRoleType("CUSTOMER");
                                    }}
                                >
                                    <input
                                        type="radio"
                                        value={roleType}
                                        name="roleType"
                                        checked={roleType == "CUSTOMER"}
                                    />
                                    Customer
                                </div>
                                <div
                                    className="radio-btn"
                                    onClick={() => {
                                        setRoleType("MANAGER");
                                    }}
                                >
                                    <input
                                        type="radio"
                                        value={roleType}
                                        name="roleType"
                                        checked={roleType == "MANAGER"}
                                    />
                                    Manager
                                </div>
                                <div
                                    className="radio-btn"
                                    onClick={() => {
                                        setRoleType("ADMIN");
                                    }}
                                >
                                    <input
                                        type="radio"
                                        value={roleType}
                                        name="roleType"
                                        checked={roleType == "ADMIN"}
                                    />
                                    Admin
                                </div>
                                <div
                                    className="radio-btn"
                                    onClick={() => {
                                        setRoleType("TELLER");
                                    }}
                                >
                                    <input
                                        type="radio"
                                        value={roleType}
                                        name="roleType"
                                        checked={roleType == "TELLER"}
                                    />
                                    Teller
                                </div>
                            </div>
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

export default CreateManager;