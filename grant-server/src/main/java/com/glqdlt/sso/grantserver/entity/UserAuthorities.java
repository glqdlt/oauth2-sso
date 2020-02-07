package com.glqdlt.sso.grantserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Jhun
 * 2020-02-06
 */
@Entity
@Table(name = "007_user_authorities")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "auth_type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class UserAuthorities implements GrantedAuthority {
    private Integer id;
    private ClientEntity clientEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }


    private UserEntity userEntity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_no")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Transient
    public String getClientName() {
        return getClientEntity().getName();
    }
}
