package com.acorn.api.security.user;

import com.acorn.api.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public String getUserUUId() {
        return user.getUserUUId();
    }

    public String getUserDisplayName() {
        return user.getUserDisplayName();
    }

    public String getUserTel() {
        return user.getUserTel();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
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
