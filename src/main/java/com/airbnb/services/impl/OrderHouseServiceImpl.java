package com.airbnb.services.impl;

import com.airbnb.exceptions.InvalidRequestException;
import com.airbnb.models.House;
import com.airbnb.models.OrderHouse;
import com.airbnb.models.Status;
import com.airbnb.models.StatusOrderHouse;
import com.airbnb.repositories.OrderHouseRepository;
import com.airbnb.security.sevice.UserPrinciple;
import com.airbnb.services.HouseService;
import com.airbnb.services.OrderHouseService;
import com.airbnb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHouseServiceImpl implements OrderHouseService {

    @Autowired
    private OrderHouseRepository orderHouseRepository;

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<OrderHouse> findAll() throws Exception {
        try {
            return orderHouseRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Has some error");
        }
    }

    @Override
    public List<OrderHouse> findByHouse(Long houseId) throws Exception {
        Long currentUserId = getCurrentUser().getId();
        House house = houseService.findById(houseId);
        Long hostId = house.getUser().getId();
        if (currentUserId.equals(hostId)) {
            return orderHouseRepository.findByHouse(house);
        } else {
            throw new Exception("You haven't role");
        }
    }

    @Override
    public OrderHouse findById(Long id) throws Exception {
        try {
            return orderHouseRepository.findById(id).get();
        } catch (Exception e) {
            throw new InvalidRequestException("Order House is not existed");
        }
    }

    @Override
    public void createOrderHouse(OrderHouse orderHouse) throws Exception {
        House house = houseService.findById(orderHouse.getHouse().getId());
        if (house.getStatus() == Status.AVAILABLE) {
            house.setStatus(Status.BOOKED);
            houseService.updateHouse(house);
            orderHouse.setStatus(StatusOrderHouse.BOOKING);
            orderHouseRepository.save(orderHouse);
        } else {
            throw new Exception(" House is not available");
        }
    }

    @Override
    public void updateOrderHouse(OrderHouse orderHouse) {
        Long currentUserId = getCurrentUser().getId();

        House house = houseService.findById(orderHouse.getHouse().getId());
        if (house.getUser().getId().equals(currentUserId)) {
            orderHouseRepository.save(orderHouse);
        }
    }

    @Override
    public void checkIn(Long houseBookingId) {
        try {
            Long currentUserId = getCurrentUser().getId();
            OrderHouse orderHouse = orderHouseRepository.findById(houseBookingId).get();
            House house = houseService.findById(orderHouse.getHouse().getId());
            // check host of house
            if (house.getUser().getId().equals(currentUserId)) {
                house.setStatus(Status.UNAVAILABLE);
                houseService.updateHouse(house);
                orderHouse.setStatus(StatusOrderHouse.CHECKIN);
                orderHouseRepository.save(orderHouse);
            }
        } catch (Exception e) {
            throw new InvalidRequestException("Order House is not existed");
        }
    }

    @Override
    public void checkOut(Long houseBookingId) {
        Long currentUserId = getCurrentUser().getId();
        OrderHouse orderHouse = orderHouseRepository.findById(houseBookingId).get();
        House house = houseService.findById(orderHouse.getHouse().getId());
        if (house.getUser().getId().equals(currentUserId)) {
            house.setStatus(Status.AVAILABLE);
            houseService.updateHouse(house);
            orderHouse.setStatus(StatusOrderHouse.CHECKOUT);
            orderHouseRepository.save(orderHouse);
        }
    }

    @Override
    public void deleteOrderHouse(Long id) {
        Long currentUserId = getCurrentUser().getId();
        OrderHouse orderHouse = orderHouseRepository.findById(id).get();
        House house = houseService.findById(orderHouse.getHouse().getId());
        if (house.getId().equals(currentUserId)) {
            orderHouseRepository.deleteById(id);
        }
    }
}
