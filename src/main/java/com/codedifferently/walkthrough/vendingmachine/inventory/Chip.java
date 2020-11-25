package com.codedifferently.walkthrough.vendingmachine.inventory;

public class Chip extends Product {
    public Chip(String nameIn, Double priceIn){
        super(nameIn, priceIn);
        msg = "Crunch Crunch, Yum!";
    }

    @Override
    public String message() {
        return msg;
    }
}
