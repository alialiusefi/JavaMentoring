package com.epam.esm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final PageDefault pagedefault = new PageDefault();
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }

    public static class PageDefault {
        private String defaultPageSize;
        private String defaultPageNumber;

        public String getDefaultPageSize() {
            return defaultPageSize;
        }

        public void setDefaultPageSize(String defaultPageSize) {
            this.defaultPageSize = defaultPageSize;
        }

        public String getDefaultPageNumber() {
            return defaultPageNumber;
        }

        public void setDefaultPageNumber(String defaultPageNumber) {
            this.defaultPageNumber = defaultPageNumber;
        }
    }

    public static class Auth {

        private String tokenSecret;
        private long tokenExpirationMsec;
        private long refreshTokenExpirationMsec;

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }

        public long getRefreshTokenExpirationMsec() {
            return refreshTokenExpirationMsec;
        }

        public void setRefreshTokenExpirationMsec(long refreshTokenExpirationMsec) {
            this.refreshTokenExpirationMsec = refreshTokenExpirationMsec;
        }
    }

    public static class OAuth2 {

        private String authorizedRedirectUri;

        public String getAuthorizedRedirectUri() {
            return authorizedRedirectUri;
        }

        public void setAuthorizedRedirectUri(String authorizedRedirectUris) {
            this.authorizedRedirectUri = authorizedRedirectUris;
        }

    }
}