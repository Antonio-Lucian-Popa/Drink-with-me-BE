package com.asusoftware.Drink_with_me.location_api.model.dto;

import com.asusoftware.Drink_with_me.location_api.model.Location;
import lombok.Data;

import java.util.UUID;

@Data
public class LocationDto {

    private UUID id;
    private String name;

    public static LocationDto toDto(Location location) {
        LocationDto dto = new LocationDto();
        dto.setId(location.getId());
        dto.setName(location.getName());
        return dto;
    }

    public static Location toEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setId(locationDto.getId());
        location.setName(locationDto.getName());
        return location;
    }
}
