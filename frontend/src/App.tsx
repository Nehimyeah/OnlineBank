import {Route, Routes, Navigate, useNavigate} from "react-router-dom";
import LoginPage from "./pages/auth/login-page";
import MainLayout from "./components/layouts/main-layout";
import HomePage from "./pages/manager/home";
import BranchPage from "./pages/branches/branches";
import SignupPage from "./pages/auth/signup-page";
import CreateManager from "./pages/manager/manager-create";
import CreateBranch from "./pages/branches/branch-create";
import AccountsList from "./pages/account/accounts";
import AccountCreate from "./pages/account/account-create";
import WithdrawMoney from "./pages/account/withdraw-money";
import TransferMoney from "./pages/account/transfer-money";
import { store } from "./app/store";
import { Provider } from "react-redux";
import DepositMoney from "./pages/account/deposit-money";
import {useEffect} from "react";
import Cookie from "js-cookie";
import WelcomePage from "./pages/welcome";
import AccountTransactions from "./pages/account/account-transactions";
import {ToastContainer} from "react-toastify";
import Loans from "./pages/loan/loans";

function App() {
  const navigate = useNavigate();
  useEffect(() => {
    console.log(1);
    const token = Cookie.get("token");
    if (!token) {
      navigate("/auth/login")
    }
  }, []);
  return (
    <Provider store={store}>
      <MainLayout>
        <Routes>
          <Route path="/" element={<WelcomePage />} />
          <Route path="auth">
            <Route path="login" element={<LoginPage />} />
            <Route path="signup" element={<SignupPage />} />
          </Route>
          <Route path="users" element={<HomePage />} />
          <Route path="users/create" element={<CreateManager />} />
          <Route path="branches" element={<BranchPage />} />
          <Route path="branches/create" element={<CreateBranch />} />
          <Route path="accounts/:id/transactions" element={<AccountTransactions />} />
          <Route path="accounts" element={<AccountsList />} />
          <Route path="accounts/create" element={<AccountCreate />} />
          <Route path="accounts/transfer" element={<TransferMoney />} />
          <Route path="accounts/withdraw" element={<WithdrawMoney />} />
          <Route path="accounts/deposit" element={<DepositMoney />} />
          <Route path="loans" element={<Loans />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
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
      </MainLayout>
    </Provider>
  );
}

export default App;
