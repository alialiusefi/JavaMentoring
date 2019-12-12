import React, {Component} from "react";
import Select from "react-select";
import {Translate} from "react-localize-redux";

const options = [
    {value: "MY", label: <Translate id="home.mygiftcertificates">My GiftCertificate</Translate>},
    {value: "ALL", label: <Translate id="home.allgiftcertificates">All GiftCertificate</Translate>}
];



class SearchMenuForm extends Component {

    constructor(props) {
        super(props);

    }



    render() {
        return (
            <div>
                <Select value={this.props.certificateDropDownValue}
                        onChange={this.handleChange}
                        options={options}>
                </Select>
            </div>
        );
    }

    handleChange = (certificateDropDown) => {
        this.props.handleGetAllCertificates(certificateDropDown);
    };



}
export default SearchMenuForm;