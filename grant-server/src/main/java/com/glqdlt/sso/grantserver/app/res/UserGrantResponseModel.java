package com.glqdlt.sso.grantserver.app.res;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jhun
 * 2020-02-07
 */
public class UserGrantResponseModel<G extends GrantedAuthority> {
    private Integer userNo;
    private String userId;
    private String userEmail;
    private String userName;
    private List<G> userAuthority;
    private final HashMap<String, Object> userDetail = new HashMap<>();

    public UserGrantResponseModel(Integer userNo, String userId, String userEmail, String userName, List<G> userAuthority) {
        this.userNo = userNo;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAuthority = userAuthority;
        this.userDetail.put("userNo", getUserNo());
        this.userDetail.put("userId", getUserId());
        this.userDetail.put("userEmail", getUserEmail());
        this.userDetail.put("userName", getUserName());
    }

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

    public List<G> getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(List<G> userAuthority) {
        this.userAuthority = userAuthority;
    }

    public Map<String, Object> getAttributes() {
        return userDetail;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserAuthority();
    }

    public String getName() {
        return getUserId();
    }
}
