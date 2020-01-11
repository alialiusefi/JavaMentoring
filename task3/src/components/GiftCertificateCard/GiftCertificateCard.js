import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import ListGroup from "react-bootstrap/ListGroup";
import Card from "react-bootstrap/Card";
import ListOfTags from "../ListOfTags/ListOfTags";
import Button from "react-bootstrap/Button";
import DeleteCertificateModal from "../DeleteCertificateModal/DeleteCertificateModal";
import BuyCertificateModal from "../BuyCertificateModal/BuyCertificateModal";
import moment from "moment";


class GiftCertificateCard extends React.Component {

    constructor(props) {
        super(props);
    }


    render() {

        const editButton = () => {
            if (this.props.role === "ADMIN") {
                return (
                    <Button onClick={() => this.props.setSelectedGiftCertificate(this.props.certificate)}
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
                    <BuyCertificateModal certificate={this.props.certificate}
                                         handleBuyCertificate={this.props.handleBuyCertificate}
                                         className="btn btn-dark text-white d-flex justify-content-center flex-column">
                        <Translate id="button.buy"/>
                    </BuyCertificateModal>
                );
            }
        };

        const renderDescription = (isModal, description) => {
            if (isModal) {
                return (
                    <p className="overflow-auto">{description}</p>
                );
            } else {
                if (description.length > 25) {
                    const smallDescription = description.substr(0, 25);
                    const finalDescription = smallDescription + "...";
                    return (
                        <p className="overflow-auto">{finalDescription}</p>
                    );
                }
            }
            return (<p className="overflow-auto">{description}</p>);
        };
        return (
            <Card>
                <ListGroup variant="flush">
                    <ListGroup.Item onClick={() => {
                        if(!this.props.isModal) {
                            this.props.setStates(this.props.modal,this.props.certificate, this.props.isModal)
                        }
                    }}>
                        <div className="container">
                            <div className="row">
                                <div className="col">
                                    {this.props.certificate.name}
                                </div>
                                <div className="col">
                                    {moment(this.props.certificate.dateOfCreation).format("LL")}
                                </div>
                            </div>
                        </div>
                    </ListGroup.Item>
                    <ListGroup.Item>
                        <div className="container">
                            <div className="row">
                                <ListOfTags tags={this.props.certificate.tags}
                                            setDropdownValue={this.props.setDropdownValue}
                                            handleGetCertificatesByTagName={this.props.handleGetCertificatesByTagName}/>
                            </div>
                            <div className="row">
                                {renderDescription(this.props.isModal, this.props.certificate.description)}
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
                                <div className="col overflow-hidden">
                                    {this.props.certificate.price} BYN
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
