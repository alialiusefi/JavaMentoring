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
        this.state = {
            certificateDropDown: "ALL"
        }
    }



    render() {
        const {certificateDropDown} = this.state;
        return (
            <div>
                <Select value={certificateDropDown}
                        onChange={this.handleChange}
                        options={options}>
                </Select>
            </div>
        );
    }

    handleChange = (certificateDropDown) => {
        this.setState({certificateDropDown});
        this.props.handleGetAllCertificates(certificateDropDown);
    };



}
export default SearchMenuForm;