package com.epam.esm.config;

import com.epam.esm.security.filter.ExceptionHandlerFilter;
import com.epam.esm.security.filter.JwtTokenAuthenticationFilter;
import com.epam.esm.security.handler.SecurityEntryPoint;
import com.epam.esm.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;
    private JwtTokenAuthenticationFilter authenticationFilter;
    private SecurityEntryPoint restAuthenticationEntryPoint;
    private ExceptionHandlerFilter exceptionHandlingfilter;
    //@Autowired
    //private CustomOAuth2UserService customOAuth2UserService;

    //@Autowired
    //private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    //@Autowired
    //private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    //    @Autowired
    //  private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    public void setAuthenticationFilter(JwtTokenAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Autowired
    public void setRestAuthenticationEntryPoint(SecurityEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Autowired
    public void setExceptionHandlingfilter(ExceptionHandlerFilter exceptionHandlingfilter) {
        this.exceptionHandlingfilter = exceptionHandlingfilter;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/v2/auth/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v2/oauth2/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v1/tags/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v1/giftcertificates/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v2/orders/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v2/users/**").permitAll()
                .anyRequest().authenticated().and()
                .oauth2Login().authorizationEndpoint()
                .baseUri("/oauth2/callback/**").and()
                .userInfoEndpoint().userService(customOAuth2UserService).and()
                .successHandler(oAuth2AuthenticationSuccessHandler).failureHandler(oAuth2AuthenticationFailureHandler)
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().securityContext();
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlingfilter, JwtTokenAuthenticationFilter.class);
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
*/


}
