package com.acorn.api.service.admin.impl;

import com.acorn.api.entity.admin.Admin;
import com.acorn.api.repository.admin.AdminRepository;
import com.acorn.api.security.admin.CustomAdminDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String adminEmail) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminEmail(adminEmail);
        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found with email: " + adminEmail);
        }
        return new CustomAdminDetails(admin);
    }
}