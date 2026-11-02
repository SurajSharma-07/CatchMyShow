package com.example.movietickets.model;

import jakarta.persistence.*;

@Entity
@Table(name="app_users")
public class User {
    public enum Role { ADMIN, CUSTOMER }
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private String fullName;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
    public User() {}
    public User(String email,String password,String fullName,Role role){this.email=email;this.password=password;this.fullName=fullName;this.role=role;}
    public Long getId(){return id;} public String getEmail(){return email;} public String getPassword(){return password;} public String getFullName(){return fullName;} public Role getRole(){return role;}
}
