import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import Button from "react-bootstrap/Button";

class ListOfTags extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="container-fluid">
                <div className="row">{
                    this.props.tags.map(
                        (tag) =>
                            <div className="col-md p-1">
                                <Button variant="secondary success" size="sm" onClick={() =>
                                    this.props.handleGetCertificatesByTagName(tag.id,tag.name)}>
                                    {tag.name}
                                </Button>
                            </div>
                    )
                }
                </div>
            </div>
        );
    }
}

export default withLocalize(ListOfTags);
