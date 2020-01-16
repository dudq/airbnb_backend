package com.airbnb.repositories;

import com.airbnb.models.ImageOfHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageOfHouseRepository extends JpaRepository<ImageOfHouse, Long> {
//    List<ImageOfHouse> findByHouse(Long id);
}
