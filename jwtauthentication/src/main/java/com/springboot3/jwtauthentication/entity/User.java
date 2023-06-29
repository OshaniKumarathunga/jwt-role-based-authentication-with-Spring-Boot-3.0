package com.springboot3.jwtauthentication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;


@Getter
@Setter
@Table
@Entity
public class User {
    //private Integer id;
    @Id
    private String userName;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;
}
