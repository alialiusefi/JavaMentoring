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
import { createStore, combineReducers } from 'redux'
import { reducer as formReducer } from 'redux-form'
import { Provider } from 'react-redux';

const reducers = {
    form: formReducer
}
const reducer = combineReducers(reducers)
const store = createStore(reducer)

const App = props => (
    <Provider store={store}>
        <LocalizeProvider>
            <BrowserRouter>
                <Route path="/" component={Home} />
            </BrowserRouter>
        </LocalizeProvider>
    </Provider>

);

render(<App />, document.getElementById("root"));


