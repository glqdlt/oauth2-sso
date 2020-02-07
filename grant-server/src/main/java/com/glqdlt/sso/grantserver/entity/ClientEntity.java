package com.glqdlt.sso.grantserver.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * @author Jhun
 * 2020-02-04
 */
@Entity
@Table(name = "001_client")
public class ClientEntity implements ClientDetails {
    private Integer id;
    private String name;
    private String secretKey;
    private LocalDateTime regDate;
    private List<ClientScopeEntity> clientScopeEntity;
    private ServiceEntity serviceEntity;
    private String redirectUrl;
    private Integer accessTokenAllowMinutes;
    private Integer refreshTokenAllowMinutes;

    @Column(name = "access_token_allow_minutes")
    public Integer getAccessTokenAllowMinutes() {
        return accessTokenAllowMinutes;
    }

    public void setAccessTokenAllowMinutes(Integer accessTokenAllowMinutes) {
        this.accessTokenAllowMinutes = accessTokenAllowMinutes;
    }

    @Column(name = "refresh_token_allow_minutes")
    public Integer getRefreshTokenAllowMinutes() {
        return refreshTokenAllowMinutes;
    }

    public void setRefreshTokenAllowMinutes(Integer refreshTokenAllowMinutes) {
        this.refreshTokenAllowMinutes = refreshTokenAllowMinutes;
    }

    @Column(name = "redirect_url")
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @JoinTable(
            name = "001_client_with_service",
            joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id")
    )
    @ManyToOne(fetch = FetchType.EAGER)
    public ServiceEntity getServiceEntity() {
        return serviceEntity;
    }

    public void setServiceEntity(ServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "clientEntity")
    public List<ClientScopeEntity> getClientScopeEntity() {
        return clientScopeEntity;
    }

    public void setClientScopeEntity(List<ClientScopeEntity> clientScopeEntity) {
        this.clientScopeEntity = clientScopeEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "secret_key")
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Column(name = "reg_date")
    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    @Transient
    @Override
    public String getClientId() {
        return getName();
    }

    @Transient
    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Transient
    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Transient
    @Override
    public String getClientSecret() {
        return getSecretKey();
    }

    @Transient
    @Override
    public boolean isScoped() {
        return true;
    }

    @Transient
    @Override
    public Set<String> getScope() {
        return getClientScopeEntity().stream()
                .map(ClientScopeEntity::getScope)
                .collect(toSet());
    }

    @Transient
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return Stream.of("authorization_code").collect(toSet());
    }

    @Transient
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Stream.of(getRedirectUrl()).collect(toSet());
    }

    @Transient
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Transient
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return (int) TimeUnit.MINUTES.toSeconds(getAccessTokenAllowMinutes());
    }

    @Transient
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return (int) TimeUnit.MINUTES.toSeconds(getRefreshTokenAllowMinutes());
    }

    @Transient
    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Transient
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
