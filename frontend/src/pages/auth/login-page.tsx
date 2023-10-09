import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { useState } from "react";
import { axiosClient } from "../../service/axios.service";
import { Navigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import ClientInput from "../../components/auth/inputs/client-input";
import ErrorSubmit from "../../components/auth/form/error-submit";
import Button from "../../components/elements/button";
import FormFieldError from "../../components/auth/form/form-field-error";
import AuthLayout from "../../components/layouts/auth-layout";
import * as yup from "yup";
import { setUser } from "../../app/authSlice";
import Cookies from "js-cookie";
import {Link} from "react-router-dom";

type LoginFields = {
  email: string;
  password: string;
};

const schema = yup
  .object({
    email: yup.string().required(),
    password: yup.string().required(),
  })
  .required();

const LoginPage = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [success, setSuccess] = useState<boolean>(false);

  const dispatch = useDispatch();

  const {
    handleSubmit,
    register,
    resetField,
    formState: { errors },
  } = useForm<LoginFields>({ resolver: yupResolver(schema) });

  const { ref: emailRef, ...emailRest } = register("email");
  const { ref: passwordRef, ...passwordRest } = register("password");

  const onsubmit = async (data: LoginFields) => {
    setIsLoading(true);
    setErrorMessage("");

    try {
      const response = await axiosClient
        .post("/users/login", data, { withCredentials: true })
        .finally(() => {
          setIsLoading(false);
          resetField("password");
        });

      Cookies.set("token", response.data.accessToken);

      dispatch(setUser({ email: response.data.email }));
      setSuccess(true);
    } catch (error) {
      setErrorMessage((error as any).response.data.message);
    }
  };

  return (
      <>
        <div className="flex items-center justify-center">
          <div className="lg:w-1/2 xl:max-w-screen-sm">
            <div className="mt-10 px-12 sm:px-24 md:px-48 lg:px-12 lg:mt-16 xl:px-24 xl:max-w-2xl">
              <h2 className="text-center text-4xl text-indigo-900 font-display font-semibold lg:text-left xl:text-5xl
                    xl:text-bold">Log in</h2>
              <div className="mt-12">
                <form noValidate
                      onSubmit={handleSubmit(onsubmit)}>
                  <div>
                    <div className="text-sm font-bold text-gray-700 tracking-wide">Email Address</div>
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
                  <div className="mt-8">
                    <div className="flex justify-between items-center">
                      <div className="text-sm font-bold text-gray-700 tracking-wide">
                        Password
                      </div>

                    </div>
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
                  <div className="mt-10">
                    <Button className="bg-indigo-500 text-gray-100 p-4 w-full rounded-full tracking-wide
                                font-semibold font-display focus:outline-none focus:shadow-outline hover:bg-indigo-600
                                shadow-lg" isLoading={isLoading} value="Login" type="submit">
                      Log In
                    </Button>
                  </div>
                </form>
                <div className="mt-12 text-sm font-display font-semibold text-gray-700 text-center">
                  Don't have an account ? <a className="cursor-pointer text-indigo-600 hover:text-indigo-800">
                  <Link to="/auth/signup">
                    Sign up
                  </Link>
                </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
  );
};

export default LoginPage;
