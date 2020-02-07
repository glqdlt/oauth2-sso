package com.glqdlt.sso.grantserver.api;

/**
 * @author Jhun
 * 2020-02-04
 */
public interface User {
    String getId();
    String getEmail();
    Boolean getLock();
    String getPassword();
}
