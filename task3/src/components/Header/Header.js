import React from "react";
import "./Header.css";
import 'bootstrap/dist/css/bootstrap.min.css';


class Header extends React.Component {
    render() {
        return (
            <nav className="navbar navbar-expand-md navbar-static-top navbar-dark bg-light">
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-5">
                            <a className="nav-item" href="#">GiftCertificates</a>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-5">
                            <a className="nav-item text-info" href="#">RU/EN</a>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-5">
                            <a className="nav-item text-info" onClick={login} >Login</a>
                        </div>
                    </div>
                </div>
            </nav>

        );
    }


}

export default Header;