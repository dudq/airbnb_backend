package com.airbnb.services.impl;

import com.airbnb.exceptions.InvalidRequestException;
import com.airbnb.models.OrderHouse;
import com.airbnb.repositories.OrderHouseRepository;
import com.airbnb.services.OrderHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHouseServiceImpl implements OrderHouseService {

    @Autowired
    private OrderHouseRepository orderHouseRepository;

    @Override
    public List<OrderHouse> findAll() {
        return orderHouseRepository.findAll();
    }

    @Override
    public OrderHouse findByName(String name) {
        return orderHouseRepository.findByName(name);
    }

    @Override
    public OrderHouse findById(Long id) {
        OrderHouse orderHouse = orderHouseRepository.findById(id).get();

        if (orderHouse == null) {
            throw new InvalidRequestException("Order House is not existed");
        }
        return orderHouse;
    }

    @Override
    public void createOrderHouse(OrderHouse orderHouse) {
        orderHouseRepository.save(orderHouse);
    }

    @Override
    public void updateOrderHouse(OrderHouse orderHouse) {
        orderHouseRepository.save(orderHouse);
    }

    @Override
    public void deleteOrderHouse(Long id) {
        orderHouseRepository.deleteById(id);
    }
}
