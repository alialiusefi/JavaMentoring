import React from "react";
import "./Header.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link} from "react-router-dom";
import ChangeLocale from '../ChangeLocale/ChangeLocale';
import {Translate} from "react-localize-redux";

class Header extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: false,
            username : 'USERNAME HERE'
        }
    }

    render() {
        const isLoggedIn = this.state.isLoggedIn;

        return (
            <div>
                <nav className="navbar navbar-expand-md navbar-static-top navbar-dark bg-light">
                    <div className="container-fluid">
                        <div className="row">
                            <div className="col-5">

                                <Link className="nav-item" to={"/giftcertificates"}>
                                    <Translate id="home.title">GiftCertificates</Translate>
                                </Link>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-5">
                                <ChangeLocale/>
                            </div>
                        </div>
                        <div>
                            Welcome {isLoggedIn ? this.state.username : 'Guest'}
                        </div>
                        <div className="row">
                            <div className="col-5">
                                <Link className="nav-item" to={"/login"}>
                                    <Translate id="home.loginlogout"> Login/Logout </Translate>
                                </Link>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
        );


    }

}

export default Header;