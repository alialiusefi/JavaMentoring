import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import ListGroup from "react-bootstrap/ListGroup";
import Card from "react-bootstrap/Card";


class GiftCertificateCard extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (

                <Card>
                    <ListGroup variant="flush">
                        <ListGroup.Item>
                            <div className="container">
                                <div className="row">
                                    <div className="col">
                                        {this.props.certificate.name}
                                    </div>
                                    <div className="col">
                                        {this.props.certificate.date}
                                    </div>
                                </div>
                            </div>
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <div className="container">
                                <div className="row">
                                    TAGS HERE
                                </div>
                                <div className="row">
                                    {this.props.certificate.description}
                                </div>
                            </div>
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <div className="container-fluid">
                                <div className="row">
                                    <div className="col">
                                        <button
                                            className="btn btn-dark text-white d-flex justify-content-center flex-column">
                                            Edit
                                        </button>
                                    </div>
                                    <div className="col">

                                        <button
                                            className="btn btn-dark text-white d-flex justify-content-center flex-column">
                                            Delete
                                        </button>
                                    </div>
                                    <div className="col">
                                        <button
                                            className="btn btn-dark text-white d-flex justify-content-center flex-column">
                                            Buy
                                        </button>
                                    </div>
                                    <div className="h-5">
                                        <label>{this.props.certificate.price} BYN</label>
                                    </div>
                                </div>
                            </div>
                        </ListGroup.Item>
                    </ListGroup>
                </Card>
        );
    }
}

export default withLocalize(GiftCertificateCard);
