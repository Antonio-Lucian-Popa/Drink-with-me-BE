package com.asusoftware.Drink_with_me.location_api.model;

import com.asusoftware.Drink_with_me.post_api.model.Post;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "Location")
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Relationship with County
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id", nullable = false)
    private County county;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
}