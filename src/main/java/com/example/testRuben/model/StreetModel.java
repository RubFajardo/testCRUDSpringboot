package com.example.testRuben.model;

import jakarta.persistence.*;

@Entity
@Table(name = "streets")
public class StreetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;

    public StreetModel() {}

    public StreetModel(String street, String city) {
        this.street = street;
        this.city = city;
    }

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    // Getters y setters
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }
}
