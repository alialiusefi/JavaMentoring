import React from 'react';
import {Field, reduxForm} from 'redux-form';
import {Link} from "react-router-dom";
import {Translate} from "react-localize-redux"


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
    const errors = {}
    /*if (!values.username) {
        errors.username = 'Required'
    } else if (!(values.username.length > 4 && values.username.length < 31)) {
        errors.username = 'Minimum be 5 characters or more and less than 30 characters'
    }
    if (!values.password) {
        errors.password = 'Required'
    } else if (values.password.length < 4) {
        errors.password = 'Minimum be 4 characters or more'
    }*/
    return errors
}

const certificate = {
    username: "asdasd"
}

let GiftCertifcateForm = props => {
    const {handleSubmit, pristine, submitting, invalid} = props;
    return (
        <div className="container justify-content-center">
            <form onSubmit={handleSubmit}>
                <div className="row text-center">
                    <div className="col">
                        <label><Translate id="giftcertificate.name"/></label>
                        <Field name="name" component={renderField}/>
                    </div>
                </div>
                <div className="row text-center">
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
                </div>
                <div className="row text-center">
                    <div className="col">
                        <label><Translate id="giftcertificate.dateofcreation"/></label>
                        <Field name="dateofcreation" component={renderField}/>
                    </div>
                </div>
                <div className="row text-center">
                    <div className="col">
                        <label><Translate id="giftcertificate.dateofmodification"/></label>
                        <Field name="dateofmodification" component={renderField}/>
                    </div>
                </div>
                <div className="row text-center">
                    <div className="col">
                        <label><Translate id="giftcertificate.duration"/></label>
                        <Field name="duration" component={renderField}/>
                    </div>
                </div>
                <br/>
                {/*Bellow Form Screen */}
                <footer className="footer">
                    <div className="row">
                        <div className="col">
                            <button disabled={pristine || submitting || invalid}
                                    className="btn btn-dark text-white  justify-content-center flex-column">
                                <Translate id="login.loginbutton">
                                    Add
                                </Translate>
                            </button>
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
                </footer>
            </form>
        </div>
    )
}
GiftCertifcateForm = reduxForm({
    form: 'addeditgiftcertificate', validate
})(GiftCertifcateForm);


export default GiftCertifcateForm;