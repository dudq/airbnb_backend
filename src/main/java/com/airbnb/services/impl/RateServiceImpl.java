package com.airbnb.services.impl;

import com.airbnb.models.Rate;
import com.airbnb.repositories.RateRepository;
import com.airbnb.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Override
    public List<Rate> findAllByHouseId(Long houseId) {
        return rateRepository.findAllByHouse(houseId);
    }

    @Override
    public void createRate(Rate rate) {
        rateRepository.save(rate);
    }

    @Override
    public boolean existsRateByUserIdAndHouseId(Long userId, Long houseId) {
        return rateRepository.existsRateByUserAndHouse(userId, houseId);
    }

    @Override
    public Rate findByUserIdAndHouseId(Long userId, Long houseId) {
        return rateRepository.findByUserAndHouse(userId, houseId);
    }
}
