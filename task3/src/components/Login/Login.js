import React from "react";
import "./Login.css";

import LoginForm from "./LoginForm";
import '../../api/apiservice'
import GoogleLogin from "react-google-login";
import {createPortal} from "react-dom";


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

        return (
            <div className="container">
                <br/>
                <div className="center container-fluid">
                    <LoginForm onSubmit={this.handleSubmit}/>
                    <br/>
                    <div className="row">
                        <div className="col">
                            <a href="http://localhost:8080/api/oauth2/authorize/google?redirect_uri=http://localhost:3000/sociallogin"
                               className="btn btn-block btn-social btn-google text-white d-flex justify-content-center flex-column">
                                <span
                                    className="fa fa-github text-white d-flex justify-content-center flex-column"></span>
                                Sign in with Google
                            </a>
                        </div>
                        <div className="col">
                            <a  href="http://localhost:8080/api/oauth2/authorize/github?redirect_uri=http://localhost:3000/sociallogin"
                                className="btn btn-block btn-social btn-github text-white d-flex justify-content-center flex-column">
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