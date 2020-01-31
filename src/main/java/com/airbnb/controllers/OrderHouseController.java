package com.airbnb.controllers;

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
    public ResponseEntity<List<OrderHouse>> getListOrderHouse() {
        List<OrderHouse> orderHouses = orderHouseService.findAll();
        if (orderHouses.isEmpty()) {
            return new ResponseEntity<List<OrderHouse>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderHouses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderHouse> getOrderHouse(@PathVariable("id") Long id) {
        try {
            OrderHouse orderHouse = orderHouseService.findById(id);
            return new ResponseEntity<OrderHouse>(orderHouse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createOrderHouse(@RequestBody OrderHouse orderHouse) {
        try {
            orderHouseService.createOrderHouse(orderHouse);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
