import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import GiftCertifcateForm from "./GiftCertificateForm";
class AddEditGiftCertificate extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="container">
                <br/>
                <div className="row justify-content-center">
                    <h2>Add/Edit Gift Certificate</h2>
                </div>
                <div className="row">
                    <GiftCertifcateForm/>
                </div>
            </div>
        );


    }

}

export default AddEditGiftCertificate;