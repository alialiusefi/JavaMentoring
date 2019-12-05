import React from "react";
import Header from '../Header/Header'
import Footer from '../Footer/Footer'
import Login from '../Login/Login'
import "./Home.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter,Route,Link,Switch} from "react-router-dom";


class Home extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <Header />
                <Switch>
                    <Route path="/login">
                        <Login />
                    </Route>
                    <Route path="/">
                    <div className="container text-center">
                        <div className="text h1">
                            <br/>GiftCertificates
                        </div>
                    </div>
                    </Route>
                </Switch>
                <Footer />
            </div>
        );
    }
}

export default Home;
