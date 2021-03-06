package com.airbnb.services.impl;

import com.airbnb.exceptions.InvalidRequestException;
import com.airbnb.models.*;
import com.airbnb.repositories.OrderHouseRepository;
import com.airbnb.security.sevice.UserPrinciple;
import com.airbnb.services.HouseService;
import com.airbnb.services.OrderHouseService;
import com.airbnb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public void checkIn(Long houseBookingId) throws Exception {
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
            throw new Exception("You don't have role");
        }
    }

    @Override
    public void checkOut(Long houseBookingId) {
        try {
            Long currentUserId = getCurrentUser().getId();
            OrderHouse orderHouse = orderHouseRepository.findById(houseBookingId).get();
            House house = houseService.findById(orderHouse.getHouse().getId());
            // check host of house
            if (house.getUser().getId().equals(currentUserId)) {
                house.setStatus(Status.AVAILABLE);
                houseService.updateHouse(house);
                orderHouse.setStatus(StatusOrderHouse.CHECKOUT);
                orderHouseRepository.save(orderHouse);
            }
        } catch (Exception e) {
            throw new InvalidRequestException("You don't have role");
        }
    }

    @Override
    public boolean cancelBooking(Long houseBookingId) throws Exception {
        try {
            Long currentUserId = getCurrentUser().getId();
            OrderHouse orderHouse = orderHouseRepository.findById(houseBookingId).get();
            House house = houseService.findById(orderHouse.getHouse().getId());
            Date now = new Date();
            boolean isExpired = (now.getTime() - orderHouse.getDateCheckIn().getTime()) < 1000 * 60 * 60 * 24;
            // check user order of house
            if (orderHouse.getUser().getId().equals(currentUserId)
                    && orderHouse.getStatus() == StatusOrderHouse.BOOKING
                    && !isExpired) {
                house.setStatus(Status.AVAILABLE);
                houseService.updateHouse(house);
                orderHouse.setStatus(StatusOrderHouse.CANCEL);
                orderHouseRepository.save(orderHouse);
                return true;
            }
        } catch (Exception e) {
            throw new Exception("You don't have role");
        }
        return false;
    }

    @Override
    public List<OrderHouse> findAllByHost(Long hostId) throws Exception {
        Long currentUserId = getCurrentUser().getId();

        if (currentUserId.equals(hostId)) {
            return orderHouseRepository.findByHouse_User_Id(hostId);
        } else {
            throw new Exception("You don't have role");
        }
    }

    @Override
    public List<OrderHouse> findAllByUser(Long userId) throws Exception {
        Long currentUserId = getCurrentUser().getId();

        if (currentUserId.equals(userId)) {
            User currentUser = userService.findById(currentUserId);
            return orderHouseRepository.findByUser(currentUser);
        } else {
            throw new Exception("You don't have role");
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
