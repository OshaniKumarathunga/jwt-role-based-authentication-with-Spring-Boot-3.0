package com.springboot3.jwtauthentication.repo;

import com.springboot3.jwtauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User , String> {
    Optional<User> findByUserName(String userName);
    Boolean existsByUserName(String userName);
}
