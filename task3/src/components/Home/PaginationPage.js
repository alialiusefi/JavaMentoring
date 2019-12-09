import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import Pagination from 'react-bootstrap/Pagination'
class PaginationPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            pageNumber : 2
        }
    }

    render() {
        const pageNumber = this.state.pageNumber;

        return (
                <Pagination>
                    <Pagination.Prev/>
                    <Pagination.Item>{1}</Pagination.Item>
                    <Pagination.Item {...pageNumber === 1 ? 'active' : ''}>{2}</Pagination.Item>
                    <Pagination.Item>{3}</Pagination.Item>
                    <Pagination.Next />
                </Pagination>
        );


    }

    handleChange = pageNumber => {
        this.setState({pageNumber});
    };

}

export default withLocalize(PaginationPage);
