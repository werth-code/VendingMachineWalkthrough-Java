package com.codedifferently.walkthrough.vendingmachine.inventory;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Product {

    private String id;
    private String name;
    private Double price;
    protected String msg;

    public Product(String id, String name, Double priceIn){
        this.name = name;
        this.price = priceIn;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public Double getPrice(){
        return price;
    }

    @Override
    public String toString(){
        return id + " " + name + " " + price;
    }

    public abstract String message();

    public abstract ArrayList<Product> setIndividualProductMenu() throws IOException;
}
