package com.glqdlt.sso.grantserver.entity;

import javax.persistence.*;

/**
 * @author Jhun
 * 2020-02-06
 */
@Table(name = "008_new_user_authorities")
@Entity
@DiscriminatorValue("2")
public class NewUserAuthoritiesEntity extends UserAuthorities {
    private BookAppUserAuthoritiesEntity bookAppUserAuthoritiesEntity;

    @OneToOne
    @JoinColumn(name = "book_app_auth_id")
    public BookAppUserAuthoritiesEntity getBookAppUserAuthoritiesEntity() {
        return bookAppUserAuthoritiesEntity;
    }

    public void setBookAppUserAuthoritiesEntity(BookAppUserAuthoritiesEntity bookAppUserAuthoritiesEntity) {
        this.bookAppUserAuthoritiesEntity = bookAppUserAuthoritiesEntity;
    }

    @Transient
    @Override
    public String getAuthority() {
        return bookAppUserAuthoritiesEntity.getAuthority();
    }
}
