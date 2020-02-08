package com.glqdlt.sso.clientA;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jhun
 * 2020-02-04
 */
@Slf4j
@ConditionalOnProperty(value = "enable.auth", havingValue = "true")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private String authServiceName;
    private String clientId;
    private String clientSecret;
    private String authorizationUrl;
    private String clientAccessScope;
    private String generateAccessTokenUrl;
    private String myRedirectUrl;
    private String userGrantResourceServerUrl;

    public SecurityConfig(@Autowired Environment environment) {
        this.fieldInit(environment);
    }

    private void fieldInit(Environment environment) {
        this.myRedirectUrl = environment.getProperty("my.oauth.client.redirectUrl");
        this.clientAccessScope = environment.getProperty("my.oauth.client.scope");
        this.authServiceName = environment.getProperty("my.oauth.auth.server.name");
        this.generateAccessTokenUrl = environment.getProperty("my.oauth.token.server.url");
        this.authorizationUrl = environment.getProperty("my.oauth.auth.server.url");
        this.clientSecret = environment.getProperty("my.oauth.client.secret");
        this.clientId = environment.getProperty("my.oauth.client.id");
        this.userGrantResourceServerUrl = environment.getProperty("my.oauth.grant.server.url");
    }

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    /**
     * @return
     * @see <a href='https://www.baeldung.com/spring-security-5-oauth2-login'>https://www.baeldung.com/spring-security-5-oauth2-login</a>
     * @see <a href='https://docs.spring.io/spring-security/site/docs/current/reference/html/oauth2.html'>https://docs.spring.io/spring-security/site/docs/current/reference/html/oauth2.html</a>
     */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {

        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId(authServiceName)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientId(clientId)
                .userNameAttributeName("userId")
                .clientSecret(clientSecret)
                .authorizationUri(authorizationUrl)
                .scope(clientAccessScope)
                .tokenUri(generateAccessTokenUrl)
                .redirectUriTemplate(myRedirectUrl)
                .userInfoUri(userGrantResourceServerUrl)
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);

    }

    @SuppressWarnings("unchecked")
    private UsernamePasswordAuthenticationToken extractUserPrincipal(OAuth2AuthenticationToken oauthAuth) {
        //                                TODO Extractor 가 있긴 한데, 원하는 설계는 아닌거같다. 이거 쓸지 말지 고민좀해보자
//                                 https://www.baeldung.com/manually-set-user-authentication-spring-security
        LinkedHashMap userDetails = oauthAuth.getPrincipal().getAttribute("userDetail");
        ArrayList<LinkedHashMap> authorities = (ArrayList<LinkedHashMap>) userDetails.get("userAuthority");
        List<SimpleGrantedAuthority> originUserAuthorities = authorities.stream().map(x -> new SimpleGrantedAuthority(x.get("authority").toString())).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails.get("userId"), oauthAuth.getCredentials(), originUserAuthorities);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        if (authentication instanceof OAuth2AuthenticationToken) {
                            OAuth2AuthenticationToken oauthAuth = (OAuth2AuthenticationToken) authentication;
                            String oauth2ProviderId = oauthAuth.getAuthorizedClientRegistrationId();

                            if (oauth2ProviderId.equals("google")) {
//                                   TODO 나중에 구글 연동하게되면 -_- method fill
                            } else if (oauth2ProviderId.equals("mySSO")) {
                                SecurityContextHolder.getContext()
                                        .setAuthentication(extractUserPrincipal(oauthAuth));
                            } else {
                                throw new RuntimeException(String.format("Not Supported ProviderId %s", oauth2ProviderId));
                            }

                        }
                        httpServletResponse.sendRedirect("/");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        log.error(e.getMessage(), e);
                    }
                })
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(oAuth2AuthorizedClientService());
    }
}
