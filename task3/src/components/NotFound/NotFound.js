import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import Button from "react-bootstrap/Button";

class NotFound extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="container-fluid text-center">
                <div className="row">
                    <a className="h2">404 Not Found</a>
                </div>
            </div>
        );
    }
}

export default withLocalize(NotFound);
