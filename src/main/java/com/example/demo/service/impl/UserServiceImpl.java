package com.example.demo.service.impl;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepository.getReferenceById(id);
    }

    @Override
    public List<User> getALlUser() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> findByFullName(String name) {
        return this.userRepository.findByFullName(name);
    }

    @Override
    @Transactional
    public Boolean addUser(User user) {
        try {
            this.userRepository.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public User update(User user) {
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public Boolean deleteUserById(int id) {
        try{
            this.userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteUserByEmail(String email) {
        this.userRepository.deleteByEmail(email);
    }

}
