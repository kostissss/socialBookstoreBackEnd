package com.example.socialBookStore.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.socialBookStore.models.User;
import com.example.socialBookStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private Set<GrantedAuthority> set = new HashSet<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            new UsernameNotFoundException("User not exists by Username");
        }
        GrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_USER");
        set.add(authorities);

        return new org.springframework.security.core.userdetails.User(username, user.get().getPasswordHash(), set);
    }

}