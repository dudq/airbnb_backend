package com.airbnb.repositories;

import com.airbnb.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByHost(Long hostId);

    House findByHouseName(String name);
}
