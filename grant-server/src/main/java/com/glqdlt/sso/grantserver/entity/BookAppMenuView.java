package com.glqdlt.sso.grantserver.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jhun
 * 2020-02-06
 */
@Entity
@Table(name = "008_book_app_menu")
@DiscriminatorValue("1")
public class BookAppMenuView extends BookAppMenu {
}
