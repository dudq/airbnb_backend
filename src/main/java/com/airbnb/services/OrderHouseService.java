package com.airbnb.services;

import com.airbnb.models.OrderHouse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderHouseService {
    List<OrderHouse> findAll() throws Exception;

    List<OrderHouse> findByHouse(Long houseId) throws Exception;

    OrderHouse findById(Long id) throws Exception;

    void createOrderHouse(OrderHouse orderHouse) throws Exception;

    void updateOrderHouse(OrderHouse orderHouse);

    void checkIn(Long houseBookingId) throws Exception;

    void checkOut(Long houseBookingId) throws Exception;

    boolean cancelBooking(Long houseBookingId) throws Exception;

    void deleteOrderHouse(Long id);
}
