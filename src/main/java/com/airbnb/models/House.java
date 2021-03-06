package com.airbnb.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String houseName;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    //    @Lob
    @OneToMany(targetEntity = ImageOfHouse.class)
    private List<ImageOfHouse> picture;

//    @OneToMany(targetEntity = OrderHouse.class)
//    @JsonManagedReference
//    private List<OrderHouse> orderHouses;

    private String address;
    private Long bedroomNumber;
    private Long bathroomNumber;
    private Long area;

    @Column(columnDefinition = "long")
    private String description;
    private Long price;
    private Long rate;

    @Enumerated(EnumType.STRING)
    private Status status;

    //    @Column(name = "host_id")
    @ManyToOne
    @JoinColumn(name = "host_id")
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ImageOfHouse> getPicture() {
        return picture;
    }

    public void setPicture(List<ImageOfHouse> picture) {
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

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
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

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
