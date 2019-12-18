import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import GiftCertificateForm from "./GiftCertificateForm";
import {Translate} from "react-localize-redux";
import './ReactInput.css'

class AddEditGiftCertificate extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tags:[

            ],
            suggestions : [
            ]
        };
    }

    componentDidMount() {
        if(( this.props.certificate != null && this.props.certificate.tags != null && this.props.certificate.tags.length !== 0)) {
            let reactTags = [];
            this.props.certificate.tags.map((tag) => {
                reactTags.push({
                    id : tag.name,
                    text : tag.name
                });
            })
            this.setState({tags : reactTags});
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
        this.setState(state => ({tags: [...state.tags, tag]}));
        console.log(this.state.tags);
    }

    handleDrag = (tag, currPos, newPos) => {
        const tags = [...this.state.tags];
        const newTags = tags.slice();

        newTags.splice(currPos, 1);
        newTags.splice(newPos, 0, tag);

        this.setState({ tags: newTags });
    }

    handleInputChange = (event) => {
        //call api to search and change state of suggestions
    }


}

/*// прокидываем в props функцию для инициализации формы
function mapDispatchToProps(dispatch){
    return {
        initializePost: function (certificate){
            dispatch(initialize('certificate',certificate));
        }
    }
}
/!*!// прокидываем в props объект для инициализаци формы
function mapStateToProps(state, ownProps){
    const id = ownProps.params.id;
    return {
        post: state.posts[id]
    }
}*!/*/
export default (AddEditGiftCertificate);