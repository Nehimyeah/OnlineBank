import axios from "axios";
import Cookie from "js-cookie";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL; //? This is the server's base url

export const axiosClient = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:9000",
});

export const axiosPrivate = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:9000",
});


export const axiosPrivateBranch = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:3300",
});

export const axiosPrivateBank = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:8181",
});

export const axiosPrivateReport = axios.create({
    baseURL: BACKEND_URL ?? "http://localhost:7777",
});

axiosPrivate.interceptors.request.use(
    function(config) {
      const token = Cookie.get("token");
      if (token) {
        config.headers["Authorization"] = 'Bearer ' + token;
      }
      return config;
    },
    function(error) {
      return Promise.reject(error);
    }
);

axiosPrivateBranch.interceptors.request.use(
    function(config) {
      const token = Cookie.get("token");
      if (token) {
        config.headers["Authorization"] = 'Bearer ' + token;
      }
      return config;
    },
    function(error) {
      return Promise.reject(error);
    }
);

axiosPrivateBank.interceptors.request.use(
    function(config) {
      const token = Cookie.get("token");
      if (token) {
        config.headers["Authorization"] = 'Bearer ' + token;
      }
      return config;
    },
    function(error) {
      return Promise.reject(error);
    }
);

axiosPrivateReport.interceptors.request.use(
    function(config) {
        const token = Cookie.get("token");
        if (token) {
            config.headers["Authorization"] = 'Bearer ' + token;
        }
        return config;
    },
    function(error) {
        return Promise.reject(error);
    }
);
