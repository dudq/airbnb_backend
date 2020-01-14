package com.airbnb.repositories;

import com.airbnb.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Long> {

    List<Rate> findAllByHouse(Long id);

    boolean existsRateByUserAndHouse(Long userId, Long houseId);

    Rate findByUserAndHouse(Long userId, Long houseId);
}
