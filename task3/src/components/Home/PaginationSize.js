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
    }

    render() {
        return (
            <div>
                <Select value={options.map((option) => {
                    if(option.value === this.props.paginationSize) {
                        return option;
                    }
                })}
                        onChange={this.handleChange}
                        options={options}
                        isSearchable={false}
                        isMulti={false}
                        defaultValue={options[0]}
                >
                </Select>
            </div>
        );
    }

    handleChange = paginationSize => {
        this.props.handleChangePageSize(paginationSize.value);
    };

}

export default withLocalize(PaginationSize);
