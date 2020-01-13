package com.airbnb.services;

import com.airbnb.messages.request.CreateHouseRequest;
import com.airbnb.messages.response.HouseDetail;
import com.airbnb.messages.response.HouseInformation;
import com.airbnb.models.House;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HouseService {

    List<House> findAll();

    List<House> findByUser(Long id);

    House findById(Long id);

    void createHouse(House house);

    void updateHouse(House house);

    void deleteHouse(Long id);

    void createHouseRequest(CreateHouseRequest house);

    HouseDetail getHouseDetailById(Long id);

    List<HouseInformation> getListHouseInformation(int page, int pageSize);

}
