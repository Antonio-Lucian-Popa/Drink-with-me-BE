package com.asusoftware.Drink_with_me.location_api.model.dto;

import com.asusoftware.Drink_with_me.location_api.model.County;
import lombok.Data;

import java.util.UUID;

@Data
public class CountyDto {

    private UUID id;
    private String name;

    public static CountyDto toDto(County county) {
        CountyDto dto = new CountyDto();
        dto.setId(county.getId());
        dto.setName(county.getName());
        return dto;
    }

    public static County toEntity(CountyDto dto) {
        County county = new County();
        county.setId(dto.getId());
        county.setName(dto.getName());
        return county;
    }
}
