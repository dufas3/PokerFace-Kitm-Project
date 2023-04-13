import { Route, Routes } from "react-router-dom";
import Join from "../join/Join";
import Login from "../login/Login";
import Poker from "../poker/Poker";
import "./App.css";
import "../../../node_modules/bootstrap/dist/css/bootstrap.min.css";
import RouteManager from "./RouteManager";
import NotFound from "../notfound/NotFound";
import PageLayout from "../pageLayout/PageLayout";
import { ToastContainer } from "react-toastify";

function App() {
  return (
    <>
      <div className="App">
        <Routes>
          <Route element={<PageLayout />}>
            <Route path="/Poker" element={<Poker />} />
            <Route path="/Join" element={<Join />} />
            <Route path="/Login" element={<Login />} />
            <Route path="/" element={<RouteManager />} />
          </Route>
          <Route path="*" element={<NotFound />} />
        </Routes>
      </div>
      <ToastContainer />
    </>
  );
}

export default App;
