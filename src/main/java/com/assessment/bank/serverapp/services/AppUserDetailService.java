package com.assessment.bank.serverapp.services;

import com.assessment.bank.serverapp.models.AppUserDetail;
import com.assessment.bank.serverapp.models.User;
import com.assessment.bank.serverapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.
                findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Customer %s has been deleted!", username)));
        return new AppUserDetail(user);
    }
}
