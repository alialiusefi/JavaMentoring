import React from "react";
import "./Login.css";

import LoginForm from "./LoginForm";
import '../../api/apiservice'
import GoogleLogin from "react-google-login";

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: {},
            error: '',
            errorMessage: ''
        }
    }

    render() {

        const responseGoogle = (response) => {
            console.log(response);
        };

        return (
            <div className="container">
                <br/>
                <div className=" container-fluid">
                    <LoginForm onSubmit={this.handleSubmit}/>
                    <br/>
                    <div className="row">
                        <div className="col">
                            <GoogleLogin
                                clientId="616675237807-7l6vdo0sma5lb1pev20okbcpi91e73uv.apps.googleusercontent.com"
                                render={renderProps => (
                                    <a onClick={renderProps.onClick}
                                       className="btn btn-block btn-social btn-google text-white d-flex justify-content-center flex-column">
                                <span
                                    className="fa fa-github text-white d-flex justify-content-center flex-column"></span>
                                        Sign in with Google
                                    </a>
                                )}
                                redirectUri="http://localhost:8080/api/oauth2/redirect"
                                buttonText="Login"
                                onSuccess={responseGoogle}
                                onFailure={responseGoogle}
                                cookiePolicy={'single_host_origin'}
                            />

                        </div>
                        <div className="col">
                            <a className="btn btn-block btn-social btn-github text-white d-flex justify-content-center flex-column">
                                <span
                                    className="fa fa-github text-white d-flex justify-content-center flex-column"></span>
                                Sign in with Github
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        );
    }


    handleSubmit = (values) =>  {
        this.props.handleLogIn(values.username,values.password);
    }
}

export default Login;