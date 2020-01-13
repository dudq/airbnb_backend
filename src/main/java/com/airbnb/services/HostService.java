package com.airbnb.services;

import com.airbnb.messages.response.HouseInformationOfHost;

import java.util.List;

public interface HostService {
    List<HouseInformationOfHost> getHouseListOfHouse(Long userId);
}
