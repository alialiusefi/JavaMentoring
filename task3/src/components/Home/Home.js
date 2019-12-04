import React from "react";
import Header from '../Header/Header'
import Footer from '../Footer/Footer'

import "./Home.css";
import 'bootstrap/dist/css/bootstrap.min.css';


class Home extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <Header />
                <div className="container text-center">
                    <div className="text h1">
                        <br/>GiftCertificates
                    </div>
                </div>
                <Footer />
            </div>
        );
    }
}

export default Home;
