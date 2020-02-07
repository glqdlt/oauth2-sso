package com.glqdlt.sso.grantserver.entity;

import javax.persistence.*;

/**
 * @author Jhun
 * 2020-02-06
 */
@Entity
@Table(name = "008_user_authorities_book_app")
@DiscriminatorValue("2")
public class BookAppUserAuthoritiesEntityMenuCode extends BookAppUserAuthoritiesEntity {

    private BookAppMenu menu;

    @ManyToOne
    @JoinColumn(name = "authority_menu_id")
    public BookAppMenu getMenu() {
        return menu;
    }

    public void setMenu(BookAppMenu menu) {
        this.menu = menu;
    }

    @Transient
    @Override
    public String getAuthority() {
        return getMenu().getCode();
    }
}
