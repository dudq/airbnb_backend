package com.airbnb.controllers;

import com.airbnb.messages.request.HouseRequest;
import com.airbnb.messages.response.HouseInformationOfHost;
import com.airbnb.messages.response.ResponseMessage;
import com.airbnb.models.House;
import com.airbnb.security.sevice.UserPrinciple;
import com.airbnb.services.HostService;
import com.airbnb.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/host")
public class HostController {
    @Autowired
    private HostService hostService;

    @Autowired
    private HouseService houseService;

//    @Autowired
//    private ImageOfHouseService imageOfHouseService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @GetMapping("/houses")
    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> listHouseOfHost() {
        long userId = getCurrentUser().getId();

        List<HouseInformationOfHost> houses = hostService.getHouseListOfHost(userId);

        if (houses.isEmpty()) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, "Not Found Data", null),
                    HttpStatus.OK);
        }

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, "Sucesfully", houses),
                HttpStatus.OK);
    }

    @PostMapping("/houses")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> createHouse(@RequestBody HouseRequest houseRequest) {
//        List<String> urlImages = houseRequest.getPicture();
//        List<ImageOfHouse> pictures = new ArrayList<>();
//        for (String image: urlImages) {
//            ImageOfHouse imageOfHouse = new ImageOfHouse();
//            imageOfHouse.setImageUrl(image);
//            imageOfHouseService.createImageOfHouse(imageOfHouse);
//            pictures.add(imageOfHouse);
//        }

        houseService.createHouseRequest(houseRequest);

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, "Post a new house successfully", null),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/houses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> editHouse(@RequestBody House house, @PathVariable Long id) {
        House currentHouse = this.houseService.findById(id);

        if (currentHouse == null) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, "Data Invalid ", null),
                    HttpStatus.NOT_FOUND
            );
        }

        currentHouse.setHouseName(house.getHouseName());
//        currentHouse.setCategory(house.getCategory());
        currentHouse.setPicture(house.getPicture());
        currentHouse.setAddress(house.getAddress());
        currentHouse.setBedroomNumber(house.getBedroomNumber());
        currentHouse.setBathroomNumber(house.getBathroomNumber());
        currentHouse.setDescription(house.getDescription());
        currentHouse.setPrice(house.getPrice());
        currentHouse.setArea(house.getArea());

        this.houseService.updateHouse(currentHouse);

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, "Update successfully", null),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/houses/{id}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ResponseMessage> deleteHouse(@PathVariable Long id) {
        House house = this.houseService.findById(id);

        long currentUserId = getCurrentUser().getId();
        if (house != null && house.getUser().getId() == currentUserId) {
            this.houseService.deleteHouse(id);
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(true, "Delete the house successfully", null),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, "Fail. Not found", null),
                    HttpStatus.NOT_FOUND);
        }

    }
}
