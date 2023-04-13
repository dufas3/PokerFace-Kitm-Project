import "./NotFound.css"

function NotFound() {
    return (
        <div className="notfound">
            <h1> 404 <br/>
                The page you looking for does not exist and can't be found</h1>
            <h4>Press the button below to go back to login page</h4>
            <a href="https://pokerface-dev.azurewebsites.net/Login" className="btn btn-primary btn-lg" role="button">Login Page</a>
        </div>
        )
}
export default NotFound;