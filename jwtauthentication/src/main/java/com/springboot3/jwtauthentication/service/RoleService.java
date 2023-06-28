package com.springboot3.jwtauthentication.service;

import com.springboot3.jwtauthentication.entity.Role;
import com.springboot3.jwtauthentication.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

        @Autowired
        private RoleRepo roleRepo;

        public Role createNewRole(Role role) {
            return roleRepo.save(role);
        }

}
