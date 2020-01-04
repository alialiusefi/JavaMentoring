import React from "react";
import Header from '../Header/Header'
import Footer from '../Footer/Footer'
import Login from '../Login/Login'
import "./Home.css";
import qs from "query-string"
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
import SocialLogin from "../SocialLogin/SocialLogin";

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
        let certificateDropDownValue = "ALL";
        let pageNumber = qs.parse(this.props.location.search, {ignoreQueryPrefix: true}).page;
        let pageSize = qs.parse(this.props.location.search, {ignoreQueryPrefix: true}).size;
        let search = qs.parse(this.props.location.search, {ignoreQueryPrefix: true}).search;
        if (pageSize == null || !pageSize.toString().match("^\\d*$")) {
            pageSize = 5;
        }
        if (pageNumber == null ||  !pageSize.toString().match("^\\d*$")) {
            pageNumber = 1;

        }
        if (search == null) {
            search = "";
        } else {
            certificateDropDownValue = "SEARCH";
        }
        this.state = {
            isRendering: false,
            giftCertificates: [],
            pageCount: null,
            pageSize: pageSize,
            pageNumber: pageNumber,
            certificateDropDownValue: certificateDropDownValue,
            isLoggedIn: false,
            username: null,
            user_id: null,
            user_role: null,
            selectedGiftCertificate: null,
            searchField: search,
            tagID: null
        };

        const locale = localStorage.getItem("locale");
        if (locale == null) {
            localStorage.setItem("locale", "en");
        }
        this.props.setActiveLanguage(locale);
    }

    componentDidMount() {
        const lang = localStorage.getItem("locale");
        this.props.setActiveLanguage(lang);
        this.handleSetLoginDetails();
    }

    render() {

        const renderSearchForm = () => {
            if (this.state.certificateDropDownValue === "MY") {
                return;
            } else {
                return (<SearchForm initialValues={{searchField: this.state.searchField}}
                                    onSubmit={this.handleSearch}
                                    handleGetAllCertificates={this.handleGetAllCertificates}
                />);
            }
        }


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


                    <Route location={this.props.location} path="/sociallogin">
                        <SocialLogin setTokens={this.setTokens} alertError={(message) => {
                            Alert.error(message);
                        }}/>
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
                                        {renderSearchForm()}
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
                                                        searchField={this.props.searchField}
                                                        handleSearch={this.handleSearch}
                                                        certificateDropDownValue={this.state.certificateDropDownValue}
                                                        setSelectedGiftCertificate={this.setSelectedGiftCertificate}
                                                        handleDeleteCertificate={this.handleDeleteCertificateModal}
                                                        handleBuyCertificate={this.handleBuyCertificate}
                                                        pageNumber={this.state.pageNumber}
                                                        pageSize={this.state.pageSize}
                                                        setDropdownValue={(value) => {
                                                            this.setState({certificateDropDownValue: value});
                                                        }}
                                />
                            </div>
                            <div className="container-fluid">
                                <div className="row justify-content-center">
                                    <div className="col-3">
                                        <PaginationSize handleChangePageSize={this.handleChangePageSize}
                                                        paginationSize={this.state.pageSize}/>
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
                    <PrivateRoute userRole={this.state.user_role}
                                  requiredRole={"ADMIN"}
                                  path="/add" component={AddEditGiftCertificate}
                                  handleAddCertificate={this.handleAddCertificate}
                                  handleSetLoginDetails={this.handleSetLoginDetails}
                    >
                    </PrivateRoute>
                    <PrivateRoute userRole={this.state.user_role}
                                  requiredRole={"ADMIN"}
                                  path="/edit" component={AddEditGiftCertificate}
                                  certificate={this.state.selectedGiftCertificate}
                                  handleUpdateCertificate={this.handleUpdateCertificate}
                                  handleSetLoginDetails={this.handleSetLoginDetails}>
                    </PrivateRoute>
                    {/*<Route path="/" component={DefaultComponent}/>*/}
                    <Route component={NotFound}/>
                </Switch>
                <Footer/>
                <Alert stack={{limit: 3}} position={"top"}/>
            </div>
        );
    }

    handleSetLoginDetails = (redirect) => {
        const now = new Date();
        const accessToken = localStorage.getItem('accessToken');
        let decodedToken;
        if (accessToken != null) {
            decodedToken = jwt_decode(accessToken);
        } else {
            if (redirect) {
                this.props.history.push("/giftcertificates");
                Alert.error(<Translate id="alerts.notloggedin"/>);
                this.handleLogOut();
            }
        }
        if (decodedToken != null) {
            if (!(decodedToken.exp < now.getTime() / 1000)) {
                this.setState({isLoggedIn: true});
                this.setState({username: decodedToken.sub});
                this.setState({user_id: decodedToken.user_id});
                this.setState({user_role: decodedToken.role});
            }
        }
    }

    setTokens = (accessToken, refreshToken) => {
        const now = new Date();
        let decodedAccessToken;
        let decodedRefreshToken;
        decodedAccessToken = jwt_decode(accessToken);
        decodedRefreshToken = jwt_decode(refreshToken);
        if (decodedAccessToken != null && decodedRefreshToken != null) {
            if (!(decodedAccessToken.exp < now.getTime() / 1000)) {
                this.setState({isLoggedIn: true});
                this.setState({username: decodedAccessToken.sub});
                this.setState({user_id: decodedAccessToken.user_id});
                this.setState({user_role: decodedAccessToken.role});
                localStorage.setItem("accessToken", accessToken);
                localStorage.setItem("refreshToken", refreshToken);
            }
        }
    };


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
            Alert.success(<Translate id="alerts.boughtcertificate"/>)
        }).catch(error => {
            console.log(error);
            Alert.error(error.message);
        });
        this.handleGetAllCertificates("MY", this.state.pageSize, 1);
    };

    handleSearch = (values) => {
        let search = "";
        if (values.searchField == null || values.searchField.length === 0) {
            if (this.state.searchField == null || this.state.searchField.length === 0) {
                Alert.warning(<Translate id="alerts.searchfieldempty"/>);
                return;
            }
        } else {
            this.setState({searchField: values.searchField});
            search = values.searchField;

        }
        if (search == null || search.length === 0) {
            Alert.warning(<Translate id="alerts.searchfieldempty"/>);
            return;
        }
        let searchValueTokens = search.split(" ");
        let tagsToSearch = [];
        let nameOrDescription;
        for (var i = 0; i < searchValueTokens.length; i++) {
            let token = searchValueTokens[i];
            if (token.charAt(0) === "@" && token.charAt(1) === "{"
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
        this.setState({certificateDropDownValue: "SEARCH"});
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
            this.props.history.push("giftcertificates?page=" + this.state.pageNumber + "&size=" + this.state.pageSize + "&search=" + this.state.searchField);
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
            if (!response.ok) {
                response.json().then(
                    (json) => {
                        Alert.error(json.errors[0]);
                    }
                );
            } else {
                this.props.history.push("/login");
                Alert.success(<Translate id="alerts.signedup"/>);
                return response.json();
            }
        }).then(json => {
            return json;
        }).catch(error => {
            Alert.error(error.message);
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
            this.handleGetAllCertificates(this.state.certificateDropDownValue,
                this.state.pageSize, this.state.pageNumber);
            const oldpageNumber = this.state.pageNumber;
            if (this.state.giftCertificates.length === 1) {
                this.setState({pageNumber: oldpageNumber - 1});
                this.handleGetAllCertificates(this.state.certificateDropDownValue,
                    this.state.pageSize, oldpageNumber - 1);
            }
        }).catch(error => {
            Alert.error(error.message);
            console.log(error);
        });

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
                this.props.history.push("giftcertificates?page=" + this.state.pageNumber + "&size=" + this.state.pageSize);
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
    };

    handleGetCertificatesByTagName = (tagID, tagName) => {
        this.setState({tagID: tagID});
        const search = qs.parse(this.props.location.search, {ignoreQueryPrefix: true}).search;
        if (search == null) {
            this.setState({pageNumber: 1});
        }
        const URL = GETALLGIFTCARDSBYTAGID.replace("TAG_ID_HERE", tagID)
            .replace("PAGE_NUMBER", this.state.pageNumber)
            .replace("PAGE_SIZE", this.state.pageSize);
        const searchField = "@{" + tagName + "}";
        this.setState({searchField: searchField});
        this.setState({certificateDropDownValue: "TAG"});
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
            this.props.history.push("giftcertificates?page=" + this.state.pageNumber + "&size=" + this.state.pageSize + "&search=" + this.state.searchField);
            return json;
        }).catch(error => {
            console.log(error);
        });
    };

    handleChangePageSize = (pageSize) => {
        if (this.state.certificateDropDownValue === "TAG") {
            const length = this.state.searchField.length - 1;
            this.setState({pageSize: pageSize}, this.handleGetCertificatesByTagName(this.state.searchField.substr(2, this.state.searchField.length - 1)));
            return;
        }
        if (this.state.certificateDropDownValue === "SEARCH") {
            this.setState({pageSize: pageSize}, this.handleSearch({searchField: this.state.searchField}));
            return;
        }
        this.setState({pageSize: pageSize},
            this.handleGetAllCertificates(this.state.certificateDropDownValue, pageSize, this.state.pageNumber));

    };

    handleChangePage = (pageNumber) => {
        this.setState({pageNumber: pageNumber});
        if (this.state.certificateDropDownValue === "TAG") {
            this.setState({pageNumber: pageNumber},
                this.handleGetCertificatesByTagName(this.state.tagID, this.state.searchField.substr(2, this.state.searchField.length - 2)));
            return;
        }
        if (this.state.certificateDropDownValue === "SEARCH") {
            this.setState({pageNumber: pageNumber},
                this.handleSearch({searchField: this.state.searchField}));
            return;
        }
        this.setState({pageNumber: pageNumber},
            this.handleGetAllCertificates(this.state.certificateDropDownValue, this.state.pageSize, pageNumber));

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
        document.cookie = "accessToken=" + accessToken;
        document.cookie = "refreshToken=" + refreshToken;
        if (accessToken == null || refreshToken == null) {
            Alert.error(<Translate id="alerts.notloggedin"/>);
            return;
        }
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
        this.props.history.push("/giftcertificates");
        this.handleGetAllCertificates(this.state.certificateDropDownValue,
            this.state.pageSize, this.state.pageNumber);
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
