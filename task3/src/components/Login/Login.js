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

    handleSubmit(values) {
        try {
            login_api(values.username, values.password);
        } catch (err) {
            alert(err);
        }

    }
}

const LOGIN_URI = "http://localhost:8080/api/v2/auth/login";


function login_api(username, password) {
    (async () => {
        await fetch(LOGIN_URI, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: username, password: password})
        }).then(async rawResponse => {
            if(!rawResponse.ok){
                console.log(JSON.stringify(rawResponse.json()));
                throw Error(JSON.stringify(rawResponse.json()));
            }
            const content = await rawResponse.json();
            const accessToken = content.accessToken;
            const refreshToken = content.refreshToken;
            return content;
        }).catch(err => {
            console.log(err);
        });
    })();
}

export default Login;