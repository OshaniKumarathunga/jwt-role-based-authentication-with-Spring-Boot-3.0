package com.springboot3.jwtauthentication.repo;

import com.springboot3.jwtauthentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role , String> {
    Optional<Role> findByRoleName(String roleName);
}
