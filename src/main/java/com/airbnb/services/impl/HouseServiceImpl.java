package com.airbnb.services.impl;

import com.airbnb.messages.response.HouseDetail;
import com.airbnb.messages.response.HouseInformation;
import com.airbnb.models.House;
import com.airbnb.repositories.HouseDao;
import com.airbnb.repositories.HouseRepository;
import com.airbnb.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseDao houseDao;

    @Override
    public List<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public List<House> findByUser(Long id) {
        return houseRepository.findByUser(id);
    }

    @Override
    public House findById(Long id) {
        return houseRepository.findById(id).get();
    }

    @Override
    public void createHouse(House house) {
        houseRepository.save(house);
    }

    @Override
    public void updateHouse(House house) {
        houseRepository.save(house);
    }

    @Override
    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
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