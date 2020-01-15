package com.airbnb.services.impl;

import com.airbnb.exceptions.InvalidRequestException;
import com.airbnb.messages.request.HouseRequest;
import com.airbnb.messages.response.HouseDetail;
import com.airbnb.messages.response.HouseInformation;
import com.airbnb.models.House;
import com.airbnb.repositories.HouseDao;
import com.airbnb.repositories.HouseRepository;
import com.airbnb.security.sevice.UserPrinciple;
import com.airbnb.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseDao houseDao;

    private UserPrinciple getCurrentUser() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public List<House> findByHostId(Long id) {
        return houseRepository.findByUser(id);
    }

    @Override
    public List<House> findByCategoryId(Long id) {
        return houseRepository.findByCategory(id);
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
    public void createHouse(House house) {
        Long currentUserId = getCurrentUser().getId();
        if (house.getUser().equals(currentUserId)) {
            houseRepository.save(house);
        } else {
            throw new InvalidRequestException("Current user is not valid");
        }
    }

    @Override
    public void updateHouse(House house) {
        House houseExisted = houseRepository.findById(house.getId()).get();
        if (houseExisted == null) {
            throw new InvalidRequestException("House is not existed");
        }

        Long currentUserId = getCurrentUser().getId();
        if (house.getUser().equals(currentUserId)) {
            houseRepository.save(house);
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
        House house = houseRequest.cloneHouse();
        houseDao.insert(house);
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
