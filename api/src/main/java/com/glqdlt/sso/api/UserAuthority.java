package com.glqdlt.sso.api;

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
