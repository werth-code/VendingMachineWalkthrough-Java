package com.codedifferently.walkthrough.vendingmachine;

import menu.Menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class VendingMachine {
    private Scanner scanner;
    private Map<String, Integer> inventoryRemaining;
    private Map<String, Integer> productPrices;
    private Map<String, Integer> cart;
    private Menu menu;


    public VendingMachine() throws IOException {
        this.scanner = new Scanner(System.in);
        setInventoryRemaining();
        setProductPrices();
    }

    public void setInventoryRemaining() throws IOException {
        inventoryRemaining = new HashMap<>();
        Stream <String> products = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/productCodeNumberOfItems.txt"));
        products
        .forEach(productCode -> {
            inventoryRemaining.put(productCode, 5);
        });
        products.close();
    }

    public void setProductPrices() throws IOException {
        productPrices = new HashMap<>();
        Stream <String> products = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/productCodeAndPrice.txt"));
        products
                .forEach(productCode -> {
                    // comma separated values with File.lines??
                     productPrices.put(productCode, 5);
                });
        products.close();
    }

    public Boolean checkForProduct(String productID) {
        if(inventoryRemaining.containsKey(productID) && inventoryRemaining.get(productID) > 0) { //if that choice exists and we have the inventory..
            return true;
        }
        return false;
    }


    public void start() throws IOException {
        System.out.println("Welcome To VenDifferently! We Have Dairy Free, GF, Nut Free & Vegan Snacks!");

        boolean flag = true;

        ArrayList<String> initialOptions = new ArrayList<>();
        initialOptions.add("(1) Display Vending Items");
        initialOptions.add("(2) Purchase");
        initialOptions.add("(3) Exit");

        menu = new Menu(initialOptions);

        while(flag) {

            for (String option : menu.getOptions()) {
                System.out.println(option);
            }

            String input = scanner.next();

            switch(input) {
                case "1" :
                    ArrayList<String> listOfProducts = new ArrayList<>();
                    Stream <String> products = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/productCodeAndPrice.txt"));
                    products
                            .forEach(product -> {
                                listOfProducts.add(product);
                                System.out.println(product);
                            });
                    products.close();
                    // Instead of creating a new Menu object we are just SOUT each product as we create it.. This may need to be refactored.
                    System.out.println("Choose A Product By Typing ID Number");

                    String usersProductChoice = scanner.next(); //get the users choice
                    Boolean productAvailable = checkForProduct(usersProductChoice); //see if the product is available and if so add to cart...

                    if(productAvailable) { //if it is
                        cart.put(usersProductChoice, 1);
                        System.out.println(usersProductChoice + " Has Been Added To Your Cart!");
                    }
                    else {
                        listOfProducts.remove(usersProductChoice);
                        System.out.println("Sorry, That Product Is No Longer Available!");
                    }
                    break;

                case "2" :
                    System.out.println("Hey");
                    //bring up purchasing menu

                case "3" :
                    flag = false;
                    System.out.println("Bye Bye Friend.");

                default:
                    System.out.println("Try Again");
            }
            // TODO: 11/26/20
            // if user enters a correct id#
            // check for available funds?
            // fetch that product from our inventory HashMap.
        }
        scanner.close();
    }

    public static void main(String[] args) throws IOException {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.start();
    }
}


//    Stream <String> products = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/allProducts.txt"));
//    products
//        .forEach(product -> {
//                    System.out.println(product);
//                });
//    products.close();
//