package com.airbnb.repositories;

import com.airbnb.models.House;
import com.airbnb.models.OrderHouse;
import com.airbnb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHouseRepository extends JpaRepository<OrderHouse, Long> {
    List<OrderHouse> findByHouse(House house);

    List<OrderHouse> findByUser(User user);
}
