import { useEffect } from "react";
import { useSelector } from "react-redux/es/exports";
import { selectUser, setUser } from "../../app/authSlice";
import jwt from "jwt-decode";
import Cookies from "js-cookie";
import { useDispatch } from "react-redux/es/hooks/useDispatch";
import { Link } from "react-router-dom";
import Button from "../elements/button";
import { axiosClient } from "../../service/axios.service";
import useRefreshToken from "../../hooks/use-refresh-token";

const Navbar = () => {
  const loggedUser = useSelector(selectUser);
  const dispatch = useDispatch();
  const refresh = useRefreshToken();

  const decodeAndStore = (token: string) => {
    const payload: { username: string } = jwt(token);
    dispatch(setUser({ username: payload.username }));
  };

  useEffect(() => {
    if (loggedUser) return;

    const token = Cookies.get("token");

    if (token) {
      decodeAndStore(token);
    } else {
      refresh().then((token) => decodeAndStore(token));
    }
  }, []);

  const notLoggedIn = (
    <>
      <Link to="/auth/login">
        <Button value="Get Started" className="w-max px-6 text-xl" />
      </Link>
    </>
  );

  const logout = async (): Promise<void> => {
    await axiosClient.post("/auth/logout");
  };

  return (
      <div className="header-2">

        <nav className="bg-white py-2 md:py-4">
          <div className="container mx-auto md:flex md:items-center">

            <div className="flex justify-between items-center">
              <Link to="/">
                <a className="font-bold text-xl text-indigo-600">Banking</a>
              </Link>
              <button className="border border-solid border-gray-600 px-3 py-1 rounded text-gray-600 opacity-50 hover:opacity-75 md:hidden" id="navbar-toggle">
                <i className="fas fa-bars"></i>
              </button>
            </div>

            <div className="hidden md:flex flex-col md:flex-row md:ml-auto mt-3 md:mt-0" id="navbar-collapse">
              <Link to="/auth/login" className='mt-2'>
                <a href="#" className="p-2 lg:px-4 md:mx-2 text-indigo-600 text-center border border-transparent rounded hover:bg-indigo-100 hover:text-indigo-700 transition-colors duration-300 active:text-white active:rounded active:bg-indigo-600">Login</a>
              </Link>
              <Link to="/auth/signup" className='mt-2'>
                <a href="#" className="p-2 lg:px-4 md:mx-2 text-indigo-600 text-center border border-solid border-indigo-600 rounded hover:bg-indigo-600 hover:text-white transition-colors duration-300 mt-1 md:mt-0 md:ml-1">Signup</a>
              </Link>
            </div>
          </div>
        </nav>

      </div>
  );
};

export default Navbar;
