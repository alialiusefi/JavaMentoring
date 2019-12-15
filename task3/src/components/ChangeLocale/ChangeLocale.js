import React from "react";
import {withLocalize, setActiveLanguage} from "react-localize-redux";

const ChangeLocale = ({languages, activeLanguage, setActiveLanguage}) => (
    <div className="container-fluid">
        {languages.map(lang => (
            <div key={lang.code}>
                <button className="btn btn-link p-0" onClick={() => {
                    setActiveLanguage(lang.code);
                    localStorage.setItem("locale", lang.code);
                }}>
                    {lang.name}
                </button>
            </div>
        ))}
    </div>
);

export default withLocalize(ChangeLocale);

