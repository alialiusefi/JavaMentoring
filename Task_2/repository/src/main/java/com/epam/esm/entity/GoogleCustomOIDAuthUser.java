package com.epam.esm.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Map;

public class GoogleCustomOIDAuthUser extends CustomOAuthUser implements OidcUser {

    private OidcIdToken idToken;
    private OidcUserInfo userInfo;


    public GoogleCustomOIDAuthUser(Collection<? extends GrantedAuthority> authorities,
                                   Map<String, Object> attributes,
                                   String nameAttributeKey, UserEntity userEntity,
                                   OidcIdToken idToken, OidcUserInfo userInfo) {
        super(authorities, attributes, nameAttributeKey, userEntity);
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    @Override
    public String getSubject() {
        return (String) this.getAttribute("email");
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.getAttributes();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }
}
