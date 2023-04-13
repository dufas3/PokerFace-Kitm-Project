import {Outlet} from "react-router-dom";
import Nav from "../header/Nav";
import Footer from "../footer/Footer";
const PageLayout = () => (
    <>
        <Nav/>
        <Outlet />
        <Footer />
    </>
);

export default PageLayout;