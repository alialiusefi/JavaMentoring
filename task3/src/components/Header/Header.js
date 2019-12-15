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

    render() {
        let homeButton;
        let homeButtonLink;
        if (this.props.location === '/giftcertificates') {
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