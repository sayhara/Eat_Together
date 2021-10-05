package com.qwon.eat_together.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // Bean에 등록
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/sign-up","/login").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login").permitAll(); // Custom Login Page url

        http.logout()
                .logoutSuccessUrl("/"); // Custom Logout Page url
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring() // js, css, image file settings
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
