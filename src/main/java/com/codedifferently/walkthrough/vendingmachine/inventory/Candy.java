package com.codedifferently.walkthrough.vendingmachine.inventory;

import java.util.ArrayList;

public class Candy extends Product {
    public Candy (String id, String nameIn, Double priceIn){
        super(id, nameIn, priceIn);
        msg = "Munch Munch, Yum!";
    }

    public ArrayList<Product> setIndividualProductMenu() {
        return new ArrayList<>();
    }

    @Override
    public String message() {
        return msg;
    }
}

//Peanut M&M’s, 2.50
//Reese’s PB Cups, 2.25
//Hershey Bar Classic, 2.00
//Peanut Chews, 2.25
//Skittles Original, 2.25
//Sour Patch Kids, 2.25
//EnjoyLife RiceMilk Chocolate Bar, 3.00
//free2b Sun Cups, 3.00