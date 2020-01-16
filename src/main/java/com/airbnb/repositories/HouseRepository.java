package com.airbnb.repositories;

import com.airbnb.models.Category;
import com.airbnb.models.House;
import com.airbnb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByUser(User user);

    List<House> findByCategory(Category user);

    List<House> findByHouseName(String name);
}
