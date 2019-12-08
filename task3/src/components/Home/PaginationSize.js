import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import ReactPaginate from 'react-paginate';

class PaginationSize extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                SELECT PAGINATION SIZE HERE
            </div>
        );
    }
}

export default withLocalize(PaginationSize);
