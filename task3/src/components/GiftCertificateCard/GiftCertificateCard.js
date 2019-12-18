import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import ListGroup from "react-bootstrap/ListGroup";
import Card from "react-bootstrap/Card";
import ListOfTags from "../ListOfTags/ListOfTags";
import Button from "react-bootstrap/Button";
import DeleteCertificateModal from "../DeleteCertificateModal/DeleteCertificateModal";


class GiftCertificateCard extends React.Component {

    constructor(props) {
        super(props);
    }


    render() {

        const editButton = () => {
            if (this.props.role === "ADMIN") {
                return (
                    <Button onClick={() => this.props.setSelectedGiftCertificate(this.props.certificate)}
                        /*href={"/edit"}*/
                            className="btn btn-dark text-white d-flex justify-content-center flex-column">
                        <Translate id="button.edit"/>
                    </Button>
                );
            }
        };

        const deleteButton = () => {
            if (this.props.role === "ADMIN") {
                return (
                    <DeleteCertificateModal certificate={this.props.certificate}
                                            handleDeleteCertificate={this.props.handleDeleteCertificate}/>
                );
            }
        };

        const buyButton = () => {
            if (this.props.role === "ADMIN" ||
                this.props.role === "USER") {
                return (
                    <button
                        className="btn btn-dark text-white d-flex justify-content-center flex-column">
                        <Translate id="button.buy"/>
                    </button>
                );
            }
        };

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
                                    {this.props.certificate.dateOfCreation}
                                </div>
                            </div>
                        </div>
                    </ListGroup.Item>
                    <ListGroup.Item>
                        <div className="container">
                            <div className="row">
                                <ListOfTags tags={this.props.certificate.tags}
                                            handleGetCertificatesByTagName={this.props.handleGetCertificatesByTagName}/>
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
                                    {editButton()}
                                </div>
                                <div className="col">
                                    {
                                        deleteButton()
                                    }
                                </div>
                                <div className="col">
                                    {buyButton()}
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
