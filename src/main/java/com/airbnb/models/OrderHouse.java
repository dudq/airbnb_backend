package com.airbnb.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
public class OrderHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank
//    @Column(unique = true)
//    private String name;

    @NotEmpty
    private Date dateCheckOut;
    @NotEmpty
    private Date dateCheckIn;
    @NotEmpty
    private Integer guest;

    @Enumerated(EnumType.STRING)
    private StatusOrderHouse status;

    @ManyToOne(targetEntity = House.class)
    private House house;
    @ManyToOne(targetEntity = User.class)
    private User user;


    public OrderHouse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public Date getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(Date dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public Integer getGuest() {
        return guest;
    }

    public void setGuest(Integer guest) {
        this.guest = guest;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusOrderHouse getStatus() {
        return status;
    }

    public void setStatus(StatusOrderHouse status) {
        this.status = status;
    }
}