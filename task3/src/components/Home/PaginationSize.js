import React from "react";
import {Translate, withLocalize} from "react-localize-redux";
import Select from 'react-select';

const options = [
    {value: 5, label: '5'},
    {value: 10, label: '10'},
    {value: 15, label: '15'},
];

class PaginationSize extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            paginationSize: 5
        }
    }

    render() {
        const {paginationSize} = this.state;
        return (
            <div>
                <Select value={paginationSize}
                        onChange={this.handleChange}
                        options={options}>
                </Select>
            </div>
        );
    }

    handleChange = paginationSize => {
        this.props.handleChangePageSize(paginationSize.value);
    };

}
export default withLocalize(PaginationSize);
