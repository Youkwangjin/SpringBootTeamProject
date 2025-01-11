package com.acorn.api.security.owner;

import com.acorn.api.entity.owner.Owner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomOwnerDetails implements UserDetails {

    private final Owner owner;

    public CustomOwnerDetails(Owner owner) {
        this.owner = owner;
    }

    public Integer getOwnerId() {
        return owner.getOwnerId();
    }

    public String getOwnerEmail() {
        return owner.getOwnerEmail();
    }

    public String getOwnerNm() {
        return owner.getOwnerNm();
    }

    public String getOwnerTel() {
        return owner.getOwnerTel();
    }

    public String getOwnerCompanyName() {
        return owner.getOwnerCompanyName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + owner.getOwnerRole()));
    }

    @Override
    public String getPassword() {
        return owner.getOwnerPassword();
    }

    @Override
    public String getUsername() {
        return owner.getOwnerBusinessNum();
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
