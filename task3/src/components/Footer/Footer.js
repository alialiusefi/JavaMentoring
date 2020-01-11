import React from "react";
import "./Footer.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Translate} from "react-localize-redux";


class Footer extends React.Component {
    render() {
        return (
            <footer className="footer fixed-bottom">
                <div className="container text-center">
                    <span className="text"> <Translate id="home.title">
                            GiftCertificates
                        </Translate> 2019 </span>
                </div>
            </footer>
        );
    }
}

export default Footer;