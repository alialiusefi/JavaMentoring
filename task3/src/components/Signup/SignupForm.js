import React from 'react';
import {Field, reduxForm} from 'redux-form';
import {Link, Prompt} from "react-router-dom";
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
    if (!values.username) {
        errors.username = 'Required'
    } else if (!(values.username.length > 4 && values.username.length < 31)) {
        errors.username = 'Minimum be 5 characters or more and less than 30 characters'
    }
    if (!values.password) {
        errors.password = 'Required'
    } else if (values.password.length < 4) {
        errors.password = 'Minimum be 4 characters or more'
    }
    return errors
}

let SignupForm = props => {
    const {handleSubmit, pristine, submitting, invalid} = props;
    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                {/*<div className="row p-3">
                    <div className="col-5">
                        <label><Translate id="login.username"> Username </Translate></label>
                        <Field name="username" component={renderField}/>
                    </div>
                </div>
                <br/>
                <div className="row">
                    <div className="col-5">
                        <label><Translate id="login.password"> Password </Translate></label>
                        <Field type="password" name="password" component={renderField}/>
                    </div>
                </div>
                <br/>*/}
                <div className="row">
                    <div className="col-5">
                        <label><Translate id="login.username"> Username </Translate></label>
                        <Field name="username" component={renderField}/>
                    </div>
                    <div className="col-5">
                        <label><Translate id="login.password"> Password </Translate></label>
                        <Field type="password" name="password" component={renderField}/>
                    </div>
                </div>
                <br/>
                <div className="row">
                    <div className="col">
                        <button disabled={pristine || submitting || invalid}
                                className="btn btn-dark text-white d-flex justify-content-center flex-column">
                            <Translate id="signup.signupbutton">
                                Signup
                            </Translate>
                        </button>

                    </div>
                    <div className="col">
                        <Link to={"/giftcertificates"}
                              className="btn btn-dark text-white d-flex justify-content-center flex-column">
                            <Translate id="login.cancelbutton">
                                Cancel
                            </Translate>
                        </Link>
                    </div>
                </div>
                <Prompt when={!pristine} message="Are you sure?"/>
            </form>
        </div>
    )
}
SignupForm = reduxForm({
    form: 'signup', validate
})(SignupForm);


export default SignupForm;