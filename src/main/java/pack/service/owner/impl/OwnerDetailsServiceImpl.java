package pack.service.owner.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pack.model.owner.Owner;
import pack.repository.owner.OwnerRepository;

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
        return owner;
    }
}
