import React from "react";
import {withLocalize} from "react-localize-redux";
import * as moment from "moment";

const ChangeLocale = ({languages, activeLanguage, setActiveLanguage}) => (
    <div className="container-fluid">
        {languages.map(lang => (
            <div key={lang.code}>
                <button className="btn btn-link p-0" onClick={() => {
                    setActiveLanguage(lang.code);
                    localStorage.setItem("locale", lang.code);
                    moment.locale(lang.code);
                }}>
                    {lang.name}
                </button>
            </div>
        ))}
    </div>
);

export default withLocalize(ChangeLocale);

