package com.airbnb.services.impl;

import com.airbnb.messages.response.HouseInformationOfHost;
import com.airbnb.repositories.HouseDao;
import com.airbnb.services.HostService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HostServiceImpl implements HostService {

    @Autowired
    private HouseDao houseDao;


    @Override
    public List<HouseInformationOfHost> getHouseListOfHouse(Long userId) {
        return houseDao.getListHouseInformationOfHost(userId);
    }
}
