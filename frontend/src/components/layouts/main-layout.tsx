import { ReactNode } from "react";
import Navbar from "../header/navbar";

interface IMainLayoutProps {
  children: ReactNode;
}

const MainLayout = ({ children }: IMainLayoutProps) => {
  return (
    <>
      <header>
        <Navbar />
      </header>
      <main className="app">{children}</main>
    </>
  );
};

export default MainLayout;
