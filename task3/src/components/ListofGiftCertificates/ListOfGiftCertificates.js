import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import GiftCertificateCard from "../GiftCertificateCard/GiftCertificateCard";
import ScrollBars from "react-custom-scrollbars";
import {Route, Switch, withRouter} from "react-router-dom";
import ModalHeader from "react-bootstrap/ModalHeader";
import ModalBody from "react-bootstrap/ModalBody";
import ModalFooter from "react-bootstrap/ModalFooter";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

class ListOfGiftCertificates extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            certificate: null
        };
    }

    componentDidMount() {
            if(this.props.searchField != null && this.props.searchField !== "") {
                this.props.handleSearch({searchField : this.state.searchField});
            } else {
                this.props.handleGetAllCertificates(this.props.certificateDropDownValue,this.props.pageSize,this.props.pageNumber);
            }

    }

    render() {

        return (
            <div>
                <ScrollBars style={{width: 1000, height: 1000}}>
                    <div className="container-fluid">
                        <div className="row">
                            {
                                this.props.giftcertificates.map(
                                    (certificate) =>
                                        <div className="btn col-md col-lg-6 p-2">
                                            <GiftCertificateCard
                                                setStates={this.setStates}
                                                modal={this.state.modal}
                                                certificate={certificate}
                                                handleGetCertificatesByTagName={this.props.handleGetCertificatesByTagName}
                                                role={this.props.role}
                                                isModal={false}
                                                setSelectedGiftCertificate={this.props.setSelectedGiftCertificate}
                                                handleDeleteCertificate={this.props.handleDeleteCertificate}
                                                handleBuyCertificate={this.props.handleBuyCertificate}
                                                setDropDownValue={this.props.setDropdownValue}
                                            />
                                        </div>
                                )
                            }
                        </div>
                    </div>
                </ScrollBars>
                <Modal show={this.state.modal} onHide={() => this.setState({modal: false})}>
                    <ModalHeader toggle={() => this.setState({modal: false})}>
                        {/*{this.state.certificate.name}*/}</ModalHeader>
                    <ModalBody>
                        <GiftCertificateCard
                            certificate={this.state.certificate}
                            handleGetCertificatesByTagName={this.props.handleGetCertificatesByTagName}
                            role={this.props.role}
                            isModal={true}
                            setSelectedGiftCertificate={this.props.setSelectedGiftCertificate}
                            handleDeleteCertificate={this.props.handleDeleteCertificate}
                            handleBuyCertificate={this.props.handleBuyCertificate}
                            setDropdownValue={this.props.setDropdownValue}
                        />
                    </ModalBody>
                    <ModalFooter>
                        <Button color="secondary" onClick={() => this.setState({modal: false})}><Translate
                            id="button.cancel"/></Button>{' '}
                    </ModalFooter>
                </Modal>
            </div>
        );
    }

    setStates = (modal,certificate, isModal) => {
        if(!isModal) {
            this.setState({
                modal: true,
                certificate: certificate
            });
        }
    }

}

export default withLocalize(withRouter(ListOfGiftCertificates));
