package com.example.demo.service.impl;

import com.example.demo.entity.CustomUserDetail;
import com.example.demo.entity.User;
import com.example.demo.entity.User_Role;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@Service
public class CustomUserDetailService implements UserDetailsService {
    public static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
    private UserService userService;

    @Autowired
    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Thông báo:  user by email: {}", email);
        User user = userService.getUserByEmail(email);
        if (user==null){
            throw new UsernameNotFoundException("sai");
        }
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        List<User_Role> roles =  user.getUserRoles();

        for(User_Role e: roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(e.getRole().getRoleName()));
            logger.info("roles: {}", e.getRole().getRoleName());

        }
        logger.info("User found with email: {}, roles: {}", email, roles);
        return new CustomUserDetail(user, grantedAuthorities);
    }

}
