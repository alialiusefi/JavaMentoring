import React from "react";
import {withLocalize} from "react-localize-redux";
import GiftCertificateCard from "../GiftCertificateCard/GiftCertificateCard";
import ScrollBars from "react-custom-scrollbars";

class ListOfGiftCertificates extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.props.handleGetAllCertificates("ALL");
    }

    render() {
        return (
            <ScrollBars style={{width: 1000, height: 1000}}>
                <div className="container-fluid">
                    <div className="row">
                        {
                            this.props.giftcertificates.map(
                                (certificate) =>
                                    <div className="col-md col-lg-6 p-2">
                                        <GiftCertificateCard certificate={certificate}
                                                             handleGetCertificatesByTagName={this.props.handleGetCertificatesByTagName}
                                                             role={this.props.role}
                                                             setSelectedGiftCertificate={this.props.setSelectedGiftCertificate}
                                                             handleDeleteCertificate={this.props.handleDeleteCertificate}
                                        />
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
