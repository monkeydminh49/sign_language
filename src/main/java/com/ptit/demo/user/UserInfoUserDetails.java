package com.ptit.demo.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserInfoUserDetails implements UserDetails {

    private String name;
    private final String email;
    private final String password;
//    private List<Role> roles;

    public UserInfoUserDetails(UserInfo userInfo) {
        this.name = userInfo.getName();
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
//        this.roles = userInfo.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
