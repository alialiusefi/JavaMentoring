import React, {Component} from 'react';
import {Field, reduxForm} from 'redux-form';
import {Translate} from "react-localize-redux";

class SearchForm extends Component {
    render() {
        const {handleSubmit} = this.props;
        return (
            <form onSubmit={handleSubmit}>
                <div className="container-fluid">
                    <label className="pr-2"><Translate id="home.search">
                        Search
                    </Translate>:</label>
                    <Field name="searchField" value={this.props.initialValues.searchField} component="input" type="text"/>
                    &nbsp;
                    <button className="btn btn-dark">
                        <Translate id="home.search">
                            Search
                        </Translate>
                    </button>
                    &nbsp;
                    <a href="/giftcertificates" className="btn btn-danger">
                        <Translate id="button.reset">
                            Reset
                        </Translate>
                    </a>
                </div>
            </form>
        );
    }
}

// Decorate the form component
SearchForm = reduxForm({
    form: 'search', // a unique name for this form
    enableReinitialize: 'true'
})(SearchForm);

export default SearchForm;
