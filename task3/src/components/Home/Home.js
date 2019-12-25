import React from "react";
import Header from '../Header/Header'
import Footer from '../Footer/Footer'
import Login from '../Login/Login'
import "./Home.css";

import 'bootstrap/dist/css/bootstrap.min.css';
import {Route, Switch, withRouter} from "react-router-dom";
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
import NotFound from "../NotFound/NotFound";
import DefaultComponent from "../DefaultComponent/NotFound";
import Alert from 'react-s-alert';
import {PrivateRoute} from "../PrivateRoute/PrivateRoute";

const options = [
    {value: 'ALL', label: 'All GiftCertificates'},
    {value: 'MY', label: 'My GiftCertificates'}
];

const GETALLCERTIFICATES_URL = "http://localhost:8080/api/v1/giftcertificates?page=PAGE_NUMBER&size=PAGE_SIZE&sortByDate=-1";
const GETALLUSERORDERS_URL = "http://localhost:8080/api/v2/users/USER_ID/orders?page=PAGE_NUMBER&size=PAGE_SIZE";
const GETALLGIFTCARDSBYTAGID = "http://localhost:8080/api/v1/giftcertificates?page=PAGE_NUMBER&size=PAGE_SIZE&tagID=TAG_ID_HERE";
const BUY_CERTIFICATE_URL = "http://localhost:8080/api/v2/users/USER_ID/orders";
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
            isRendering: false,
            giftCertificates: [],
            pageCount: null,
            pageSize: 5,
            pageNumber: 1,
            certificateDropDownValue: "ALL",
            isLoggedIn: false,
            username: null,
            user_id: null,
            user_role: null,
            selectedGiftCertificate: null,

        };
        const locale = localStorage.getItem("locale");
        if (locale == null) {
            localStorage.setItem("locale", "en");
        }
        this.props.setActiveLanguage(locale);
    }

    componentDidMount() {
        const now = new Date();
        const accessToken = localStorage.getItem('accessToken');
        let decodedToken;
        if (accessToken != null) {
            decodedToken = jwt_decode(accessToken);
        }
        if (decodedToken != null) {
            if (!(decodedToken.exp < now.getTime() / 1000)) {
                this.setState({isLoggedIn: true});
                this.setState({username: decodedToken.sub});
                this.setState({user_id: decodedToken.user_id});
                this.setState({user_role: decodedToken.role});
            }
        }
        const lang = localStorage.getItem("locale");
        this.props.setActiveLanguage(lang);
    }

    render() {
        return (
            <div>
                <Header isLoggedIn={this.state.isLoggedIn} username={this.state.username}
                        location={this.props.location.pathname} role={this.state.user_role}
                        handleLogOut={() => {
                            localStorage.removeItem("accessToken");
                            localStorage.removeItem("refreshToken");
                            this.setState({
                                    isLoggedIn: false,
                                    user_role: null,
                                    username: null,
                                    user_id: null
                                }, this.props.history.push("login")
                            );
                        }}/>
                <Switch>
                    <Route path="/login">
                        <Login handleLogIn={this.handleLogIn}/>
                    </Route>
                    <Route path="/signup">
                        <Signup handleSignup={this.handleSignup}/>
                    </Route>
                    <PrivateRoute userRole={this.state.user_role}
                                  requiredRole={"ADMIN"}
                                  path="/add" component={AddEditGiftCertificate}
                                  handleAddCertificate={this.handleAddCertificate}>
                    </PrivateRoute>
                    <PrivateRoute userRole={this.state.user_role}
                                  requiredRole={"ADMIN"}
                                  path="/edit" component={AddEditGiftCertificate}
                                  certificate={this.state.selectedGiftCertificate}
                                  handleUpdateCertificate={this.handleUpdateCertificate}>
                    </PrivateRoute>
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
                                        <SearchForm onSubmit={this.handleSearch}/>
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
                                                        handleDeleteCertificate={this.handleDeleteCertificateModal}
                                                        handleBuyCertificate={this.handleBuyCertificate}
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
                    <Route path="/" component={DefaultComponent}/>
                    <Route path='*' exact={true} component={NotFound}/>
                </Switch>
                <Footer/>
                <Alert stack={{limit: 3}} position={"top"}/>
            </div>
        );
    }

    handleBuyCertificate = (certificate) => {
        const URL = BUY_CERTIFICATE_URL
            .replace("USER_ID", this.state.user_id);
        const accessToken = localStorage.getItem("accessToken");
        const refreshToken = localStorage.getItem("refreshToken");
        if (accessToken == null || refreshToken == null) {
            Alert.error(<Translate id="alerts.notloggedin"/>);
            return;
        }
        document.cookie = "accessToken=" + accessToken;
        document.cookie = "refreshToken=" + refreshToken;
        const data = {
            giftCertificates: [certificate.id]
        };
        fetch(URL,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify(data)
            }).then(response => {
            if (!response.ok) {
                const json = response.json();
                const errors = json.errors;
                Alert.error(errors);
                return Promise.reject(response.json());
            }
        }).catch(error => {
            console.log(error);
            Alert.error(error.message);
        });
    };

    handleSearch = (values) => {
        if (values.searchField == null || values.searchField.length == 0) {
            Alert.warning(<Translate id="alerts.searchfieldempty"/>);
            return;
        }
        let searchValueTokens = values.searchField.split(" ");
        let tagsToSearch = [];
        let nameOrDescription;
        for (var i = 0; i < searchValueTokens.length; i++) {
            let token = searchValueTokens[i];
            if (token.charAt(0) === "#" && token.charAt(1) === "{"
                && token.charAt(token.length - 1) === "}") {
                tagsToSearch.push(token.substr(2, token.length - 3));
            } else {
                nameOrDescription = token;
            }
        }
        console.log(tagsToSearch);
        let URL = GETALLCERTIFICATES_URL.replace("PAGE_NUMBER", this.state.pageNumber)
            .replace("PAGE_SIZE", this.state.pageSize);
        if (nameOrDescription != null) {
            URL += "&" + "giftCertificateName=" + nameOrDescription;
            URL += "&" + "giftCertificateDesc=" + nameOrDescription;
        }
        if (tagsToSearch.length !== 0) {
            URL += "&" + "tagName=";
            for (var i = 0; i < tagsToSearch.length; i++) {
                if (i === tagsToSearch.length - 1) {
                    URL += tagsToSearch[i];
                    continue;
                }
                URL += tagsToSearch[i] + ","
            }
        }
        console.log(URL);
        fetch(URL,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
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

    handleSignup = (login, password) => {
        const URL = SIGNUP_URL;
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
            Alert.success(<Translate id="alerts.signedup"/>);
            return json;
        }).then(json => {
            return json;
        }).catch(error => {
            console.log(error);
        });
    };

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

    handleLogOut = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        this.setState({
                isLoggedIn: false,
                user_role: null,
                username: null,
                user_id: null
            }, this.props.history.push("login")
        );
        /*this.setState({user_role: null});
        this.setState({username: null});
        this.setState({user_id: null});
        */
    };

    handleDeleteCertificateModal = (certificate) => {
        const URL = DELETE_CERTIFICATE_URL
            .replace("CERTIFICATE_ID", certificate.id);
        const accessToken = localStorage.getItem("accessToken");
        const refreshToken = localStorage.getItem("refreshToken");
        if (accessToken == null || refreshToken == null) {
            Alert.error("Test");
            return;
        }
        document.cookie = "accessToken=" + accessToken;
        document.cookie = "refreshToken=" + refreshToken;
        fetch(URL,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            }).then(response => {
            if (!response.ok) {
                return Promise.reject(response.json());
            }

        }).catch(error => {
            Alert.error(error.message);
            console.log(error);
        });
        this.setState({isRendering: this.isRendering});
    };


    handleGetAllCertificates = (filterAllOrMy, pageSize, pageNumber) => {
        let arg = filterAllOrMy;
        if (!(filterAllOrMy === "ALL" || filterAllOrMy === "MY")) {
            arg = filterAllOrMy.value;
        }
        if (arg === "ALL") {
            this.setState({certificateDropDownValue: "ALL"});
            let URL = GETALLCERTIFICATES_URL.replace("PAGE_NUMBER", this.state.pageNumber)
                .replace("PAGE_SIZE", this.state.pageSize);
            if (pageSize != null && pageNumber != null) {
                URL = GETALLCERTIFICATES_URL.replace("PAGE_NUMBER", pageNumber)
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
                Alert.error(error.message);
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
                Alert.error("Cannot Authorize!");
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
                Alert.error(error.message);
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
            this.handleGetAllCertificates(this.state.certificateDropDownValue, pageSize, this.state.pageNumber));
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
            if (!response.ok) {
                response.json().then(
                    (json) => {
                        Alert.error(json.errors[0]);
                    }
                );
            } else {
                const json = response.json();
                Alert.success(<Translate id="alerts.loggedIn"/>);
                return json;
            }
        }).then(json => {
            if (json == null) {
                Promise.reject(json);
            } else {
                if (json.accessToken != null && json.refreshToken != null) {
                    localStorage.setItem('accessToken', json.accessToken);
                    localStorage.setItem('refreshToken', json.refreshToken);
                    const decodedToken = jwt_decode(localStorage.getItem('accessToken'));
                    this.setState({isLoggedIn: true});
                    this.setState({username: decodedToken.sub});
                    this.setState({user_id: decodedToken.user_id});
                    this.setState({user_role: decodedToken.role});
                    this.props.history.push("giftcertificates");
                }
            }
            return json;
        }).catch(error => {
            Alert.error(error.message);
            console.log(error);
        });
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
            Alert.success(<Translate id="alerts.editcertificatesuccess"/>);
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
            Alert.error("Cannot Authorize!");
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
            Alert.success(<Translate id="alerts.addcertificatesuccess"/>);
            return json;
        }).then(json => {
            console.log(json);
            return json;
        }).catch(error => {
            console.log(error);
        });
    }


}

const DELETE_CERTIFICATE_URL = "http://localhost:8080/api/v1/giftcertificates/CERTIFICATE_ID";
const UPDATE_CERTIIFCATE_URL = "http://localhost:8080/api/v1/giftcertificates/CERTIFICATE_ID";
const ADD_CERTIFICATE_URL = "http://localhost:8080/api/v1/giftcertificates";
const LOGIN_URL = "http://localhost:8080/api/v2/auth/login";
const SIGNUP_URL = "http://localhost:8080/api/v2/auth/signup";
export default withLocalize(withRouter(Home));
