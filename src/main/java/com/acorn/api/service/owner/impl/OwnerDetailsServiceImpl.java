package com.acorn.api.service.owner.impl;

import com.acorn.api.model.owner.Owner;
import com.acorn.api.repository.owner.OwnerRepository;
import com.acorn.api.security.owner.CustomOwnerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerDetailsServiceImpl implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String ownerBusinessNum) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByOwnerBusinessNum(ownerBusinessNum);
        if (owner == null) {
            throw new UsernameNotFoundException("Owner not found with BusinessNumber: " + ownerBusinessNum);
        }
        return new CustomOwnerDetails(owner);
    }
}