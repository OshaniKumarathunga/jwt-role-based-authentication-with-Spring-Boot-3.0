package com.springboot3.jwtauthentication.repo;

import com.springboot3.jwtauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User , String> {
}
