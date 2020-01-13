package com.airbnb.services;

import com.airbnb.messages.response.HouseInformationOfHost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HostService {
    List<HouseInformationOfHost> getHouseListOfHost(Long userId);
}
