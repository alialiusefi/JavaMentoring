import {Translate, withLocalize} from "react-localize-redux";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import ModalHeader from "react-bootstrap/ModalHeader";
import ModalFooter from "react-bootstrap/ModalFooter";
import React,{useState} from "react";

const DeleteCertificateModal = (props) => {
    const {
        certificate,
        handleDeleteCertificate
    } = props;

    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const onClick = () => {
        toggle()
    };

    const deleteHandler = (certificate) => {
        //todo: getAll certificates callback
        handleDeleteCertificate(certificate);
        toggle()
    };
    return (
        <div>
            <Button color={'danger'} className={'pStyle'} onClick={onClick}>
                <Translate id="button.delete"/>
            </Button>
            <Modal show={modal} onHide={() => setModal(false)}>
                <ModalHeader toggle={toggle}><Translate id="modal.deletequestion"/></ModalHeader>
                <ModalFooter>
                    <Button color={'primary'}
                            onClick={() => deleteHandler(certificate)}><Translate id="button.delete"/></Button>{' '}
                    <Button color="secondary" onClick={toggle}><Translate id="button.cancel"/></Button>{' '}
                </ModalFooter>
            </Modal>
        </div>
    );
};

export default DeleteCertificateModal;

