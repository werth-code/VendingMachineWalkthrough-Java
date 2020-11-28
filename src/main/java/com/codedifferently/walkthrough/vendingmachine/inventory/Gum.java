package com.codedifferently.walkthrough.vendingmachine.inventory;


public class Gum extends Product{

    public Gum(String nameIn, Double priceIn){
        super(nameIn, priceIn);
        msg = "Chew Chew, Yum!";
    }

    public Gum(){
        this("Rhino Chew", 0.0);
    }

    @Override
    public String message() {
        return msg;
    }
}


// This is our public class Gum and it Extends from the abstract class of Product.
// We made it this way so that we could create multiple products with sharable features like name, price and a message.
// This allows us to abstract some of our code so that all of our products, even in the future will follow our blueprint.

// This is our constructor function which has name & price parameters. This calls on our super or parent Product class implementation.
// When we create a new Gum(name, price) object - the name and price arguments are assigned to our name and price variables in the super.

// msg is passed to our Product super class's msg variable. NOTE - this is different for each product and sends in a unique message.


// This is a default constructor function which can be called by creating a new Gum() and not passing in any arguments.
// The arguments will be set to the default values or Rhino Chew and 0.0.

// Here we are overriding an existing method. This changes the behavior of the method to return our msg variable when called.
// This is an example of polymorphism.

//Trident Mint, 2.50
//Trident Cinnamon, 2.50
//Ice Breakers Sour Lemon, 2.50
//Ice Breakers Bubble Gum, 2.50
//Dentyne Spearmint Tub, 4.50
//Dentyne Classic Mint Tub, 4.50
//Juicy Fruit Classic, 3.00
//TicTac Orange, 2.00
//TicTac Mint,  2.00