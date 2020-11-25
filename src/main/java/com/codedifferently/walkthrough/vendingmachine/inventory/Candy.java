package com.codedifferently.walkthrough.vendingmachine.inventory;

public class Candy extends Product {
    public Candy (String nameIn, Double priceIn){
        super(nameIn, priceIn);
        msg = "Munch Munch, Yum!";
    }

    @Override
    public String message() {
        return msg;
    }
}
