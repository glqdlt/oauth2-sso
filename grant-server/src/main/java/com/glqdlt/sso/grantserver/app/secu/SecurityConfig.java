package com.glqdlt.sso.grantserver.app.secu;

import com.glqdlt.sso.grantserver.entity.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Jhun
 * 2020-02-06
 * @see <a href='https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/htmlsingle/#do-i-need-to-stand-up-my-own-authorization-server'>https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/htmlsingle/#do-i-need-to-stand-up-my-own-authorization-server</a>
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/oauth/**").permitAll()
                .and()
                .httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService(@Autowired UserEntityRepo userEntityRepo) {
        return new MyUserDetailService(userEntityRepo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }


}
