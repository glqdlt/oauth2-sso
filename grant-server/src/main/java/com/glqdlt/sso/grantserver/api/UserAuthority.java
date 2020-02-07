package com.glqdlt.sso.grantserver.api;

/**
 * @author Jhun
 * 2020-02-04
 */
public interface UserAuthority<U extends User, C extends Client, A extends Authority> {
    U getUser();

    Integer getType();

    C getClient();

    A[] getAuthorities();
}
