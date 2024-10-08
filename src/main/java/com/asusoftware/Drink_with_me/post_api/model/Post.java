package com.asusoftware.Drink_with_me.post_api.model;

import com.asusoftware.Drink_with_me.location_api.model.Location;
import com.asusoftware.Drink_with_me.user_api.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Post")
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relationship with post likes
    @ManyToMany
    @JoinTable(
            name = "user_post_participants",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();


    // Relationship with comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_filename")
    private List<String> imageFilenames = new ArrayList<>();

}