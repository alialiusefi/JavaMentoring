import React from "react";
import "./Login.css";

import LoginForm from "./LoginForm";
import '../../api/apiservice'

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
                <div className=" container-fluid">
                    <LoginForm onSubmit={this.handleSubmit}/>
                    <br/>
                    <div className="row">
                        <div className="col">
                            <a className="btn btn-block btn-social btn-google text-white d-flex justify-content-center flex-column">
                                <span
                                    className="fa fa-github text-white d-flex justify-content-center flex-column"></span>
                                Sign in with Google
                            </a>
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