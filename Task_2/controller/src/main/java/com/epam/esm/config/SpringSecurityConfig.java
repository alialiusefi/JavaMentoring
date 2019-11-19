package com.epam.esm.config;

import com.epam.esm.security.filter.ExceptionHandlerFilter;
import com.epam.esm.security.filter.JwtTokenAuthenticationFilter;
import com.epam.esm.security.handler.CustomAccessDeniedHandler;
import com.epam.esm.security.handler.OAuth2AuthenticationFailureHandler;
import com.epam.esm.security.handler.OAuth2AuthenticationSuccessHandler;
import com.epam.esm.security.handler.SecurityEntryPoint;
import com.epam.esm.security.provider.JwtTokenProvider;
import com.epam.esm.security.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.epam.esm.service.CustomOAuthUserService;
import com.epam.esm.service.CustomOIDAuthService;
import com.epam.esm.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String GIFTCERTIFICATE_MAPPING = "/v1/giftcertificates/**";
    private static final String TAG_MAPPING = "/v1/tags/**";
    private static final String ORDERS_MAPPING = "/v2/orders/**";
    private static final String USERS_MAPPING = "/v2/users/**";


    private CustomUserService customUserService;
    private JwtTokenProvider jwtTokenProvider;
    private SecurityEntryPoint restAuthenticationEntryPoint;
    private ExceptionHandlerFilter exceptionHandlingfilter;
    private CustomOAuthUserService customOAuth2UserService;

    private CustomOIDAuthService customOIDAuthService;
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    public SpringSecurityConfig(CustomUserService customUserService, JwtTokenProvider jwtTokenProvider,
                                SecurityEntryPoint restAuthenticationEntryPoint, ExceptionHandlerFilter exceptionHandlingfilter,
                                CustomOAuthUserService customOAuth2UserService, CustomOIDAuthService customOIDAuthService,
                                OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                                OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler) {
        this.customUserService = customUserService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.exceptionHandlingfilter = exceptionHandlingfilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOIDAuthService = customOIDAuthService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/oauth2/**").anonymous()
                .and().oauth2Login().authorizationEndpoint().baseUri("/oauth2/authorize/")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and().redirectionEndpoint().baseUri("/oauth2/callback/*")
                .and().userInfoEndpoint().userService(customOAuth2UserService)
                .and().userInfoEndpoint().oidcUserService(customOIDAuthService).and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().securityContext();

        http.addFilterBefore(new JwtTokenAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlingfilter, JwtTokenAuthenticationFilter.class);
    }

    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/auth/**");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }


}
