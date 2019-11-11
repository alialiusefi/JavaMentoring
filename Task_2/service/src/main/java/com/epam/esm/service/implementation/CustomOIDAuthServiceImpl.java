package com.epam.esm.service.implementation;

import com.epam.esm.entity.*;
import com.epam.esm.repository.AuthorityRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserName;
import com.epam.esm.service.CustomOIDAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CustomOIDAuthServiceImpl extends OidcUserService implements CustomOIDAuthService {

    private static final String GOOGLE_NAMEATTRIBUTEKEY = "email";
    public UserRepository userRepository;
    public AuthorityRepository authorityRepository;

    @Autowired
    public CustomOIDAuthServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserEntity registerNewUser(String username) {
        UserEntity.UserBuilder builder = new UserEntity.UserBuilder(
                username,
                "nopass", true);
        UserEntity userEntity = builder.getResult();
        userEntity = userRepository.add(userEntity);
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority.AuthorityBuilder(userEntity.getId(),
                UserStatus.USER).getResult();
        authority = authorityRepository.add(authority);
        authorities.add(authority);
        userEntity.setAuthorityList(authorities);
        userEntity = userRepository.update(userEntity);
        return userEntity;
    }

    @Override
    public OidcUser processOAuth2User(OidcUserRequest userRequest,
                                      OidcUser oauthUser)
            throws OAuth2AuthenticationException {
        if (userRequest.
                getClientRegistration().getRegistrationId().equalsIgnoreCase(AuthenticationProvider.
                GOOGLE.toString().toLowerCase())) {
            UserEntity userEntity;
            GoogleCustomOIDAuthUser customOAuthUser =
                    new GoogleCustomOIDAuthUser(oauthUser.getAuthorities(),
                            oauthUser.getClaims(),
                            GOOGLE_NAMEATTRIBUTEKEY, null,
                            oauthUser.getIdToken(),
                            oauthUser.getUserInfo());
            Optional<UserEntity> optionalUser;
            if (!customOAuthUser.getSubject().isEmpty()) {
                String username = customOAuthUser.getSubject();
                optionalUser = userRepository.
                        queryEntity(new FindUserByUserName(username));
                if (optionalUser.isPresent()) {
                    userEntity = optionalUser.get();
                } else {
                    userEntity = registerNewUser(username);
                }
                customOAuthUser.setUserEntity(userEntity);
                return customOAuthUser;
            }
        }
        return null;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return processOAuth2User(userRequest, (OidcUser) user);
    }
}