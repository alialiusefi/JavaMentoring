import React from "react";
import "./Header.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link} from "react-router-dom";
import ChangeLocale from '../ChangeLocale/ChangeLocale';
import {Translate} from "react-localize-redux";

class Header extends React.Component {

    constructor(props) {
        super(props);
    }

    logOut = (isLoggedIn) => {
        if(isLoggedIn) {
            this.props.handleLogOut();
        }
    };

    render() {
        let homeButton;
        let homeButtonLink;
        if (this.props.location === '/giftcertificates' && this.props.role === "ADMIN") {
            homeButton = "home.addnewgiftcertificates";
            homeButtonLink = "/add";
        } else {
            homeButton = "home.title";
            homeButtonLink = "/giftcertificates";
        }



        return (
            <div>
                <nav className="navbar navbar-expand-md navbar-static-top navbar-dark bg-light">
                    <div className="container-fluid">
                        <div className="row">
                            <div className="col-5">
                                <Link className="nav-item" to={homeButtonLink}>
                                    <Translate id={homeButton}/>
                                </Link>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-5">
                                <ChangeLocale/>
                            </div>
                        </div>
                        <div>
                            <Translate id="header.welcome"/> {this.props.isLoggedIn ? this.props.username : 'Guest'}
                        </div>
                        <div className="row">
                            <div className="col-auto">
                                <Link className="nav-item" to={"/login"} onClick={this.logOut}>
                                    <Translate id="home.loginlogout"> Login/Logout </Translate>
                                </Link>
                            </div>
                            <div className={"col-auto"}>
                                <Link className="nav-item" to={"/signup"}>
                                    <Translate id="signup.signupbutton"/>
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