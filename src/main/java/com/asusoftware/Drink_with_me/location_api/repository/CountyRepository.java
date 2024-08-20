package com.asusoftware.Drink_with_me.location_api.repository;

import com.asusoftware.Drink_with_me.location_api.model.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CountyRepository extends JpaRepository<County, UUID> {
}
