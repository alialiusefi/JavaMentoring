import React from "react";
import "./Footer.css";
import 'bootstrap/dist/css/bootstrap.min.css';


class Footer extends React.Component {
    render() {
        return (
            <footer className="footer fixed-bottom">
                <div className="container text-center">
                    <span className="text"> Gift Certificates 2019 </span>
                </div>
            </footer>
        );
    }
}

export default Footer;