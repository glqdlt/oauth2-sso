package com.glqdlt.sso.grantserver.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author Jhun
 * @see <a href='https://www.baeldung.com/spring-security-5-oauth2-login'>https://www.baeldung.com/spring-security-5-oauth2-login</a>
 * @see <a href='https://spring.io/guides/tutorials/spring-boot-oauth2/'>https://spring.io/guides/tutorials/spring-boot-oauth2/</a>
 * 2020-02-06
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private MyClientDetailService fClientDetailService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(fClientDetailService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenStore tokenStore = tokenStore();
        DefaultTokenServices resourceServerTokenServices = new DefaultTokenServices();
        resourceServerTokenServices.setTokenStore(tokenStore);

        OAuth2AuthenticationManager auth2AuthenticationManager = new OAuth2AuthenticationManager();
        auth2AuthenticationManager.setClientDetailsService(fClientDetailService);
        auth2AuthenticationManager.setTokenServices(resourceServerTokenServices);
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(auth2AuthenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

}
