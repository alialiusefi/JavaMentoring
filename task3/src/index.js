import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Home from './components/Home/Home';
import {BrowserRouter,Route} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-social/bootstrap-social.css'
import 'font-awesome/css/font-awesome.min.css'
import { render } from "react-dom";
import {LocalizeProvider} from "react-localize-redux";

const App = props => (
    <LocalizeProvider>
        <BrowserRouter>
            <Route path="/" component={Home} />
        </BrowserRouter>
    </LocalizeProvider>
);

render(<App />, document.getElementById("root"));


