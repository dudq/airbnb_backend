package com.airbnb.repositories;

import com.airbnb.models.OrderHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHouseRepository extends JpaRepository<OrderHouse, Long> {
    OrderHouse findByName(String name);
}
