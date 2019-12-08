import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import ReactPaginate from 'react-paginate';

class PaginationPage extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <ReactPaginate previousLabel={'<'}
                           nextLabel={'>'}
                           pageCount={5}
                           pageRangeDisplayed={5}
                           /*onPageChange={}*/
                           containerClassName={'pagination'}
                           subContainerClassName={'pages pagination'}
                           activeClassName={'active'}>
            </ReactPaginate>
        );
    }
}

export default withLocalize(PaginationPage);
