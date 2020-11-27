package com.codedifferently.walkthrough.vendingmachine.inventory;

import java.util.ArrayList;

public class Drink extends Product {
    public Drink (String id, String nameIn, Double priceIn){
        super(id, nameIn, priceIn);
        msg = "Glug Glug, Yum!";
    }

    public ArrayList<Product> setIndividualProductMenu() {
        return new ArrayList<>();
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