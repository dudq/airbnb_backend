package com.airbnb.services.impl;

import com.airbnb.models.ImageOfHouse;
import com.airbnb.repositories.ImageOfHouseRepository;
import com.airbnb.services.ImageOfHouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImageOfHouseServiceImpl implements ImageOfHouseService {

    @Autowired
    private ImageOfHouseRepository imageOfHouseRepository;

    @Override
    public List<ImageOfHouse> findAll() {
        return imageOfHouseRepository.findAll();
    }

    @Override
    public List<ImageOfHouse> findByHouseId(Long id) {
        return imageOfHouseRepository.findByHouse(id);
    }

    @Override
    public ImageOfHouse findById(Long id) {
        return imageOfHouseRepository.findById(id).get();
    }

    @Override
    public void createImageOfHouse(ImageOfHouse imageOfHouse) {
        imageOfHouseRepository.save(imageOfHouse);
    }

    @Override
    public void updateImageOfHouse(ImageOfHouse imageOfHouse) {
        imageOfHouseRepository.save(imageOfHouse);
    }

    @Override
    public void deleteImageOfHouse(Long id) {
        imageOfHouseRepository.deleteById(id);
    }
}
