package com.glqdlt.sso.grantserver.entity;

import javax.persistence.*;

/**
 * @author Jhun
 * 2020-02-06
 */
@Entity
@Table(name = "007B_user_authorities_legacy")
@DiscriminatorValue("1")
public class LegacyUserAuthoritiesEntity extends UserAuthorities {

    private LegacyAuthorityEntity legacyAuthorityEntity;

    @ManyToOne
    @JoinColumn(name = "legacy_auth_id")
    public LegacyAuthorityEntity getLegacyAuthorityEntity() {
        return legacyAuthorityEntity;
    }

    public void setLegacyAuthorityEntity(LegacyAuthorityEntity legacyAuthorityEntity) {
        this.legacyAuthorityEntity = legacyAuthorityEntity;
    }

    @Transient
    @Override
    public String getAuthority() {
        return getLegacyAuthorityEntity().getAuth();
    }
}
