package com.glqdlt.sso.grantserver.entity;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Jhun
 * 2020-02-04
 */
@Entity
@Table(name = "005_user")
public class UserEntity implements UserDetails {
    private Integer no;
    private String userId;
    private String email;
    private Boolean globalLock;
    private Boolean globalIpCheck;
    private Boolean needChangePassword;
    private String name;
    private String password;
    private LocalDateTime regDate;
    private String description;
    private List<UserAuthorities> userAuthorities;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userEntity")
    public List<UserAuthorities> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(List<UserAuthorities> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    @Id
    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    @Column(name = "id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "global_lock")
    public Boolean getGlobalLock() {
        return globalLock;
    }

    public void setGlobalLock(Boolean globalLock) {
        this.globalLock = globalLock;
    }

    @Column(name = "ip_check")
    public Boolean getGlobalIpCheck() {
        return globalIpCheck;
    }

    public void setGlobalIpCheck(Boolean globalIpCheck) {
        this.globalIpCheck = globalIpCheck;
    }

    @Column(name = "need_change_password")
    public Boolean getNeedChangePassword() {
        return needChangePassword;
    }

    public void setNeedChangePassword(Boolean needChangePassword) {
        this.needChangePassword = needChangePassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    @Override
    public Collection<UserAuthorities> getAuthorities() {
        return getUserAuthorities();
    }

    public String getPassword() {
        return password;
    }

    @Transient
    @Override
    public String getUsername() {
        return getName();
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return !getGlobalLock();
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return !getGlobalLock();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "reg_date")
    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
