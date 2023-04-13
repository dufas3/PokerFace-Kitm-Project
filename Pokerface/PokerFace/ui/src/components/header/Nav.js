import FestoLogo from "../../imgs/festo.png";
import Dropdown from "react-bootstrap/Dropdown";
import { Link, useSearchParams } from "react-router-dom";
import "./Nav.css";
import { useNavigate } from "react-router-dom";
import LogoutUser from "../../api/logoutUser";
import { signalRConnection } from "../../api/signalR/signalRHub";
import { WebsiteUrl } from "../../common/connectionUrl";

const Nav = ({ user }) => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const HandleLogout = async () => {
    await LogoutUser({ roomId: searchParams.get("room"), userId: user.id });
    signalRConnection.stop();
    navigate("/Join?room=" + searchParams.get("room"), { replace: true });
  };

  return (
    <header>
      <nav className="navbar navbar-expand-sm navbar-light bg-light border-bottom box-shadow mb-3 fixed-top">
        <div className="container-fluid">
          <a className="navbar-brand">Festo Scrum Poker</a>
          <div className="d-flex justify-content-end">
            {!user ? (
              <Link to="/Login">
                <a className="btn mx-5" id="H1">
                  <i className="bi bi-person-fill" />
                  Login
                </a>
              </Link>
            ) : (
              <div className="component m-lg-1">
                <Dropdown>
                  <Dropdown.Toggle variant="btn-secondary" id="dropdown-basic">
                    <i className="bi bi-person-fill" /> {user.name}
                  </Dropdown.Toggle>

                  <Dropdown.Menu>
                    <Dropdown.Item
                      onClick={() => {
                        navigator.clipboard.writeText(
                          WebsiteUrl +
                            "/?roomId=" +
                            searchParams.get("roomId")
                        );
                      }}
                    >
                      <h6>Room link</h6>
                    </Dropdown.Item>
                    <Dropdown.Item>
                      <Link to="/Join" style={{ textDecoration: "none" }}>
                        <a
                          onClick={HandleLogout}
                          className="btn"
                          id="logoutbutton"
                        >
                          <i className="fa-solid fa-arrow-right-from-bracket" />
                          Logout
                        </a>
                      </Link>
                    </Dropdown.Item>
                  </Dropdown.Menu>
                </Dropdown>
              </div>
            )}
            <a
              className="navbar-brand"
              href="https://www.festo.com/us/en/"
              id="logolink"
            >
              <img src={FestoLogo} className="logo" />
            </a>
          </div>
        </div>
      </nav>
    </header>
  );
};
export default Nav;
