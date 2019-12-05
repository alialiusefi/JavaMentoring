import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Home from './components/Home/Home';
import {BrowserRouter} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-social/bootstrap-social.css'
import 'font-awesome/css/font-awesome.min.css'

ReactDOM.render(
    <BrowserRouter>
        <Home />
    </BrowserRouter>, document.getElementById('root')
);


