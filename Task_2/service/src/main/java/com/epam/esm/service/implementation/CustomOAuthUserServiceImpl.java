package com.epam.esm.service.implementation;

import com.epam.esm.entity.AuthenticationProvider;
import com.epam.esm.entity.Authority;
import com.epam.esm.entity.CustomOAuthUser;
import com.epam.esm.entity.GithubCustomOAuthUser;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.entity.UserStatus;
import com.epam.esm.repository.AuthorityRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserName;
import com.epam.esm.service.CustomOAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomOAuthUserServiceImpl extends DefaultOAuth2UserService
        implements CustomOAuthUserService {

    public UserRepository userRepository;
    public AuthorityRepository authorityRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
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
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return processOAuth2User(userRequest, user);
    }

    @Override
    public CustomOAuthUser processOAuth2User(OAuth2UserRequest userRequest,
                                             OAuth2User oauthUser)
            throws OAuth2AuthenticationException {
        if (userRequest.
                getClientRegistration().getRegistrationId().equalsIgnoreCase(AuthenticationProvider.
                GITHUB.toString().toLowerCase())) {

            GithubCustomOAuthUser githubCustomOAuthUser =
                    new GithubCustomOAuthUser(oauthUser.getAuthorities(),
                            oauthUser.getAttributes(),
                            "login", null);
            UserEntity userEntity;
            Optional<UserEntity> optionalUser;
            if (!githubCustomOAuthUser.getName().isEmpty()) {
                String username = githubCustomOAuthUser.getName();
                optionalUser = userRepository.
                        queryEntity(new FindUserByUserName(username));
                if (optionalUser.isPresent()) {
                    userEntity = optionalUser.get();
                } else {
                    userEntity = registerNewUser(username);
                }
                githubCustomOAuthUser.setUserEntity(userEntity);
                List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (GrantedAuthority i : userEntity.getAuthorityList()) {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(i.getAuthority());
                    grantedAuthorities.add(simpleGrantedAuthority);
                }
                //SecurityContextHolder.getContext().getAuthentication().getAuthorities().addAll(grantedAuthorities);
                return githubCustomOAuthUser;
            }
        }
        return null;
    }

}
