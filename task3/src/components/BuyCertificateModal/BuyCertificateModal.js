import {Translate} from "react-localize-redux";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import ModalHeader from "react-bootstrap/ModalHeader";
import ModalFooter from "react-bootstrap/ModalFooter";
import React, {useState} from "react";

const BuyCertificateModal = (props) => {
    const {
        certificate,
        handleBuyCertificate
    } = props;

    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const onClick = () => {
        toggle()
    };

    const buyHandler = (certificate) => {
        handleBuyCertificate(certificate);
        toggle()
    };
    return (
        <div>
            <Button color={'danger'} className={'pStyle'} onClick={onClick}>
                <Translate id="button.buy"/>
            </Button>
            <Modal show={modal} onHide={() => setModal(false)}>
                <ModalHeader toggle={toggle}><Translate id="modal.buyquestion"/></ModalHeader>
                <ModalFooter>
                    <Button color={'primary'}
                            onClick={() => buyHandler(certificate)}><Translate id="button.buy"/></Button>{' '}
                    <Button color="secondary" onClick={toggle}><Translate id="button.cancel"/></Button>{' '}
                </ModalFooter>
            </Modal>
        </div>
    );
};

export default BuyCertificateModal;

