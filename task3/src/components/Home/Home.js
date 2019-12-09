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
import SearchForm from "./SearchForm";
import SearchMenuForm from "./SearchMenuForm";
import ListOfGiftCertificates from "../ListofGiftCertificates/ListOfGiftCertificates";
import PaginationPage from "./PaginationPage";
import PaginationSize from "./PaginationSize";


const options = [
    {value: 'ALL', label: 'All GiftCertificates'},
    {value: 'MY', label: 'My GiftCertificates'}
]

const GETALLCERTIFICATES_URL  = "http://localhost:8080/api/v1/giftcertificates/";

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
        this.state = {
            giftCertificates: [],
            pageCount : 5
        }

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
                            <div className="text h3">
                                <br/> <Translate id="home.title">GiftCertificates</Translate>
                            </div>
                            <div className="container">
                                <div className="row">
                                    <div className="col-4">
                                        <SearchMenuForm handleGetAllCeritificates={this.handleGetAllCertificates}/>
                                    </div>
                                    <div className="col">
                                        <SearchForm/>
                                    </div>
                                </div>
                            </div>
                            <div className="container">
                                <ListOfGiftCertificates giftcertificates={this.state.giftCertificates}/>
                            </div>
                            <div className="container-fluid">
                                <div className="row justify-content-center">
                                    <div className="col-3">
                                        <PaginationSize />
                                    </div>
                                    <div className="col-6">
                                        <PaginationPage  />
                                    </div>
                                </div>
                                <br/>
                                <div className="row">
                                    <div className="col">
                                        <button className="btn btn-primary">Older</button>
                                    </div>
                                    <div className="col">
                                        <button className="btn btn-primary">Newer</button>
                                    </div>
                                </div>
                                <br/>
                            </div>
                        </div>
                    </Route>
                </Switch>
                <Footer/>
            </div>
        );
    }

    handleGetAllCertificates = (filterAllOrMy) => {
        if(filterAllOrMy.value === "ALL"){
            fetch(GETALLCERTIFICATES_URL,
                {
                    method:'GET',
                    headers:{
                        'Content-Type': 'application/json'
                    }
                }).then(response => {
                const json = response.json();
                if(!response.ok){
                    return Promise.reject(json);
                }
                return json;
            }).then(json => {
                this.setState({giftCertificates : json.results});
                console.log(this.state.giftCertificates);
                return json;
            }).catch(error => {
                console.log(error);
            });
        } else {

        }
    }
}

export default withLocalize(Home);
