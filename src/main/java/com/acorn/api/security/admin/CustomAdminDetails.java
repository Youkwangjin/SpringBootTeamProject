package com.acorn.api.security.admin;

import com.acorn.api.entity.admin.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomAdminDetails implements UserDetails {

    private final Admin admin;

    public CustomAdminDetails(Admin admin) {
        this.admin = admin;
    }

    public Integer getAdminId() {
        return admin.getAdminId();
    }

    public String getAdminNm() {
        return admin.getAdminNm();
    }

    @Override
    public String getUsername() {
        return admin.getAdminEmail();
    }

    @Override
    public String getPassword() {
        return admin.getAdminPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + admin.getAdminRole()));
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