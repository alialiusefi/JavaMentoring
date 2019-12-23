import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Home from './components/Home/Home';
import {BrowserRouter, Route} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-social/bootstrap-social.css'
import 'font-awesome/css/font-awesome.min.css'
import {render} from "react-dom";
import {LocalizeProvider} from "react-localize-redux";
import {createStore, combineReducers} from 'redux'
import {reducer as formReducer} from 'redux-form'
import {Provider} from 'react-redux';
import { transitions,withAlert, positions, Provider as AlertProvider } from 'react-alert'
import AlertTemplate from 'react-alert-template-basic'
const reducers = {
    form: formReducer
}
const reducer = combineReducers(reducers);
const store = createStore(reducer);
const options ={
    position: positions.TOP_CENTER,

};

const App = ({props}) => (
    <Provider store={store}>
        <LocalizeProvider>
            <AlertProvider template={AlertTemplate} {...options}>
                <BrowserRouter>
                    <Route path="/" component={Home}/>
                </BrowserRouter>
            </AlertProvider>
        </LocalizeProvider>
    </Provider>
);

render(<App/>, document.getElementById("root"));


