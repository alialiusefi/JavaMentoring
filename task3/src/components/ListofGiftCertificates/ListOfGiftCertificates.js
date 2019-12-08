import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import GiftCertificateCard from "../GiftCertificateCard/GiftCertificateCard";
import ScrollBars from "react-custom-scrollbars";

class ListOfGiftCertificates extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (

            <ScrollBars style={{width: 1000, height: 500}}>
                <div className="container-fluid">
                    <div className="row">
                {
                    this.props.giftcertificates.map(
                        (certificate) =>
                                <div className="col-md col-lg-6 p-2">
                                    <GiftCertificateCard certificate={certificate}/>
                                </div>
                    )
                }
                    </div>
                </div>
            </ScrollBars>
        );
    }
}

export default withLocalize(ListOfGiftCertificates);
