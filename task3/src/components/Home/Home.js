import React from "react";
import Header from '../Header/Header'
import Footer from '../Footer/Footer'
import Login from '../Login/Login'
import "./Home.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter, Route, Link, Switch} from "react-router-dom";
import {renderToStaticMarkup} from "react-dom/server";
import {withLocalize, Translate} from "react-localize-redux";
import globalTranslations from "../../translations/global.json";

class Home extends React.Component {

    constructor(props) {
        super(props);
        this.props.initialize({
            languages: [
                {name: "EN", code: "en"},
                {name: "RU", code: "ru"}
            ],
            translation: globalTranslations,
            options: {renderToStaticMarkup}
        });
        this.props.addTranslation(globalTranslations);
    }


    render() {
        return (
            <div>
                <Header/>
                <Switch>
                    <Route path="/login">
                        <Login/>
                    </Route>
                    <Route path="/">
                        <div className="container text-center">
                            <div className="text h1">
                                <br/> <Translate id="home.title">GiftCertificates</Translate>
                            </div>
                        </div>
                    </Route>
                </Switch>
                <Footer/>
            </div>
        );
    }
}

export default withLocalize(Home);
