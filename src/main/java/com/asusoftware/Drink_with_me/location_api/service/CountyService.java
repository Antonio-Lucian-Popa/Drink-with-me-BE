package com.asusoftware.Drink_with_me.location_api.service;

import com.asusoftware.Drink_with_me.location_api.model.County;
import com.asusoftware.Drink_with_me.location_api.model.dto.CountyDto;
import com.asusoftware.Drink_with_me.location_api.repository.CountyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CountyService {

    @Autowired
    private CountyRepository countyRepository;

    // Method to get all counties
    public List<CountyDto> getAllCounties() {
        return countyRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Method to get a county by ID
    public CountyDto getCountyById(UUID id) {
        County county = countyRepository.findById(id).orElseThrow(() -> new RuntimeException("County not found"));
        return convertToDto(county);
    }

    // Conversion methods between Entity and DTO
    private CountyDto convertToDto(County county) {
        return CountyDto.toDto(county);
    }
}