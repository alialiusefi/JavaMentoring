import React from "react";
import "./Header.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link} from "react-router-dom";
import {FormErrors} from '../FormErrors/FormErrors'

class Header extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <nav className="navbar navbar-expand-md navbar-static-top navbar-dark bg-light">
                    <div className="container-fluid">
                        <div className="row">
                            <div className="col-5">
                                <Link className="nav-item" to={"/giftcertificates"}>GiftCertificates</Link>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-5">
                                <Link className="nav-item text-info" to={"/changeLocale"}>RU/EN</Link>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-5">
                                <Link className="nav-item" to={"/login"}>Login</Link>
                            </div>
                        </div>
                    </div>
                </nav>
                {/*<div className="panel panel-default">
                    <FormErrors formErrors={this.state.formErrors}/>
                </div>*/}
            </div>
        );
    }
}

export default Header;