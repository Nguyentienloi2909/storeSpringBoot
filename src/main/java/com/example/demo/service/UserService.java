package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User getUserByEmail(String email);
    public User getUserById(int id);
    public List<User> getALlUser();
    public List<User> findByFullName(String name);
    public Boolean addUser(User user);
    public User update(User user);
    public Boolean deleteUserById(int id);
    public void deleteUserByEmail(String email);

}
