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

//Herr’s BBQ, 2.00
//EnjoyLife Lentil Chips, 3.00
//Mozaics Organic Veggie Chips, 3.00
//Lance Gluten Free Crackers, 3.00
//Cheetos Original, 2.25
//Snyders Gluten Free Pretzels, 2.50
//Snyders Original Pretzels, 2.25
//Kettle Original Potato Chips,  3.00
//GoldFish Original, 2.25