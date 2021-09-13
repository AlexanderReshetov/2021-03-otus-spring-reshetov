package ru.otus.loader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            final UriComponents logoutRedirect = UriComponentsBuilder.fromHttpUrl("https://battle.net")
                    .pathSegment("login", "logout")
                    .queryParam("ref", "http://localhost:8081/login")
                    .build();
            response.sendRedirect(logoutRedirect.toString());
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and().oauth2Client()
                .and().oauth2Login()
                .and().logout().logoutSuccessHandler(logoutSuccessHandler());
    }
}