import React, {Component} from "react";
import {Field, reduxForm} from "redux-form";
import {Translate} from "react-localize-redux";
import SearchForm from "./SearchForm";


class SearchMenuForm extends Component {

    render() {
        const {handleSubmit} = this.props;
        return (
            <form name="selectForm" onSubmit={this.handleSubmit}>
                <select
                    /*value={this.state.certificateLocation}
                    onChange={event => {
                        this.setState({ certificateLocation : event.target.value});
                    }}*/
                >
                    <option value="ALL">
                        {/*<Translate id="home.allgiftcertificates">
                                                            </Translate>*/}
                        All GiftCertificates
                    </option>
                    <option value="MY">
                        {/*<Translate id="home.mygiftcertificates">
                                                            </Translate>*/}
                        My GiftCertificates
                    </option>
                </select>
            </form>
        );
    }
}

SearchMenuForm = reduxForm({
    form: 'searchmenu' // a unique name for this form
})(SearchMenuForm);

export default SearchMenuForm;