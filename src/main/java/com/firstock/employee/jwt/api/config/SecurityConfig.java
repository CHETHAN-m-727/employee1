package com.firstock.employee.jwt.api.config;

import com.firstock.employee.jwt.api.filter.JwtFilter;
import com.firstock.employee.jwt.api.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableSwagger2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("chethan")
                .password(passwordEncoder().encode("chethan123"))
                .authorities("ADMIN");
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/emp/authenticate",
                        "/users",
                        "/adduser",
                        "/swagger-ui/**",
                        "/v2/api-docs/**",
                        "/swagger-resources/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .httpBasic().and()
                .exceptionHandling().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        ;
    }


}