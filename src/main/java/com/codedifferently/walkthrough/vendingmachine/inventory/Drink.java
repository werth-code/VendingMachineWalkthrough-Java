package com.codedifferently.walkthrough.vendingmachine.inventory;

import java.util.ArrayList;

public class Drink extends Product {
    private Integer quantity;

    public Drink (String nameIn, Double priceIn){
        super(nameIn, priceIn);
        msg = "Glug Glug, Yum!";
        quantity = 5;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    @Override
    public String message() {
        return msg;
    }
}

//Starbucks Cold Brew, 3.50
//LaColumb Almond Milk Mocha, 3.50
//Coke Classic, 2.50
//Sprite, 2.50
//Mountain Dew, 2.50
//Minute Maid Orange Juice, 3.00
//Minute Maid Apple Juice, 3.00
//Tazo Black Tea, 3.00
//Aquifina Bottled Water,  2.00