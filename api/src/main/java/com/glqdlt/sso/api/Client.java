package com.glqdlt.sso.api;

/**
 * @author Jhun
 * 2020-02-04
 */
public interface Client {
    String getName();
    String getSecretKey();
    Integer getType();
    Integer getId();
}
