import { createSlice } from "@reduxjs/toolkit";
import { PayloadAction } from "@reduxjs/toolkit/dist/createAction";
import { RootState } from "./store";
import Cookie from "js-cookie";
export type AuthStore = {
  exp: number;
  firstName: string | null;
  iat: number;
  id: string;
  isActive: boolean;
  lastName: string;
  role: string;
  sub: string;
  email: string | null;
};

const initialState = {
  email: null,
} as AuthStore;

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setUser(state, action: PayloadAction<AuthStore>) {
      state.email = action.payload.email;
      state.firstName = action.payload.firstName
      state.lastName = action.payload.lastName
      state.email = action.payload.sub
      state.isActive = action.payload.isActive
    },
    logoutUser(state) {
      state.email = null;
    },
  },

});

export const { setUser, logoutUser } = authSlice.actions;
export default authSlice.reducer;

export const selectUser = (state: RootState) => {
  const user = <string>Cookie.get("user");
  if (user) {
    const decodedUser:AuthStore = JSON.parse(user);
    return decodedUser.firstName + " " + decodedUser.lastName
  }
  return ''
};

export const getUserRole = (state: RootState) => {
  const user = <string>Cookie.get("user");
  if (user) {
    const decodedUser:AuthStore = JSON.parse(user);
    return decodedUser.role;
  }
};
