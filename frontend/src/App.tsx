import { Route, Routes, Navigate } from "react-router-dom";
import LoginPage from "./pages/auth/login-page";
import MainLayout from "./components/layouts/main-layout";
import HomePage from "./pages/manager/home";
import BranchPage from "./pages/branches/branches";
import SignupPage from "./pages/auth/signup-page";
import CreateManager from "./pages/manager/manager-create";
import CreateBranch from "./pages/branches/branch-create";
import AccountsList from "./pages/account/accounts";
import { store } from "./app/store";
import { Provider } from "react-redux";

function App() {
  return (
    <Provider store={store}>
      <MainLayout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="auth">
            <Route path="login" element={<LoginPage />} />
            <Route path="signup" element={<SignupPage />} />
          </Route>
          <Route path="users" element={<HomePage />} />
          <Route path="users/create" element={<CreateManager />} />
          <Route path="branches" element={<BranchPage />} />
          <Route path="branches/create" element={<CreateBranch />} />
          <Route path="accounts" element={<AccountsList />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </MainLayout>
    </Provider>
  );
}

export default App;
