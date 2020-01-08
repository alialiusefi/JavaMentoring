import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import GiftCertificateForm from "./GiftCertificateForm";
import {Translate} from "react-localize-redux";
import './ReactInput.css'
import Alert from 'react-s-alert';

class AddEditGiftCertificate extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tags: [],
            suggestions: []
        };
    }

    componentDidMount() {
        this.props.handleSetLoginDetails(true);
        if ((this.props.certificate != null && this.props.certificate.tags != null
            && this.props.certificate.tags.length !== 0)) {
            let reactTags = [];
            this.props.certificate.tags.map((tag) => {
                reactTags.push({
                    id: tag.name,
                    text: tag.name
                });
            })
            this.setState({tags: reactTags});
        }
    }

    render() {
        return (
            <div className="container">
                <br/>
                <div className="row justify-content-center">
                    <h2><Translate id={"addeditgiftcertificate.title"}/></h2>
                </div>
                <div className="row">
                    <GiftCertificateForm
                        onSubmit={this.props.certificate == null ? this.handleSubmit : this.handleUpdate}
                        initialValues={this.props.certificate}
                        certificate={this.props.certificate}
                        handleTagDelete={this.handleDelete}
                        handleTagAdd={this.handleAddition}
                        handleTagDrag={this.handleDrag}
                        tags={this.state.tags}
                        suggestions={this.state.suggestions}
                        handleInputChange={this.handleInputChange}
                    />
                </div>
                <Alert stack={{limit: 3}} position={"top"}/>
            </div>
        );
    }


    handleSubmit = (values) => {
        console.log(values);
        let arrayTag = [];
        this.state.tags.map((tag) => {
            arrayTag.push({name: tag.text})
        });
        this.props.handleAddCertificate(
            values.name,
            values.description,
            values.price,
            values.durationTillExpiry,
            arrayTag);
    };


    handleInputChange = (input) => {
        fetch("http://localhost:8080/api/v1/tags/?page=1&size=5&tagName=TAG_NAME".replace("TAG_NAME", input),
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            }).then(response => {
            const json = response.json();
            if (!response.ok) {
                return Promise.reject(json);
            }
            return json;
        }).then(json => {
            console.log(json);
            let suggestions = [];
            for (let i = 0; i < json.length; i++) {
                suggestions.push({id: json[i].id, text: json[i].name});
            }
            this.setState({suggestions: suggestions});
            return json;
        }).catch(error => {
            console.log(error);
        });

    };

    handleUpdate = (values) => {
        console.log(values);
        let arrayTag = [];
        this.state.tags.map((tag) => {
            arrayTag.push({name: tag.text})
        });
        const certificate_id = this.props.certificate.id;
        this.props.handleUpdateCertificate(
            values.name,
            values.description,
            values.price,
            values.durationTillExpiry,
            arrayTag, certificate_id);
    };

    handleDelete = (i) => {
        const {tags} = this.state;
        this.setState({
            tags: tags.filter((tag, index) => index !== i),
        });
    }

    handleAddition = (tag) => {
        if (tag.text.length < 17 && tag.text.length > 1) {
            this.setState(state => ({tags: [...state.tags, tag]}));
        } else {
            Alert.warning("Tag size should be from 1 to 16");
            return;
        }
        console.log(this.state.tags);
    }

    handleDrag = (tag, currPos, newPos) => {
        const tags = [...this.state.tags];
        const newTags = tags.slice();

        newTags.splice(currPos, 1);
        newTags.splice(newPos, 0, tag);

        this.setState({tags: newTags});
    };

}

export default (AddEditGiftCertificate);