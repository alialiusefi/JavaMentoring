import {Translate, withLocalize} from "react-localize-redux";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import ModalHeader from "react-bootstrap/ModalHeader";
import ModalFooter from "react-bootstrap/ModalFooter";
import React,{useState} from "react";

const UnsavedChangesModal = (props) => {

    const [modal, setModal] = useState(props.when);
    const toggle = () => setModal(!modal);
    const onClick = () => {
        toggle()
    };

    const handler = () => {
        toggle()

    };
    return (
        <div>

        </div>
    );
};

export default UnsavedChangesModal;

