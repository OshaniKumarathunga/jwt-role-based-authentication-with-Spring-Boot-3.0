package com.springboot3.jwtauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Role {
    @Id
    private String roleName;
    private String roleDescription;
}
