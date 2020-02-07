package com.glqdlt.sso.grantserver.app.res;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author Jhun
 * 2020-02-07
 */
public class UserGrantResponseModel {
    private Integer userNo;
    private String userId;
    private String userEmail;
    private String userName;
    private List<GrantedAuthority> userAuthority;

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<GrantedAuthority> getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(List<GrantedAuthority> userAuthority) {
        this.userAuthority = userAuthority;
    }
}
