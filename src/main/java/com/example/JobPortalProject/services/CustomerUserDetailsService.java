package com.example.JobPortalProject.services;

import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.repository.UsersRepository;
import com.example.JobPortalProject.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Dịch vụ này triển khai UserDetailsService của Spring Security để tải thông tin người dùng.no
public class CustomerUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomerUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(username).orElseThrow(
                ()->new UsernameNotFoundException("Could not found user")
        );
        return new CustomUserDetails(users);
    }
}
