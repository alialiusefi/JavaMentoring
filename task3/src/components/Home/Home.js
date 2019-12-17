import React from "react";
import Header from '../Header/Header'
import Footer from '../Footer/Footer'
import Login from '../Login/Login'
import "./Home.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Redirect, Route, Switch, withRouter} from "react-router-dom";
import {renderToStaticMarkup} from "react-dom/server";
import {Translate, withLocalize} from "react-localize-redux";
import globalTranslations from "../../translations/global.json";
import SearchForm from "./SearchForm";
import SearchMenuForm from "./SearchMenuForm";
import ListOfGiftCertificates from "../ListofGiftCertificates/ListOfGiftCertificates";
import PaginationSize from "./PaginationSize";
import Pagination from "react-js-pagination";
import Signup from "../Signup/Signup";
import jwt_decode from 'jwt-decode';
import AddEditGiftCertificate from "../AddEditGiftCertifcate/AddEditGiftCertificate";

const options = [
    {value: 'ALL', label: 'All GiftCertificates'},
    {value: 'MY', label: 'My GiftCertificates'}
]

const GETALLCERTIFICATES_URL = "http://localhost:8080/api/v1/giftcertificates?page=PAGE_NUMBER&size=PAGE_SIZE";
const GETALLUSERORDERS_URL = "http://localhost:8080/api/v2/users/USER_ID/orders?page=PAGE_NUMBER&size=PAGE_SIZE";
const GETALLGIFTCARDSBYTAGID = "http://localhost:8080/api/v1/giftcertificates?page=PAGE_NUMBER&size=PAGE_SIZE&tagID=TAG_ID_HERE";
const NotFoundRedirect = () => <Redirect to='/not_found'/>;

const EnglishInit = () => [
    {name: "EN", code: "en"},
    {name: "RU", code: "ru"}
];

class Home extends React.Component {

    constructor(props) {
        super(props);
        this.props.initialize({
            languages: EnglishInit(),
            translation: globalTranslations,
            options: {renderToStaticMarkup}
        });
        this.props.addTranslation(globalTranslations);
        this.state = {
            giftCertificates: [],
            pageCount: null,
            pageSize: 5,
            pageNumber: 1,
            certificateDropDownValue: "ALL",
            isLoggedIn: false,
            username: null,
            user_id: null,
            user_role: null,
            selectedGiftCertificate: null
        }

    }


    render() {
        return (
            <div>
                <Header isLoggedIn={this.state.isLoggedIn} username={this.state.username}
                        location={this.props.location.pathname} role={this.state.user_role}/>
                <Switch>
                    <Route path="/login">
                        <Login handleLogIn={this.handleLogIn}/>
                    </Route>
                    <Route path="/signup">
                        <Signup handleSignup={this.handleSignup}/>
                    </Route>
                    <Route path="/add">
                        <AddEditGiftCertificate handleAddCertificate={this.handleAddCertificate}/>
                    </Route>
                    <Route path="/edit">
                        <AddEditGiftCertificate certificate={this.state.selectedGiftCertificate}
                                                handleUpdateCertificate={this.handleUpdateCertificate}/>
                    </Route>
                    <Route path="/giftcertificates">
                        <div className="container text-center">
                            <div className="text h3">
                                <br/> <Translate id="home.title">GiftCertificates</Translate>
                            </div>
                            <div className="container">
                                <div className="row">
                                    <div className="col-4">
                                        <SearchMenuForm certificateDropDownValue={this.state.certificateDropDownValue}
                                                        handleGetAllCertificates={this.handleGetAllCertificates}
                                                        role={this.state.user_role}/>
                                    </div>
                                    <div className="col">
                                        <SearchForm/>
                                    </div>
                                </div>
                            </div>
                            <div className="container">
                                <ListOfGiftCertificates giftcertificates={this.state.giftCertificates}
                                                        handleGetCertificatesByTagName={this.handleGetCertificatesByTagName}
                                                        handleGetAllCertificates={this.handleGetAllCertificates}
                                                        setPageSize={this.setPageSize}
                                                        setPageNumber={this.setPageNumber}
                                                        role={this.state.user_role}
                                                        setSelectedGiftCertificate={this.setSelectedGiftCertificate}
                                />
                            </div>
                            <div className="container-fluid">
                                <div className="row justify-content-center">
                                    <div className="col-3">
                                        <PaginationSize handleChangePageSize={this.handleChangePageSize}/>
                                    </div>
                                    <div className="col-6">
                                        <Pagination activePage={this.state.pageNumber}
                                                    itemsCountPerPage={this.state.pageSize}
                                                    totalItemsCount={this.state.pageCount}
                                                    pageRangeDisplayed={5}
                                                    onChange={this.handleChangePage}
                                                    itemClass="page-item"
                                                    linkClass="page-link">
                                        </Pagination>
                                    </div>
                                </div>
                                <br/>
                            </div>
                        </div>
                    </Route>
                    {/*<Route component={NotFound} path="/not_found"/>
                    <Route component={NotFoundRedirect}/>*/}
                </Switch>
                <Footer/>
            </div>
        );
    }

    handleSignup = (login, password) => {
        const URL = SIGNUP_URL;
        const data = {
            username: login,
            password: password
        }
        fetch(URL,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            }).then(response => {
            const json = response.json();
            if (!response.ok) {
                return Promise.reject(json);
            }
            return json;
        }).then(json => {
            return json;
        }).catch(error => {
            console.log(error);
        });
    }

    setPageSize = (pageSize) => {
        this.setState({pageSize: pageSize});
    };

    setPageNumber = (pageNumber) => {
        this.setState({pageNumber: pageNumber});
    };

    setSelectedGiftCertificate = (certificate) => {
        this.setState({selectedGiftCertificate: certificate},
            this.props.history.push("edit"));
    };

    handleGetAllCertificates = (filterAllOrMy,pageSize,pageNumber) => {
        let arg = filterAllOrMy;
        if (!(filterAllOrMy === "ALL" || filterAllOrMy === "MY")) {
            arg = filterAllOrMy.value;
        }
        if (arg === "ALL") {
            this.setState({certificateDropDownValue: "ALL"});
            let URL = GETALLCERTIFICATES_URL.replace("PAGE_NUMBER", this.state.pageNumber)
                .replace("PAGE_SIZE", this.state.pageSize);
            if (pageSize != null && pageNumber != null) {
                URL = GETALLCERTIFICATES_URL.replace("PAGE_NUMBER",pageNumber)
                    .replace("PAGE_SIZE", pageSize);
            }
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
            this.setState({certificateDropDownValue: "MY"});
            let URLWITHID = GETALLUSERORDERS_URL.replace("USER_ID", this.state.user_id)
                .replace("PAGE_NUMBER", this.state.pageNumber)
                .replace("PAGE_SIZE", this.state.pageSize);
            if (pageSize != null && pageNumber != null) {
                URLWITHID = GETALLUSERORDERS_URL.replace("USER_ID", this.state.user_id).replace("PAGE_NUMBER", pageNumber)
                    .replace("PAGE_SIZE", pageSize);
            }
            const accessToken = localStorage.getItem("accessToken");
            const refreshToken = localStorage.getItem("refreshToken");
            if (accessToken == null || refreshToken == null) {
                alert("unauthorized");
                return;
            }
            document.cookie = "accessToken=" + accessToken;
            document.cookie = "refreshToken=" + refreshToken;
            fetch(URLWITHID,
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                }).then(response => {
                const json = response.json();
                if (!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            }).then(json => {
                let certificates = [];
                json.results.map((order) => {
                    order.giftCertificates.map((certificate) => {
                        certificates.push(certificate);
                    })
                });
                this.setState({giftCertificates: certificates});
                this.setState({pageCount: json.totalResults});
                console.log(this.state.giftCertificates);
                return json;
            }).catch(error => {
                console.log(error);
            });
        }
        this.props.history.push("giftcertificates");
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

    handleChangePageSize = (pageSize) => {
        this.setState({pageSize: pageSize},
            this.handleGetAllCertificates(this.state.certificateDropDownValue,pageSize,this.state.pageNumber));
    };

    handleChangePage = (pageNumber) => {
        this.setState({pageNumber: pageNumber});
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
            const pageCount = Math.ceil(json.totalResults / this.state.pageSize);
            this.setState({pageCount: pageCount});
            console.log(this.state.giftCertificates);
            return json;
        }).catch(error => {
            console.log(error);
        });
    };

    handleLogIn = (login, password) => {
        const URL = LOGIN_URL;
        const data = {
            username: login,
            password: password
        };
        fetch(URL,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            }).then(response => {
            const json = response.json();
            if (!response.ok) {
                return Promise.reject(json);
            }
            return json;
        }).then(json => {
            localStorage.setItem('accessToken', json.accessToken);
            localStorage.setItem('refreshToken', json.refreshToken);
            const decodedToken = jwt_decode(localStorage.getItem('accessToken'));
            this.setState({isLoggedIn: true});
            this.setState({username: decodedToken.sub});
            this.setState({user_id: decodedToken.user_id});
            this.setState({user_role: decodedToken.role});
            return json;
        }).catch(error => {
            console.log(error);
        });
        this.props.history.push("giftcertificates");
    };

    handleUpdateCertificate = (name, description, price, durationTillExpiry, tags, id) => {
        const URL = UPDATE_CERTIIFCATE_URL.replace("CERTIFICATE_ID", id);
        const data = {
            name: name,
            description: description,
            price: price,
            durationTillExpiry: durationTillExpiry,
            tags: tags
        };
        const accessToken = localStorage.getItem("accessToken");
        const refreshToken = localStorage.getItem("refreshToken");
        if (accessToken == null || refreshToken == null) {
            alert("unauthorized");
            return;
        }
        document.cookie = "accessToken=" + accessToken;
        document.cookie = "refreshToken=" + refreshToken;
        const body = JSON.stringify(data);
        fetch(URL,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: body
            }).then(response => {
            console.log(response);
            const json = response.json();
            if (!response.ok) {
                return Promise.reject(json);
            }
            return json;
        }).then(json => {
            console.log(json);
            return json;
        }).catch(error => {
            console.log(error);
        });
        this.props.history.push("giftcertificates");
    };

    handleAddCertificate = (name, description, price, durationTillExpiry, tags) => {
        const URL = ADD_CERTIFICATE_URL;

        const data = {
            name: name,
            description: description,
            price: price,
            durationTillExpiry: durationTillExpiry,
            tags: tags
        };
        const accessToken = localStorage.getItem("accessToken");
        const refreshToken = localStorage.getItem("refreshToken");
        if (accessToken == null || refreshToken == null) {
            alert("unauthorized");
            return;
        }
        document.cookie = "accessToken=" + accessToken;
        document.cookie = "refreshToken=" + refreshToken;
        const body = JSON.stringify(data);
        fetch(URL,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: body
            }).then(response => {
            console.log(response);
            const json = response.json();
            if (!response.ok) {
                return Promise.reject(json);
            }
            return json;
        }).then(json => {
            console.log(json);
            return json;
        }).catch(error => {
            console.log(error);
        });
    }


}

const UPDATE_CERTIIFCATE_URL = "http://localhost:8080/api/v1/giftcertificates/CERTIFICATE_ID";
const ADD_CERTIFICATE_URL = "http://localhost:8080/api/v1/giftcertificates";
const LOGIN_URL = "http://localhost:8080/api/v2/auth/login";
const SIGNUP_URL = "http://localhost:8080/api/v2/auth/signup";
export default withRouter(withLocalize(Home));
