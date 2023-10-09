import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { selectUser } from "../app/authSlice";
import Button from "../components/elements/button";
import image from "../assets/001-wallet.svg"
const HomePage = () => {
  const loggedUser = useSelector(selectUser);

  // @ts-ignore
  return (
    <section className={'container'}>

    </section>
  );
};

export default HomePage;
