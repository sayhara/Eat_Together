package com.qwon.eat_together.config;

import com.qwon.eat_together.service.AccountService;
import com.qwon.eat_together.social.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration // Bean에 등록
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;
    private final DataSource dataSource;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/sign-up","/login","/search/meeting").permitAll()
                .mvcMatchers(HttpMethod.GET,"/profile/*").permitAll()
                .anyRequest().authenticated();

        http.oauth2Login()
                        .loginPage("/login").permitAll(); // // Custom oauth2 Login Page url

        http.formLogin()
                        .loginPage("/login").permitAll(); // Custom normal Login Page url

        http.logout()
                .logoutSuccessUrl("/"); // Custom Logout Page url

        http.rememberMe()
                .userDetailsService(accountService) // UserDetailsService
                .tokenRepository(tokenRepository()); // save token in db

        http.oauth2Login() // Oauth2 로그인 설정 시작점
                .userInfoEndpoint() // Oauth2 로그인 성공 후 사용자 정보를 가져올 때의 설정들
                .userService(customOAuth2UserService); // Oauth2 로그인 성공시, 후작업을 진행할 UserService 인터페이스 구현체 등록

    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring() // js, css, image file settings
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
