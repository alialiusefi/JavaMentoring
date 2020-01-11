import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import Button from "react-bootstrap/Button";
import {withRouter} from "react-router-dom";
import qs from "query-string"
import jwt_decode from 'jwt-decode';
import { Redirect } from 'react-router-dom'

class SocialLogin extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn : false
        };
    }

    componentWillMount() {
        const accessToken = qs.parse(this.props.location.search,{ ignoreQueryPrefix: true }).accessToken;
        const refreshToken = qs.parse(this.props.location.search,{ ignoreQueryPrefix: true }).refreshToken;
        if(accessToken != null && refreshToken != null) {
            this.props.setTokens(accessToken, refreshToken);
            this.setState({isLoggedIn : true});

        }
    }

    render() {
        if(this.state.isLoggedIn) {
           return (<Redirect to="/giftcertificates"/>);
        } else {
            this.props.alertError(<Translate id="alerts.tokenerror"/>);
            return (<Redirect to="/login"/>);
        }
    }
}

export default withRouter(withLocalize(SocialLogin));
