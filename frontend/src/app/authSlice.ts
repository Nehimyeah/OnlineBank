import { createSlice } from "@reduxjs/toolkit";
import { PayloadAction } from "@reduxjs/toolkit/dist/createAction";
import { RootState } from "./store";

export type AuthStore = {
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
    },
    logoutUser(state) {
      state.email = null;
    },
  },
});

export const { setUser, logoutUser } = authSlice.actions;
export default authSlice.reducer;

export const selectUser = (state: RootState) => state.email;
