package com.airbnb.controllers;

import com.airbnb.messages.response.ResponseMessage;
import com.airbnb.models.OrderHouse;
import com.airbnb.services.OrderHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order-houses")
public class OrderHouseController {
    @Autowired
    private OrderHouseService orderHouseService;

    @GetMapping
    public ResponseEntity<ResponseMessage> getListOrderHouse() {
        try {
            List<OrderHouse> orderHouses = orderHouseService.findAll();
            return new ResponseEntity<>(new ResponseMessage(true, "OK", orderHouses), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/house/{id}")
    public ResponseEntity<ResponseMessage> getListBookingByHouse(@PathVariable("id") Long id) {
        try {
            List<OrderHouse> orderHouses = orderHouseService.findByHouse(id);
            return new ResponseEntity<>(new ResponseMessage(true, "OK", orderHouses), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/host/{id}")
    public ResponseEntity<ResponseMessage> getListBookingByHost(@PathVariable("id") Long id) {
        try {
            List<OrderHouse> orderHouses = orderHouseService.findAllByHost(id);
            return new ResponseEntity<>(new ResponseMessage(true, "OK", orderHouses), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<ResponseMessage> getListBookingByUser(@PathVariable("id") Long id) {
        try {
            List<OrderHouse> orderHouses = orderHouseService.findAllByUser(id);
            return new ResponseEntity<>(new ResponseMessage(true, "OK", orderHouses), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseMessage> getOrderHouse(@PathVariable("id") Long id) {
        try {
            OrderHouse orderHouse = orderHouseService.findById(id);
            return new ResponseEntity<>(new ResponseMessage(true, "OK", orderHouse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createOrderHouse(@RequestBody OrderHouse orderHouse) {
        try {
            orderHouseService.createOrderHouse(orderHouse);
            return new ResponseEntity<>(new ResponseMessage(true, "Booking successfully", orderHouse), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateOrderHouse(@RequestBody OrderHouse orderHouse) {
        try {
            orderHouseService.updateOrderHouse(orderHouse);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/checkin/{id}")
    public ResponseEntity<ResponseMessage> checkIn(@PathVariable("id") Long id) {
        try {
            orderHouseService.checkIn(id);
            return new ResponseEntity<>(new ResponseMessage(true, "OK", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/checkout/{id}")
    public ResponseEntity<ResponseMessage> checkOut(@PathVariable("id") Long id) {
        try {
            orderHouseService.checkOut(id);
            return new ResponseEntity<>(new ResponseMessage(true, "OK", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ResponseMessage> cancelBooking(@PathVariable("id") Long id) {
        try {
            if (orderHouseService.cancelBooking(id)) {
                return new ResponseEntity<>(new ResponseMessage(true, "OK", null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage(false, "Expired to cancel", null), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderHouse(@PathVariable(value = "id") Long id) {
        try {
            orderHouseService.deleteOrderHouse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
