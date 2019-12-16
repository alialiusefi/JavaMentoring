import React from 'react';
import {Field, reduxForm} from 'redux-form';
import {Link} from "react-router-dom";
import {Translate} from "react-localize-redux"
import { WithContext as ReactTags } from 'react-tag-input';

const renderField = ({input, label, type, meta: {touched, error, warning}}) => (
    <div>
        <label className="control-label">{label}</label>
        <div>
            <input {...input} placeholder={label} type={type} className="form-control"/>
            {touched && ((error && <span className="text-danger">{error}</span>) || (warning &&
                <span>{warning}</span>))}
        </div>
    </div>
)

const validate = values => {
    const errors = {};
    if (!values.name) {
        errors.name = 'Required'
    } else if (!(values.name.length > 0 && values.name.length < 51)) {
        errors.name = 'Minimum be 1 characters or more and less than 51 characters'
    }
    if (!values.description) {
        errors.description = 'Required'
    } else if (!(values.description.length > 0 && values.description.length < 257)) {
        errors.description = 'Minimum be 1 characters or more and less than 256 characters'
    }
    if (!values.price) {
        errors.price = 'Required'
    } else if (!values.price.match("\\d[\\d\\,\\.]+")) {
        errors.price = 'Incorrect price format'
    }
    if (!values.durationTillExpiry) {
        errors.durationTillExpiry = 'Required'
    } else if (!(values.durationTillExpiry > 0)) {
        errors.durationTillExpiry = 'duration should be at least 1 day'
    }
    return errors;
}

const applyButton = (certificate, pristine, submitting, invalid) => {
    if (certificate == null) {
        return (
            <button type="submit" disabled={pristine || submitting || invalid}
                    className="btn btn-dark text-white  justify-content-center flex-column">
                <Translate id="addeditgiftcertificate.add"/>
            </button>
        );
    } else {
        return (
            <button type="submit" disabled={pristine || submitting || invalid}
                    className="btn btn-dark text-white  justify-content-center flex-column">
                <Translate id="addeditgiftcertificate.edit"/>
            </button>
        );
    }
}

let GiftCertifcateForm = props => {
    const {handleSubmit, pristine, submitting, invalid, certificate
    ,tags,suggestions,handleTagDelete,handleTagAdd,handleTagDrag,handleInputChange} = props;

    return (
        <div className="container justify-content-center">
            <form onSubmit={handleSubmit}>
                <div className="row text-center">
                    <div className="col">
                        <label><Translate id="giftcertificate.name"/></label>
                        <Field name="name" component={renderField}/>
                    </div>
                    <div className="col">
                        <label><Translate id="giftcertificate.description"/></label>
                        <Field name="description" component={renderField}/>
                    </div>
                </div>
                <div className="row text-center">
                    <div className="col">
                        <label><Translate id="giftcertificate.price"/></label>
                        <Field name="price" component={renderField}/>
                    </div>
                    <div className="col">
                        <label><Translate id="giftcertificate.duration"/></label>
                        <Field name="durationTillExpiry" component={renderField}/>
                    </div>
                </div>
                <br/>
                <div className="row text-center">
                    <div className="col">
                        <label><Translate id={"tags.tags"}/> :</label>
                        <ReactTags
                            tags={tags}
                        suggestions={suggestions}
                        handleDelete={handleTagDelete}
                        handleAddition={handleTagAdd}
                        handleDrag={handleTagDrag}
                        handleInputChange={handleInputChange}/>
                    </div>
                </div>
                <div className="row">
                    <div className="col">
                        {/*{applyButton(certificate,pristine, submitting, invalid)}*/}
                        {applyButton()}
                    </div>

                    <div className="col">
                        <Link to={"/giftcertificates"}
                              className="btn btn-dark text-white justify-content-center flex-column">
                            <Translate id="login.cancelbutton">
                                Cancel
                            </Translate>
                        </Link>
                    </div>
                </div>
                <br/>
                {/*Below Form Screen */}
            </form>
        </div>
    )
};



GiftCertifcateForm = reduxForm({
    form: 'giftcertificateform', validate
})(GiftCertifcateForm);


export default GiftCertifcateForm;