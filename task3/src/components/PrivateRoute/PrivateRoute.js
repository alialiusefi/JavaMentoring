import React, {Component} from "react";
import {Redirect, Route} from "react-router-dom";

export const PrivateRoute = ({component: Component, userRole, requiredRole, ...rest}) => (
    <Route {...rest}
           render={
               props => userRole === requiredRole ?
                   (<Component {...rest}/>)
                   : (<Redirect
                       to={{
                           pathname: "/giftcertificates",
                           state: {from: props.location}
                       }}
                   />)

           }
    />
);