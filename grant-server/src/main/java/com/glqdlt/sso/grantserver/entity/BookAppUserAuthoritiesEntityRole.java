package com.glqdlt.sso.grantserver.entity;

import javax.persistence.*;

/**
 * @author Jhun
 * 2020-02-06
 */
@Entity
@Table(name = "008_user_authorities_book_app")
@DiscriminatorValue("1")
public class BookAppUserAuthoritiesEntityRole extends BookAppUserAuthoritiesEntity {
    private String role;

    @Column(name = "authority_role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Transient
    @Override
    public String getAuthority() {
        return getRole();
    }
}
