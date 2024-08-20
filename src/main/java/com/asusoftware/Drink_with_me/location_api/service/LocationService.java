package com.asusoftware.Drink_with_me.location_api.service;

import com.asusoftware.Drink_with_me.location_api.model.Location;
import com.asusoftware.Drink_with_me.location_api.model.dto.LocationDto;
import com.asusoftware.Drink_with_me.location_api.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    // Method to get all locations
    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Method to get a location by ID
    public LocationDto getLocationById(UUID id) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
        return convertToDto(location);
    }

    // Method to create a new location
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = convertToEntity(locationDto);
        Location savedLocation = locationRepository.save(location);
        return convertToDto(savedLocation);
    }

    // Method to update an existing location
    public LocationDto updateLocation(UUID id, LocationDto locationDto) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
        location.setName(locationDto.getName());
        Location updatedLocation = locationRepository.save(location);
        return convertToDto(updatedLocation);
    }

    // Method to delete a location
    public void deleteLocation(UUID id) {
        locationRepository.deleteById(id);
    }

    // Conversion methods between Entity and DTO
    private LocationDto convertToDto(Location location) {
        return LocationDto.toDto(location);
    }

    private Location convertToEntity(LocationDto locationDto) {
        return LocationDto.toEntity(locationDto);
    }
}