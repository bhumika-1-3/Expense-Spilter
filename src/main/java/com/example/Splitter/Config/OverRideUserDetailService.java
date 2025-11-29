package com.example.Splitter.Config;

import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OverRideUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByname(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("User found {}",username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword())     // must be encoded
                .build();
    }
}
