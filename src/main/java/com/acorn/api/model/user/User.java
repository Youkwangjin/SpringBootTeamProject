package com.acorn.api.model.user;

import com.acorn.api.role.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private int userId;

    private String userUUId;

    private String userEmail;

    private String userPassword;

    private String userDisplayName;

    private String userTel;

    private String userAddr;

    private UserRole userRole;

    @Override
    public String getUsername() {
        return getUserEmail();
    }

    @Override
    public String getPassword() {
        return getUserPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + getUserRole()));
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
