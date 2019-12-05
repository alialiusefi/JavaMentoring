import React from "react";
import "./Login.css";
import {BrowserRouter, Route, Link, Switch} from "react-router-dom";


class Login extends React.Component {

    constructor(props) {
        super(props);
        
        this.state = {
            username: 'a',
            password: 'a',
            formErrors: {username: '', password: ''},
            usernameValid: false,
            passwordValid: false,
            formValid: false
        }

        this.validateField = this.validateField.bind(this);
        this.validateForm = this.validateForm.bind(this);
        this.handleUserInput = this.handleUserInput.bind(this);
    }

    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let usernameValid = this.state.usernameValid;
        let passwordValid = this.state.passwordValid;

        switch (fieldName) {
            case 'username':
                usernameValid = value.match(/^[a-z]{6,30}\d*$/i);
                fieldValidationErrors.username = usernameValid ? '' : ' is invalid';
                break;
            case 'password':
                passwordValid = value.length >= 4;
                fieldValidationErrors.password = passwordValid ? '' : ' is too short';
                break;
            default:
                break;
        }
        this.setState({formErrors: fieldValidationErrors,
            usernameValid: usernameValid,
            passwordValid: passwordValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.emailValid && this.state.passwordValid});
    }

    errorClass(error) {
        return (error.length === 0 ? '' : 'has-error');
    }

    render() {
        return (
            <div className="container">
                <br/>
                <div className=" container-fluid">
                    <div className="row">
                        <div className="col">
                            <h2 className="text text-center">
                                Login
                            </h2>
                            <div className={'form-group ${this.errorClass(this.state.formErrors.email)}'}>
                                <label>Username</label>
                                <input type="text" onChange={this.handleUserInput}
                                       className="form-control" value={this.state.username}
                                       placeholder="Enter Username"/>
                            </div>
                            <div className={"form-group ${this.errorClass(this.state.formErrors.password)}"}>
                                <label>Password</label>
                                <input type="password" onChange={this.handleUserInput}
                                       value={this.state.password}
                                       className="form-control"
                                       placeholder="Enter Password"/>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col">
                            <button className="btn btn-dark text-white d-flex justify-content-center flex-column"
                                    disabled={!this.state.formValid}>
                                Log In
                            </button>
                        </div>
                        <div className="col">
                            <Link to={"/giftcertificates"}
                                  className="btn btn-dark text-white d-flex justify-content-center flex-column">
                                Cancel
                            </Link>
                        </div>
                    </div>
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



}

export default Login;