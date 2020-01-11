import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import Button from "react-bootstrap/Button";

class DefaultComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="container-fluid text-center">
                <div className="row">
                    <div className="col">
                        <a className="h2">Home</a>
                    </div>
                </div>
            </div>
        );
    }
}

export default withLocalize(DefaultComponent);
