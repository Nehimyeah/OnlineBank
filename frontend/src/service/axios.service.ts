import axios from "axios";
import Cookie from "js-cookie";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL; //? This is the server's base url
const token: string | undefined = Cookie.get("token");

export const axiosClient = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:9000",
});

export const axiosPrivate = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:9000",
  headers: {
    Authorization: `Bearer ${token}`
  }
});
