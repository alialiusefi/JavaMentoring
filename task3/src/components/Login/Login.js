import React from "react";
import "./Login.css";

import LoginForm from "./LoginForm";
import '../../api/apiservice'

class Login extends React.Component {

    constructor(props) {
        super(props);

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

    handleSubmit(values) {
        const json = login_api(values.username,values.password);
        alert(json);
    }
}

const URL = "http://localhost:8080/api";
const LOGIN_URI = "/v2/auth/login";

function login_api(username, password) {
    fetch(URL + LOGIN_URI,
        {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(
                {
                    username: username,
                    password: password
                }
            )
        }).then(function (resp) {
        console.log("Response status: " + resp.status);
        if (!resp.ok) {
            this.setState({
                error: true,
                errorMessage: resp.json()
            })
        }
        return resp.json();
    }).then((payload) => {
        this.setState(
            {data: payload})
    });

}

export default Login;