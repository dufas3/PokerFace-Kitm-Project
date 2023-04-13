import "./Login.css";
import { Link, useNavigate } from "react-router-dom";
import { useCallback, useState } from "react";
import GetModerator from "../../api/get/getModerator";
import LoadingScreen from "../loadingScreen/LoadingScreen";
import { MethodNames } from "../../common/methodNames";
import { signalRConnection } from "../../api/signalR/signalRHub";
import { setUserId } from "../../common/UserId";
import Nav from "../header/Nav";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [enter, setEnter] = useState(false);
  //make it so the useState will have default state as error object array: [{isActiveError: boolean, errorType: string}] //error types will be: networkError, wrongLoginError
  const [errors, setErrors] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [navig, setNavig] = useState();

  const navigate = useNavigate();
  const handleOnClick = useCallback(() => navig, [navigate]);

  const validation = async () => {
    setIsLoading(true);

    let response = await GetModerator({ email: email, password: password });

    if (!response) {
      setEnter(false);
      setErrors(true);
      setIsLoading(false);
      return;
    }

    setErrors(false);
    setIsLoading(true);

    console.log(response);

    await signalRConnection.start();

    await signalRConnection.invoke(
      MethodNames.ReceiveConnectSockets,
      response.userId,
      response.roomId
    );

    setIsLoading(false);
    setEnter(true);

    setUserId(response.userId);
    setNavig(
      navigate("/Poker?roomId=" + response.roomId, {
        replace: true,
      })
    );

    handleOnClick();
  };
  return (
    <>
      <Nav user={null}/>
      <div className="center mb-345">
        {isLoading ? <LoadingScreen /> : ""}
        <div className="login">
          <div className="info">
            <h2>Welcome back</h2>
            <h3>Login with your account:</h3>
          </div>
          <input
            onChange={(e) => {
              setEmail(e.currentTarget.value);
            }}
            className={errors ? "border-danger" : ""}
            type="email"
            placeholder="&#61447;   Enter your email"
            id="emailenter"
          ></input>
          <input
            onChange={(e) => {
              setPassword(e.currentTarget.value);
            }}
            className={errors ? "border-danger" : ""}
            type="password"
            placeholder="&#61475;   Enter your password"
            id="passwordenter"
          ></input>
          <div className="error">
            {errors && (
              <h5 className="error-text text-danger">
                Wrong username or password!
              </h5>
            )}
          </div>
          <button
            onClick={
              isLoading
                ? () => {}
                : () => {
                    validation();
                  }
            }
            className="fix"
            id="loginbutton"
          >
            {enter ? (
              <Link to="/Poker" style={{ textDecoration: "none" }}>
                <h3 className="login-button">
                  {isLoading ? "Loading" : "Login"}
                </h3>
              </Link>
            ) : (
              <Link to="/Login" style={{ textDecoration: "none" }}>
                <h3 className="login-button">
                  {isLoading ? "Loading" : "Login"}
                </h3>
              </Link>
            )}
          </button>
          <div className="help">
            <a href="#" className="button" id="forgotpasswordbutton">
              <h4 className="reset-button">Forgotten password?</h4>
            </a>
          </div>
        </div>
      </div>
    </>
  );
};

export default Login;
