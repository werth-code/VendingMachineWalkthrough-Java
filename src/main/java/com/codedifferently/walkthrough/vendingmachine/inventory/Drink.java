package com.codedifferently.walkthrough.vendingmachine.inventory;

public class Drink extends Product {
    public Drink (String nameIn, Double priceIn){
        super(nameIn, priceIn);
        msg = "Glug Glug, Yum!";
    }

    @Override
    public String message() {
        return msg;
    }
}
