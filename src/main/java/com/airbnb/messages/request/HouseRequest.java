package com.airbnb.messages.request;

import com.airbnb.models.*;

import java.util.List;

public class HouseRequest {

    private String houseName;
    private Long category;
    private List<String> picture;
    private String address;
    private Long bedroomNumber;
    private Long bathroomNumber;
    private String description;
    private Long price;
    private Long area;
    private Status status;
//    private Long user;


    public House cloneHouse(Category category, List<ImageOfHouse> picture, User user) {
        House house = new House();
        house.setHouseName(this.houseName);
        house.setCategory(category);
        house.setPicture(picture);
        house.setAddress(this.address);
        house.setBedroomNumber(this.bedroomNumber);
        house.setBathroomNumber(this.bathroomNumber);
        house.setDescription(this.description);
        house.setPrice(this.price);
        house.setArea(this.area);
        house.setStatus(Status.AVAILABLE);
        house.setUser(user);
        return house;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(Long bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public Long getBathroomNumber() {
        return bathroomNumber;
    }

    public void setBathroomNumber(Long bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public Long getUser() {
//        return user;
//    }
//
//    public void setUser(Long user) {
//        this.user = user;
//    }
}
