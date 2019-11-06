package com.epam.esm.config;

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

    @Autowired
    public void setAuthenticationFilter(JwtTokenAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Autowired
    public void setRestAuthenticationEntryPoint(SecurityEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/v2/auth/**").anonymous()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v1/tags/**").anonymous()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v1/giftcertificates/**").anonymous()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v2/orders/**").anonymous()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/v2/users/**").anonymous()
                .anyRequest().authenticated().and().
                exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().securityContext();
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
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

}
