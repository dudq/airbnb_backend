package com.airbnb.services;

import com.airbnb.models.OrderHouse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderHouseService {
    List<OrderHouse> findAll();

//    OrderHouse findByName(String name);

    OrderHouse findById(Long id);

    void createOrderHouse(OrderHouse orderHouse) throws Exception;

    void updateOrderHouse(OrderHouse orderHouse);

    void checkIn(OrderHouse orderHouse);

    void checkOut(OrderHouse orderHouse);

    void deleteOrderHouse(Long id);
}
