package com.airbnb.repositories;

import com.airbnb.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByHost(Long hostId);

    House findByHouseName(String name);
}
