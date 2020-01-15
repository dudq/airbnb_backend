package com.airbnb.repositories;

import com.airbnb.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    List<Rate> findAllByHouse(Long id);

    boolean existsRateByUserAndHouse(Long userId, Long houseId);

    Rate findByUserAndHouse(Long userId, Long houseId);
}
