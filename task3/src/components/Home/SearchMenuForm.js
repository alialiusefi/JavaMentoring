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
        {
            if (this.props.role !== null) {
                return (
                    <Select value={this.props.certificateDropDownValue === "MY" ? options[0] : options[1] }
                            onChange={this.handleChange}
                            options={options}
                            isSearchable={false}
                            isMulti={false}
                            defaultValue={options[0]}>
                    </Select>);
            } else {
                return (<div></div>);
            }
        }
    }

    handleChange = (certificateDropDown) => {
        this.props.handleGetAllCertificates(certificateDropDown, 5, 1);
    };


}

export default SearchMenuForm;