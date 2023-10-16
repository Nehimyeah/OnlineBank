import axios from "axios";
import Cookie from "js-cookie";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL; //? This is the server's base url

export const axiosClient = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:9000",
});

export const axiosPrivate = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:9000",
  headers: {
    Authorization: `Bearer ${Cookie.get("token")}`,
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept"
  }
});


export const axiosPrivateBranch = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:3300",
  headers: {
    Authorization: `Bearer ${Cookie.get("token")}`,
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept"
  }
});

export const axiosPrivateBank = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:8181",
  headers: {
    Authorization: `Bearer ${Cookie.get("token")}`,
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept"
  }
});
