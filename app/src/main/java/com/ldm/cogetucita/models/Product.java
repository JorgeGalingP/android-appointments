package com.ldm.cogetucita.models;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private float price;
    private String image;

    public Product() {
    }

    public Product(Integer id,
                   String name,
                   String description,
                   float price,
                   String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Has seleccionado para registrar el producto " + id +
                " (" + name + ")" +
                " con un precio de " + price + "euros.";
    }
}
