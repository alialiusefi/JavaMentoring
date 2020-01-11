/*
import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import Pagination from 'react-js-pagination'

class PaginationPage extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {

        return (
            /!*<Pagination>
                <Pagination.Prev/>
                {
                    this.generatePageNumbers(this.props.pageCount,this.props.pageNumber)
                }
                <Pagination.Next />
            </Pagination>*!/

        );


    }

    /!* generatePageNumbers = (pageCount,pageNumber) => {
         return (
             /!*<div>
                 {[...Array(pageCount)].map(function(object, i){
                     if(i === pageNumber){
                         return <Pagination.Item active>
                             <a onClick={this.handlePageChange}>
                                 {i}
                             </a>
                         </Pagination.Item>;
                     }
                     return <Pagination.Item>
                         <a onClick={this.handlePageChange}>
                         {i}</a>
                     </Pagination.Item>;
                 })}
             </div>*!/
         )
     };*!/

    handlePageChange = (pageNumber) => {
        this.props.changePage(pageNumber);
    };

}

export default withLocalize(PaginationPage);
*/
