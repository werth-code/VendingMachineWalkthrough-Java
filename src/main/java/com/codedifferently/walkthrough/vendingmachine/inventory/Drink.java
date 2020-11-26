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

//Starbucks Cold Brew, 3.50
//LaColumb Almond Milk Mocha, 3.50
//Coke Classic, 2.50
//Sprite, 2.50
//Mountain Dew, 2.50
//Minute Maid Orange Juice, 3.00
//Minute Maid Apple Juice, 3.00
//Tazo Black Tea, 3.00
//Aquifina Bottled Water,  2.00