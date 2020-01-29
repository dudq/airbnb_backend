package com.airbnb.services.impl;

import com.airbnb.exceptions.InvalidRequestException;
import com.airbnb.messages.request.HouseRequest;
import com.airbnb.messages.response.HouseDetail;
import com.airbnb.messages.response.HouseInformation;
import com.airbnb.messages.response.HouseInformationOfHost;
import com.airbnb.models.Category;
import com.airbnb.models.House;
import com.airbnb.models.ImageOfHouse;
import com.airbnb.models.User;
import com.airbnb.repositories.HouseDao;
import com.airbnb.repositories.HouseRepository;
import com.airbnb.security.sevice.UserPrinciple;
import com.airbnb.services.CategoryService;
import com.airbnb.services.HouseService;
import com.airbnb.services.ImageOfHouseService;
import com.airbnb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageOfHouseService imageOfHouseService;

    @Autowired
    private UserService userService;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public List<House> findByHostId(Long id) {
        User user = userService.findById(id);
        return houseRepository.findByUser(user);
    }

    @Override
    public List<HouseInformationOfHost> getHouseListOfHost(Long userId) {
        return houseDao.getListHouseInformationOfHost(userId);
    }


    @Override
    public List<House> findByCategoryId(Long id) {
        Category category = categoryService.findById(id);
        return houseRepository.findByCategory(category);
    }


    @Override
    public List<House> findByHouseName(String houseName) {
        return houseRepository.findByHouseName(houseName);
    }


    @Override
    public House findById(Long id) {
        House house = houseRepository.findById(id).get();
        if (house == null) {
            throw new InvalidRequestException("House is not existed");
        }
        return house;
    }

    @Override
    public void createHouse(HouseRequest houseRequest) {
        Long currentUserId = getCurrentUser().getId();
        User user = userService.findById(currentUserId);
        Category category = categoryService.findById(houseRequest.getCategory());
        List<ImageOfHouse> imageOfHouses = new ArrayList<>();
        for (String picture : houseRequest.getPicture()) {
            ImageOfHouse imageOfHouse = new ImageOfHouse(picture);
            imageOfHouseService.createImageOfHouse(imageOfHouse);
            imageOfHouses.add(imageOfHouse);
        }
        House house = houseRequest.cloneHouse(category, imageOfHouses, user);
        houseRepository.save(house);
    }

    @Override
    public void updateHouse(House house) {
        House houseUpdated = houseRepository.findById(house.getId()).get();
        if (houseUpdated == null) {
            throw new InvalidRequestException("House is not existed");
        }

        Long currentUserId = getCurrentUser().getId();
        if (house.getUser().equals(currentUserId)) {

            List<ImageOfHouse> imageOfHouses = new ArrayList<>();
            for (ImageOfHouse picture : house.getPicture()) {
                ImageOfHouse imageOfHouse = new ImageOfHouse(picture.getUrl());
                imageOfHouseService.createImageOfHouse(imageOfHouse);
                imageOfHouses.add(imageOfHouse);
            }
            house.setPicture(imageOfHouses);
            houseUpdated.setHouseName(house.getHouseName());
            houseUpdated.setCategory(house.getCategory());
            houseUpdated.setPicture(house.getPicture());
            houseUpdated.setAddress(house.getAddress());
            houseUpdated.setBedroomNumber(house.getBedroomNumber());
            houseUpdated.setBathroomNumber(house.getBathroomNumber());
            houseUpdated.setArea(house.getArea());
            houseUpdated.setDescription(house.getDescription());
            houseUpdated.setPrice(house.getPrice());
            houseRepository.save(houseUpdated);
        } else {
            throw new InvalidRequestException("Current user is not valid");
        }
    }

    @Override
    public void deleteHouse(Long id) {
        House house = houseRepository.findById(id).get();
        if (house == null) {
            throw new InvalidRequestException("House is not existed");
        }

        Long currentUserId = getCurrentUser().getId();
        if (house.getUser().equals(currentUserId)) {
            houseRepository.delete(house);
        } else {
            throw new InvalidRequestException("Current user is not valid");
        }
    }

    @Override
    public void createHouseRequest(HouseRequest houseRequest) {
//        House house = houseRequest.cloneHouse();
//        houseDao.insert(house);
    }

    @Override
    public HouseDetail getHouseDetailById(Long id) {
        return houseDao.getHouseDetailById(id);
    }

    @Override
    public List<HouseInformation> getListHouseInformation(int page, int pageSize) {
        return houseDao.getListHouse(page, pageSize);
    }
}
