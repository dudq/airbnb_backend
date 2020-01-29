package com.airbnb.controllers;

import com.airbnb.messages.request.HouseRequest;
import com.airbnb.messages.response.HouseInformation;
import com.airbnb.messages.response.HouseInformationOfHost;
import com.airbnb.messages.response.ResponseMessage;
import com.airbnb.models.House;
import com.airbnb.security.sevice.UserPrinciple;
import com.airbnb.services.HouseService;
import com.airbnb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<House>> getListHouse() {
        List<House> houses = houseService.findAll();
        if (houses.isEmpty()) {
            return new ResponseEntity<List<House>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<House> getHouse(@PathVariable("id") Long id) {
        try {
            House house = houseService.findById(id);
            return new ResponseEntity<House>(house, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<Void> createHouse(@RequestBody HouseRequest house) {
        try {
            houseService.createHouse(house);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateHouse(@RequestBody House house) {
        try {
            houseService.updateHouse(house);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<Void> deleteHouse(@PathVariable(value = "id") Long id) {
        try {
            houseService.deleteHouse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<House>> findHouse(@RequestParam String houseName) {
        List<House> houses = houseService.findByHouseName(houseName);
        if (houses.isEmpty()) {
            return new ResponseEntity<List<House>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houses, HttpStatus.OK);

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<House>> getListHouseByCategory(@PathVariable Long id) {
        try {
            List<House> houses = houseService.findByCategoryId(id);
            return new ResponseEntity<List<House>>(houses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<List<HouseInformationOfHost>> getListHouseByHost(@PathVariable Long id) {
        try {
            List<HouseInformationOfHost> houses = houseService.getHouseListOfHost(id);
            return new ResponseEntity<List<HouseInformationOfHost>>(houses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseMessage> listAllHouse(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<HouseInformation> houses = this.houseService.getListHouseInformation(1, 2);

        if (houses.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, "Not Found Data", null), HttpStatus.OK
            );
        }

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, "Successfully. Get list all house", houses), HttpStatus.OK
        );
    }

}
