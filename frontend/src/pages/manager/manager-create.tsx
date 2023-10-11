const ManagerCreate = () => {

}
export default ManagerCreate;
// import {Link} from "react-router-dom";
// import * as yup from "yup";
// import {useState} from "react";
// import {useForm} from "react-hook-form";
// import {yupResolver} from "@hookform/resolvers/yup/dist/yup";
// import {axiosClient} from "../../service/axios.service";
// import DashboardLayout from "../../components/layouts/dashboard-layout";
// import Button from "../../components/elements/button";
// import ClientInput from "../../components/auth/inputs/client-input";
// import FormFieldError from "../../components/auth/form/form-field-error";
//
// type ManagerFields = {
//     email: string;
//     password: string;
//     confirmPassword: string;
//     firstName: string,
//     lastName: string,
//     street1: string,
//     street2: string,
//     city: string,
//     state: string,
//     zipcode: string,
// };
//
// const schema = yup
//     .object({
//         firstname: yup.string().required(),
//         lastname: yup.string().required(),
//         street1: yup.string().required(),
//         street2: yup.string(),
//         city: yup.string().required(),
//         state: yup.string().required(),
//         zipcode: yup.string().required(),
//         email: yup.string().required().email(),
//         password: yup.string().required(),
//         confirmPassword: yup
//             .string()
//             .oneOf([yup.ref("password"), null], "Passwords must match"),
//     })
//     .required();
//
//
// const CreateManager = () => {
//
//     const [isLoading, setIsLoading] = useState<boolean>(false);
//     const [success, setSuccess] = useState<boolean>(false);
//     const [submissionErrors, setSubmissionErrors] = useState<string[]>([]);
//
//     const {
//         handleSubmit,
//         register,
//         resetField,
//         formState: { errors },
//     } = useForm<ManagerFields>({ resolver: yupResolver(schema) });
//     const { ref: firstnameRef, ...firstnameRest } = register("firstName");
//     const { ref: lastnameRef, ...lastnameRest } = register("lastName");
//     const { ref: street1Ref, ...street1Rest } = register("street1");
//     const { ref: street2Ref, ...street2Rest } = register("street2");
//     const { ref: cityRef, ...cityRest } = register("city");
//     const { ref: stateRef, ...stateRest } = register("state");
//     const { ref: zipcodeRef, ...zipcodeRest } = register("zipcode");
//     const { ref: emailRef, ...emailRest } = register("email");
//     const { ref: passwordRef, ...passwordRest } = register("password");
//     const { ref: confirmPasswordRef, ...confirmPasswordRest } =
//         register("confirmPassword");
//
//     const submitFields = async (data: ManagerFields) => {
//         console.log(1);
//         setSubmissionErrors([]);
//         console.log(2);
//         setIsLoading(true);
//
//         try {
//             await axiosClient.post("/users/team", data).finally(() => {
//                 setIsLoading(false);
//             });
//
//             setSuccess(true);
//         } catch (error) {
//             if (
//                 (error as any).response.data.errors &&
//                 (error as any).response.data.errors.length > 0
//             ) {
//                 const errorsArray = (error as any).response.data.errors.map(
//                     (err: { msg: string }) => err.msg
//                 );
//                 setSubmissionErrors(errorsArray);
//             } else if ((error as any).response.data.message) {
//                 setSubmissionErrors([(error as any).response.data.message]);
//             }
//         }
//     };
//
//     return (
//         <DashboardLayout>
//             <div className={"container mx-auto"}>
//                 <form
//                     noValidate
//                     onSubmit={handleSubmit(submitFields)}
//                     className="bg-white/20 mt-5"
//                 >
//                     <h1 className="text-center font-bold  text-3xl mb-4 text-indigo-900">
//                         Create Manager
//                     </h1>
//                     {submissionErrors.length > 0 ? (
//                         <ul className="p-4 border-[1px] border-red-500 rounded-xl mb-5 max-w-sm flex items-center justify-center flex-col space-y-3">
//                             {submissionErrors.map((err, i) => (
//                                 <li key={i} className="text-red-500 font-bold">
//                                     • {err}.
//                                 </li>
//                             ))}
//                         </ul>
//                     ) : (
//                         ""
//                     )}
//                     {success ? (
//                         <div className="flex flex-col justify-center text-slate-800 items-center space-y-6 mx-auto container">
//                             <h1 className="text-3xl font-light">
//                                 You've successfully signed up!
//                             </h1>
//                             <p className="text-xl font-light text-center">
//                                 You can now go and login to your new account!
//                             </p>
//                             <Link to="/auth/login">
//                                 <Button
//                                     value="Go Login!"
//                                     className="bg-green-500 hover:bg-green-600"
//                                 />
//                             </Link>
//                         </div>
//                     ) : (
//                         <div className="flex flex-col space-y-6">
//                             <div className="flex flex-col space-y-3">
//                                 <ClientInput
//                                     placeholder="First name"
//                                     reference={firstnameRef}
//                                     {...firstnameRest}
//                                 />
//                                 {errors.firstName?.message ? (
//                                     <FormFieldError errorMessage={errors.firstName.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-3">
//                                 <ClientInput
//                                     placeholder="Last name"
//                                     reference={lastnameRef}
//                                     {...lastnameRest}
//                                 />
//                                 {errors.lastName?.message ? (
//                                     <FormFieldError errorMessage={errors.lastName.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-3">
//                                 <ClientInput
//                                     placeholder="Email"
//                                     reference={emailRef}
//                                     {...emailRest}
//                                 />
//                                 {errors.email?.message ? (
//                                     <FormFieldError errorMessage={errors.email.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-1">
//                                 <ClientInput
//                                     type={"password"}
//                                     placeholder="Password"
//                                     reference={passwordRef}
//                                     {...passwordRest}
//                                 />
//                                 {errors.password?.message ? (
//                                     <FormFieldError errorMessage={errors.password.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-1">
//                                 <ClientInput
//                                     type={"password"}
//                                     placeholder="Confirm Password"
//                                     reference={confirmPasswordRef}
//                                     {...confirmPasswordRest}
//                                 />
//                                 {errors.confirmPassword?.message ? (
//                                     <FormFieldError errorMessage={errors.confirmPassword.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-1">
//                                 <ClientInput
//                                     placeholder="Street 1"
//                                     reference={street1Ref}
//                                     {...street1Rest}
//                                 />
//                                 {errors.street1?.message ? (
//                                     <FormFieldError errorMessage={errors.street1.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-1">
//                                 <ClientInput
//                                     placeholder="Street 2"
//                                     reference={street2Ref}
//                                     {...street2Rest}
//                                 />
//                                 {errors.street2?.message ? (
//                                     <FormFieldError errorMessage={errors.street2.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-1">
//                                 <ClientInput
//                                     placeholder="City"
//                                     reference={cityRef}
//                                     {...cityRest}
//                                 />
//                                 {errors.city?.message ? (
//                                     <FormFieldError errorMessage={errors.city.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-1">
//                                 <ClientInput
//                                     placeholder="State"
//                                     reference={stateRef}
//                                     {...stateRest}
//                                 />
//                                 {errors.state?.message ? (
//                                     <FormFieldError errorMessage={errors.state.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <div className="flex flex-col space-y-1">
//                                 <ClientInput
//                                     placeholder="Zipcode"
//                                     reference={zipcodeRef}
//                                     {...zipcodeRest}
//                                 />
//                                 {errors.zipcode?.message ? (
//                                     <FormFieldError errorMessage={errors.zipcode.message} />
//                                 ) : (
//                                     ""
//                                 )}
//                             </div>
//                             <Button className="bg-indigo-500 text-gray-100 p-4 rounded-full tracking-wide
//                                 font-semibold font-display focus:outline-none focus:shadow-outline hover:bg-indigo-600
//                                 shadow-lg" isLoading={isLoading} value="Create" type="submit">
//                                 Sing up
//                             </Button>
//                         </div>
//                     )}
//                 </form>
//             </div>
//         </DashboardLayout>)
// }
// export default CreateManager;