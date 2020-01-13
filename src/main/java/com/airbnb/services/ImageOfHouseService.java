package com.airbnb.services;

import com.airbnb.models.ImageOfHouse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageOfHouseService {
    List<ImageOfHouse> findAll();

    List<ImageOfHouse> findByHouseId(Long id);

    ImageOfHouse findById(Long id);

    void createImageOfHouse(ImageOfHouse imageOfHouse);

    void updateImageOfHouse(ImageOfHouse imageOfHouse);

    void deleteImageOfHouse(Long id);
}
