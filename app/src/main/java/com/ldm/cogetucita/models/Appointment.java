package com.ldm.cogetucita.models;

public class Appointment {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String date;
    private String location;
    private State state;
    private Product product;

    public Appointment() {
    }

    public Appointment(int id,
                       String name,
                       String surname,
                       String email,
                       String date,
                       String location,
                       Product product) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.date = date;
        this.location = location;
        this.state = State.PENDING;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
