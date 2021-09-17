package ru.zheleznov.web.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.web.security.jwt.TokenAuthenticationFilter;
import ru.zheleznov.web.security.jwt.TokenAuthenticationProvider;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAt(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().disable()
                .authorizeRequests()
                    .antMatchers("/api/auth/**", "/talk/create").permitAll()
                    .antMatchers(HttpMethod.POST, "/talk").hasAuthority(User.Role.SPEAKER.toString())
                    .antMatchers(HttpMethod.PUT, "/talk/**").hasAuthority(User.Role.SPEAKER.toString())
                    .antMatchers(HttpMethod.DELETE, "/talk/**").hasAuthority(User.Role.SPEAKER.toString())
                    .antMatchers(HttpMethod.GET, "/talk/**").permitAll()
                    .antMatchers("/talk/**").authenticated()
                    .antMatchers( "/user/**").hasAuthority(User.Role.ADMIN.toString())
                    .antMatchers("/","/home","/register","/login").permitAll()
                    .antMatchers("/css","/js").permitAll().and();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
}
