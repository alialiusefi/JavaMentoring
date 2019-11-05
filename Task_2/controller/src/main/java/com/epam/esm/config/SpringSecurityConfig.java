package com.epam.esm.config;

import com.epam.esm.security.filter.JwtTokenAuthenticationFilter;
import com.epam.esm.security.handler.SecurityEntryPoint;
import com.epam.esm.service.implementation.CustomUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserServiceImpl customUserService;
    @Autowired
    private JwtTokenAuthenticationFilter authenticationFilter;
    @Autowired
    private SecurityEntryPoint restAuthenticationEntryPoint;


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We don't need CSRF for this example
        http.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/v2/auth/**").anonymous().
                // all other requests need to be authenticated
                        anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().securityContext();
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
