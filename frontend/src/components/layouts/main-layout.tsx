import { ReactNode } from "react";
import Navbar from "../header/navbar";
import {useLocation} from "react-router-dom";

interface IMainLayoutProps {
  children: ReactNode;
}

const MainLayout = ({ children }: IMainLayoutProps) => {
  return (
    <>
        {
            useLocation().pathname.includes("auth") &&
            <header>
                <Navbar />
            </header>
        }
        <main className="app">{children}</main>
    </>
  );
};

export default MainLayout;
