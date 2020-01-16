package com.airbnb.services;

import com.airbnb.messages.request.HouseRequest;
import com.airbnb.messages.response.HouseDetail;
import com.airbnb.messages.response.HouseInformation;
import com.airbnb.models.House;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HouseService {

    List<House> findAll();

    List<House> findByHostId(Long id);

    List<House> findByCategoryId(Long id);

    List<House> findByHouseName(String houseName);

    House findById(Long id);

    void createHouse(HouseRequest houseRequest);

    void updateHouse(House house);

    void deleteHouse(Long id);

    void createHouseRequest(HouseRequest house);

    HouseDetail getHouseDetailById(Long id);

    List<HouseInformation> getListHouseInformation(int page, int pageSize);

}
