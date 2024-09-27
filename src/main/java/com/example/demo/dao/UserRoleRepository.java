package com.example.demo.dao;

import com.example.demo.entity.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<User_Role, Integer> {
}
