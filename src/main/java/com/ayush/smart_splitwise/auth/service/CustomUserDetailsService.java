package com.ayush.smart_splitwise.auth.service;

import com.ayush.smart_splitwise.auth.dto.CustomUserDetails;
import com.ayush.smart_splitwise.core.user.entity.User;
import com.ayush.smart_splitwise.core.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
