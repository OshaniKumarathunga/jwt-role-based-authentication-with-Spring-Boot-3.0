package com.springboot3.jwtauthentication.service;

import com.springboot3.jwtauthentication.entity.Role;
import com.springboot3.jwtauthentication.entity.User;
import com.springboot3.jwtauthentication.repo.RoleRepo;
import com.springboot3.jwtauthentication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        Role role = roleRepo.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(getEncodedPassword(user.getPassword()));

        return userRepo.save(user);
    }

//    public void initRoleAndUser() {
//
//        Role adminRole = new Role();
//        adminRole.setRoleName("Admin");
//        adminRole.setRoleDescription("Admin role 5555");
//        roleRepo.save(adminRole);
//
//        Role userRole = new Role();
//        userRole.setRoleName("User");
//        userRole.setRoleDescription("Default role for newly created record");
//        roleRepo.save(userRole);
//
//        User adminUser = new User();
//        adminUser.setUserName("admin123");
//        adminUser.setPassword(getEncodedPassword("admin@pass"));
//        adminUser.setEmail("admin@gmail.com");
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//        adminUser.setRole(adminRoles);
//        userRepo.save(adminUser);
//
//        User user = new User();
//        user.setUserName("raj123");
//        user.setPassword(getEncodedPassword("raj@123"));
//        user.setEmail("user@gmail.com");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userRepo.save(user);
//    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
