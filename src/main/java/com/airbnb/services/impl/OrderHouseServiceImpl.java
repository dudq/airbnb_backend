package com.airbnb.services.impl;

import com.airbnb.exceptions.InvalidRequestException;
import com.airbnb.models.House;
import com.airbnb.models.OrderHouse;
import com.airbnb.models.Status;
import com.airbnb.models.StatusOrderHouse;
import com.airbnb.repositories.OrderHouseRepository;
import com.airbnb.services.HouseService;
import com.airbnb.services.OrderHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHouseServiceImpl implements OrderHouseService {

    @Autowired
    private OrderHouseRepository orderHouseRepository;

    @Autowired
    private HouseService houseService;

    @Override
    public List<OrderHouse> findAll() {
        return orderHouseRepository.findAll();
    }

//    @Override
//    public OrderHouse findByName(String name) {
//        return orderHouseRepository.findByName(name);
//    }

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
        House house = houseService.findById(orderHouse.getHouse().getId());
        house.setStatus(Status.BOOKED);
        houseService.updateHouse(house);
        orderHouse.setStatus(StatusOrderHouse.BOOKING);
        orderHouseRepository.save(orderHouse);
    }

    @Override
    public void updateOrderHouse(OrderHouse orderHouse) {
        orderHouseRepository.save(orderHouse);
    }

    @Override
    public void checkIn(OrderHouse orderHouse) {
        House house = houseService.findById(orderHouse.getHouse().getId());
        house.setStatus(Status.UNAVAILABLE);
        houseService.updateHouse(house);
        orderHouse.setStatus(StatusOrderHouse.CHECKIN);
        orderHouseRepository.save(orderHouse);
    }

    @Override
    public void checkOut(OrderHouse orderHouse) {
        House house = houseService.findById(orderHouse.getHouse().getId());
        house.setStatus(Status.AVAILABLE);
        houseService.updateHouse(house);
        orderHouse.setStatus(StatusOrderHouse.CHECKOUT);
        orderHouseRepository.save(orderHouse);
    }

    @Override
    public void deleteOrderHouse(Long id) {
        orderHouseRepository.deleteById(id);
    }
}
