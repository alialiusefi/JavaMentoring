import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import SignupForm from "./SignupForm";

class Signup extends React.Component {

    constructor(props) {
        super(props);
        this.state = {type : "password"};
    }

    render() {
        return (
            <div className="container text-center">

                <h3><Translate id="signup.signupbutton">Sign Up</Translate></h3>
                <br/>
                    <SignupForm onSubmit={this.handleSubmit} type={this.state.type} showhidePass={this.showhidePass}/>
                    <br/>
            </div>
        );
    }

    handleSubmit = (values) =>{
       this.props.handleSignup(values.username,values.password);
    }

    showhidePass = () => {
        this.setState({
            type: this.state.type === 'input' ? 'password' : 'input'
        })
    }
}

export default withLocalize(Signup);
