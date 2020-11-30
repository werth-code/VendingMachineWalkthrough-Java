package com.codedifferently.walkthrough.vendingmachine.inventory;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Product {

    private String name;
    private Double price;
    private Integer quantity = 5;
    protected String msg;

    public Product(String name, Double priceIn){
        this.name = name;
        this.price = priceIn;
    }

    public Product(double parseDouble) {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public Double getPrice(){
        return price;
    }

    @Override
    public String toString(){
        return name + " $" + price;
    }

    public abstract String message();

    //public abstract ArrayList<Product> setIndividualProductMenu(String fileName) throws IOException;
}
