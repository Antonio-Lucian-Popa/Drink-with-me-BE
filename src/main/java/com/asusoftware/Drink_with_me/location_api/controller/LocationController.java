package com.asusoftware.Drink_with_me.location_api.controller;

import com.asusoftware.Drink_with_me.location_api.model.dto.CountyDto;
import com.asusoftware.Drink_with_me.location_api.model.dto.LocationDto;
import com.asusoftware.Drink_with_me.location_api.service.CountyService;
import com.asusoftware.Drink_with_me.location_api.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private CountyService countyService;

    // Endpoint to get all locations
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    // Endpoint to get a location by its ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable UUID id) {
        LocationDto location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }

    // New: Endpoint to get locations by County ID
    @GetMapping("/by-county/{countyId}")
    public ResponseEntity<List<LocationDto>> getLocationsByCountyId(@PathVariable UUID countyId) {
        List<LocationDto> locations = locationService.getLocationsByCountyId(countyId);
        return ResponseEntity.ok(locations);
    }

    // Endpoint to create a new location
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        LocationDto createdLocation = locationService.createLocation(locationDto);
        return ResponseEntity.ok(createdLocation);
    }

    // Endpoint to update an existing location
    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable UUID id, @RequestBody LocationDto locationDto) {
        LocationDto updatedLocation = locationService.updateLocation(id, locationDto);
        return ResponseEntity.ok(updatedLocation);
    }

    // Endpoint to delete a location by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to get all counties
    @GetMapping("/counties")
    public ResponseEntity<List<CountyDto>> getAllCounties() {
        List<CountyDto> counties = countyService.getAllCounties();
        return ResponseEntity.ok(counties);
    }

    // Endpoint to get a county by its ID
    @GetMapping("/counties/{id}")
    public ResponseEntity<CountyDto> getCountyById(@PathVariable UUID id) {
        CountyDto county = countyService.getCountyById(id);
        return ResponseEntity.ok(county);
    }
}
