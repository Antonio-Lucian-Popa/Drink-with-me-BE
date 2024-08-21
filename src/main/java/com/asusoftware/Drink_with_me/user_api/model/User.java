package com.asusoftware.Drink_with_me.user_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "email", nullable = false, length = 50)
    private String email; // Unique identifier for the user

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false; // Account activation status

    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "lives_in")
    private String livesIn;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "token")
    private String token;

    // Relationship with follower users
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    // Relationship with following users
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "followers")
    private Set<User> following = new HashSet<>();
}
