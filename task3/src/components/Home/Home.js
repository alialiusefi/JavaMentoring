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

const GETALLCERTIFICATES_URL = "http://localhost:8080/api/v1/giftcertificates?page=PAGE_NUMBER&size=PAGE_SIZE";
const GETALLUSERORDERS_URL = "http://localhost:8080/api/v2/users/USER_ID/orders/";
const GETALLGIFTCARDSBYTAGID = "http://localhost:8080/api/v1/giftcertificates?page=PAGE_NUMBER&size=PAGE_SIZE&tagID=TAG_ID_HERE";

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
            pageCount: 5,
            pageSize: 5,
            pageNumber: 1
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
                                        <SearchMenuForm handleGetAllCertificates={this.handleGetAllCertificates}/>
                                    </div>
                                    <div className="col">
                                        <SearchForm/>
                                    </div>
                                </div>
                            </div>
                            <div className="container">
                                <ListOfGiftCertificates giftcertificates={this.state.giftCertificates}
                                                        handleGetCertificatesByTagName={this.handleGetCertificatesByTagName}/>
                            </div>
                            <div className="container-fluid">
                                <div className="row justify-content-center">
                                    <div className="col-3">
                                        <PaginationSize/>
                                    </div>
                                    <div className="col-6">
                                        <PaginationPage totalResults={this.state.pageCount}
                                                        changePage={this.handleChangePage}/>
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
        if (filterAllOrMy.value === "ALL") {
            const URL = GETALLCERTIFICATES_URL.replace("PAGE_NUMBER", this.state.pageNumber).replace("PAGE_SIZE", this.state.pageSize);
            fetch(URL,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(response => {
                const json = response.json();
                if (!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            }).then(json => {
                this.setState({giftCertificates: json.results});
                this.setState({pageCount: json.totalResults});
                console.log(this.state.giftCertificates);
                return json;
            }).catch(error => {
                console.log(error);
            });
        } else {
            /*todo: replace "2" with dynamic userID and handle pagination*/
            const URLWITHID = GETALLUSERORDERS_URL.replace("USER_ID", "2");
            fetch(URLWITHID,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(response => {
                const json = response.json();
                if (!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            }).then(json => {
                this.setState({giftCertificates: json.results.giftCertificates});
                this.setState({pageCount: json.totalResults});
                console.log(this.state.giftCertificates);
                return json;
            }).catch(error => {
                console.log(error);
            });
        }
    };

    handleGetCertificatesByTagName = (tagID, tagName) => {
        const URL = GETALLGIFTCARDSBYTAGID.replace("TAG_ID_HERE", tagID)
            .replace("PAGE_NUMBER", this.state.pageNumber)
            .replace("PAGE_SIZE", this.state.pageSize);
        fetch(URL,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
            const json = response.json();
            if (!response.ok) {
                return Promise.reject(json);
            }
            return json;
        }).then(json => {
            this.setState({giftCertificates: json.results});
            this.setState({pageCount: json.totalResults});
            console.log(this.state.giftCertificates);
            return json;
        }).catch(error => {
            console.log(error);
        });
    };

    handleChangePage = (pageNumber) => {
        this.setState({pageNumber : pageNumber});
        const URL = GETALLCERTIFICATES_URL
            .replace("PAGE_NUMBER", this.state.pageNumber)
            .replace("PAGE_SIZE", this.state.pageSize);
        fetch(URL,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
            const json = response.json();
            if (!response.ok) {
                return Promise.reject(json);
            }
            return json;
        }).then(json => {
            this.setState({giftCertificates: json.results});
            const pageCount =  Math.ceil(json.totalResults / this.state.pageSize);
            this.setState({pageCount: pageCount});
            console.log(this.state.giftCertificates);
            return json;
        }).catch(error => {
            console.log(error);
        });
    };

}


export default withLocalize(Home);
