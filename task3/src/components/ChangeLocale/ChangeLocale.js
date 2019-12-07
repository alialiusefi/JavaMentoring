import React from "react";
import {withLocalize} from "react-localize-redux";

const ChangeLocale = ({languages, activeLanguage, setActiveLanguage}) => (
    <div className="container-fluid">
        <div className="row">
            {languages.map(lang => (
                <div key={lang.code}>
                    <button className="btn btn-link" onClick={() => setActiveLanguage(lang.code)}>
                        {lang.name}
                    </button>
                </div>
            ))}
        </div>

    </div>
);

export default withLocalize(ChangeLocale);

