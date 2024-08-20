package com.asusoftware.Drink_with_me.location_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "counties")
public class County {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Relationship with Locations
    @OneToMany(mappedBy = "county", fetch = FetchType.LAZY)
    private List<Location> locations;

}