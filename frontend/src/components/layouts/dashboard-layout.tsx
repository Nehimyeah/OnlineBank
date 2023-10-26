import { useSelector } from "react-redux";
import {setUser, selectUser, AuthStore} from "../../app/authSlice";
import Button from "../../components/elements/button";
import {useDispatch} from "react-redux/es/hooks/useDispatch";
import Cookie from "js-cookie";
import {Link, useNavigate, useLocation} from "react-router-dom";
import {ReactNode, useEffect, useRef, useState} from "react";
import {RolePolicy} from "../type/types";
import Cookies from "js-cookie";

interface IAuthLayoutProps {
    children: ReactNode;
}
const DashboardLayout = ({ children }: IAuthLayoutProps) => {
    let loggedUser = useSelector(selectUser);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const location = useLocation();
    const icons = useRef<{users: string, accounts: string, branches: string}>({
        users: "M9.13478 20.7733V17.7156C9.13478 16.9351 9.77217 16.3023 10.5584 16.3023H13.4326C13.8102 16.3023 14.1723 16.4512 14.4393 16.7163C14.7063 16.9813 14.8563 17.3408 14.8563 17.7156V20.7733C14.8539 21.0978 14.9821 21.4099 15.2124 21.6402C15.4427 21.8705 15.7561 22 16.0829 22H18.0438C18.9596 22.0023 19.8388 21.6428 20.4872 21.0008C21.1356 20.3588 21.5 19.487 21.5 18.5778V9.86686C21.5 9.13246 21.1721 8.43584 20.6046 7.96467L13.934 2.67587C12.7737 1.74856 11.1111 1.7785 9.98539 2.74698L3.46701 7.96467C2.87274 8.42195 2.51755 9.12064 2.5 9.86686V18.5689C2.5 20.4639 4.04738 22 5.95617 22H7.87229C8.55123 22 9.103 21.4562 9.10792 20.7822L9.13478 20.7733Z",
        accounts: "M6.11304 4.5H11.9051C14.3271 4.5 16.0181 6.16904 16.0181 8.56091V15.4391C16.0181 17.831 14.3271 19.5 11.9051 19.5H6.11304C3.69102 19.5 2 17.831 2 15.4391V8.56091C2 6.16904 3.69102 4.5 6.11304 4.5ZM19.958 6.87898C20.397 6.65563 20.912 6.67898 21.331 6.94294C21.75 7.20589 22 7.66274 22 8.16223V15.8384C22 16.3389 21.75 16.7947 21.331 17.0577C21.102 17.2008 20.846 17.2739 20.588 17.2739C20.373 17.2739 20.158 17.2231 19.957 17.1206L18.476 16.3734C17.928 16.0952 17.588 15.5369 17.588 14.9165V9.08305C17.588 8.46173 17.928 7.90335 18.476 7.62721L19.958 6.87898Z",
        branches: "M2 10.6699C2 5.88166 5.84034 2 10.5776 2C12.8526 2 15.0343 2.91344 16.6429 4.53936C18.2516 6.16529 19.1553 8.37052 19.1553 10.6699C19.1553 15.4582 15.3149 19.3399 10.5776 19.3399C5.84034 19.3399 2 15.4582 2 10.6699ZM19.0134 17.6543L21.568 19.7164H21.6124C22.1292 20.2388 22.1292 21.0858 21.6124 21.6082C21.0955 22.1306 20.2576 22.1306 19.7407 21.6082L17.6207 19.1785C17.4203 18.9766 17.3076 18.7024 17.3076 18.4164C17.3076 18.1304 17.4203 17.8562 17.6207 17.6543C18.0072 17.2704 18.6268 17.2704 19.0134 17.6543Z",
        loans: "M2 10.6699C2 5.88166 5.84034 2 10.5776 2C12.8526 2 15.0343 2.91344 16.6429 4.53936C18.2516 6.16529 19.1553 8.37052 19.1553 10.6699C19.1553 15.4582 15.3149 19.3399 10.5776 19.3399C5.84034 19.3399 2 15.4582 2 10.6699ZM19.0134 17.6543L21.568 19.7164H21.6124C22.1292 20.2388 22.1292 21.0858 21.6124 21.6082C21.0955 22.1306 20.2576 22.1306 19.7407 21.6082L17.6207 19.1785C17.4203 18.9766 17.3076 18.7024 17.3076 18.4164C17.3076 18.1304 17.4203 17.8562 17.6207 17.6543C18.0072 17.2704 18.6268 17.2704 19.0134 17.6543Z"
    });
    const policy = useRef<RolePolicy>({
        CUSTOMER: ['accounts'],
        ADMIN: ['users', 'branches'],
        TELLER: ['accounts'],
        MANAGER: ['accounts', "loans"]
    })
    const [loggedInUserRole, setLoggedInUserRole] = useState<'MANAGER' | 'TELLER' | 'CUSTOMER' | 'ADMIN'>('ADMIN')
    useEffect(() => {
        const user = Cookies.get('user')
        if (user) {
            setLoggedInUserRole(JSON.parse(Cookies.get('user') as string).role);
        } else {
            setLoggedInUserRole("MANAGER")
        }
    }, []);

    const logout = () => {
        const user: AuthStore = {
            firstName: null,
            iat: 0,
            lastName: '',
            role: '',
            sub: '',
            email: null,
            exp: 0,
            id: '',
            isActive: false
        };
        Cookie.remove("token")
        Cookie.remove("user")
        dispatch(setUser(user));
        navigate("/auth/login")
    }


    // @ts-ignore
    return (
        <section>
            <main className="flex">
                <div className="bg-gray-900 sm:w-60 min-h-screen w-14 pt-4 transition-all">
                    <div className="text-center text-white px-6 flex gap-3">
                        <img className="w-8 h-8 mt-0.5" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAYAAAA7MK6iAAAACXBIWXMAAAsTAAALEwEAmpwYAAABQElEQVR4nO3Vuy5FQRjFcU5EQ2g0nkBFo1AIL+CSeABKjYYovIKW7ryCRqNTEQVxqUQjIdEoJBJCiNtPTvIV22VfyNkIVrKTnZm15r9nvj0zDQ3/+snCGPbjGfsKYB/WvNUmBssAdmEJT7K1iu56ADuwgHvFVfNW0fkZYAvmcOHzusI82ooAmzCJ07xRE5k8ncUkmtOgjdgqOJudRG67YGazxkgD78UWydJswl+J9+mczD523wW/+og0HQVwAJdRx5HIHKSFcmtcALwb/f04ibZbnOOuTPA9esNTQQ9mcJiRqQtYLO8ihtAe/lYcKxF8+2pZbzAVmWqZ4OXoH4/D5RoT0bZSJvgBowlfJXFbPZYJFhfFesK7kXd51Av8YrCPeFOF4fhh6q2b2i7IAg99C/jvSHm1zV5y5dU2G/yvX61nYg0MUNF2iZ8AAAAASUVORK5CYII=" />
                        <p className="font-bold text-3xl mb-4 text-white ">Banking</p>
                    </div>
                    <ul className="mt-11 sidebar">
                        {
                            policy.current[loggedInUserRole as string].map((item:string, i:number) => {
                            return (
                                <Link to={`/${item}`} key={i}>
                                    <li className="hover:bg-gray-800 cursor-pointer sm:justify-start px-4 h-12 flex items-center justify-center ">

                                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                            <path d={icons.current[item] as string} fill="#fff"/>
                                        </svg>

                                        <span className={"ml-3 hidden sm:block  text-gray-400 font-semibold tracking-wide hover:text-white transition-colors"}>{item[0].toUpperCase() + item.slice(1)}</span>
                                    </li>
                                </Link>
                            )
                        })}
                    </ul>
                </div>
                <section className="flex-1">
                    <div className="bg-gray-900 p-4 text-white flex justify-end align-bottom gap-2">
                        <p className="capitalize flex align-center leading-2 mt-1.5">{loggedUser}</p>
                        <Button onClick={logout} style={{width: '120px'}} value="Logout" className="px-6 text-sm py-2" />
                    </div>
                    <div>{children}</div>
                </section>
            </main>
        </section>
    );
};

export default DashboardLayout;
