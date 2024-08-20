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

    // Other fields like latitude, longitude can also be added for more detailed location filtering

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
}