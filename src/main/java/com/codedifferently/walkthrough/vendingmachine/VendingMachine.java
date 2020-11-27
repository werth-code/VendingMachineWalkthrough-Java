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
    private Map<String, Integer> inventoryRemaining; //product code, number of items remaining in inventory.
    private Map<String, Double> productPrices;
    private Map<String, Double> cart;
    private Double moneyProvided = 0.00;
    private Menu menu;


    public VendingMachine() throws IOException {
        this.scanner = new Scanner(System.in);
        setInventoryRemaining();
        setProductPrices();
    }

    public void setInventoryRemaining() throws IOException {
        inventoryRemaining = new HashMap<>();
        Stream <String> products = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/productCode.txt"));
        products
            .forEach(productCode -> {
                inventoryRemaining.put(productCode, 5);
            });
        products.close();
    }

    public Map<String, Integer> getInventory() {
        return inventoryRemaining;
    }

    public void setProductPrices() throws IOException {
        productPrices = new HashMap<>();
        Stream <String> products = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/productCodeAndPrice.txt"));
        products
                .forEach(product -> {
                    //split the txt so we have a hash with string, double
                    String[] splitLine = product.split(",");
                    String productCode = splitLine[0];
                    Double productPrice = Double.parseDouble(splitLine[1]);
                    productPrices.put(productCode, productPrice);
                });
        products.close();
    }

    public Boolean checkForProduct(String productID) {
        if(inventoryRemaining.containsKey(productID) && inventoryRemaining.get(productID) > 0) { //if that choice exists and we have the inventory..
            return true;
        }
        return false;
    }

    public ArrayList<String> setInitialOptions() {
        ArrayList<String> initialOptions = new ArrayList<>();
        initialOptions.add("(1) Display Vending Items");
        initialOptions.add("(2) Purchase");
        initialOptions.add("(3) Exit");
        return initialOptions;
    }

    public ArrayList<String> setPurchaseOptions() {
        ArrayList<String> purchaseOptions = new ArrayList<>();
        purchaseOptions.add("(1) Feed Money");
        purchaseOptions.add("(2) Select Product");
        purchaseOptions.add("(3) Finish Transaction");
        return purchaseOptions;
    }

    public String getAllProductsForDisplay(String fileName) throws IOException {
//      ArrayList<String> listOfProducts = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        Stream <String> products = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/" + fileName));
        products
                .forEach(product -> {
//                  listOfProducts.add(product);
                    stringBuilder.append(product);
                    System.out.println(product);
                });
        products.close();
        System.out.println("\n");
        return stringBuilder.toString();
    }


    public void start() throws IOException {
        System.out.println("Welcome To VenDifferently! We Have Dairy Free, GF, Nut Free & Vegan Snacks!");

        boolean flag = true;
        menu = new Menu(setInitialOptions());
        setInventoryRemaining();
        setProductPrices();

        while(flag) {

            for (String option : menu.getOptions()) {
                System.out.println(option);
            }

            String input = scanner.next();

            switch(input) {
                case "1" :
                    getAllProductsForDisplay("allCandy.txt");
                    getAllProductsForDisplay("allChips.txt");
                    getAllProductsForDisplay("allDrinks.txt");
                    getAllProductsForDisplay("allGum.txt");

                    // Instead of creating a new Menu object we are just SOUT each product as we create it.. This may need to be refactored.
                    System.out.println("Choose A Product By Typing ID Number");

                    String usersProductChoice = scanner.next(); //get the users choice
                    Boolean productAvailable = checkForProduct(usersProductChoice); //see if the product is available and if so add to cart...

                    if(productAvailable) { //if it is
                        cart = new HashMap<>();
                        cart.put(usersProductChoice, productPrices.get(usersProductChoice));
                        Integer currentItemNumberRemaining = inventoryRemaining.get(usersProductChoice);
                        inventoryRemaining.put(usersProductChoice, currentItemNumberRemaining -1);

                        System.out.println(usersProductChoice + " Has Been Added To Your Cart!");
                    }
                    else {
                        System.out.println("Sorry, That Product Is No Longer Available!");
                    }

                    break;

                case "2" : // TODO: 11/26/20 THIS IS OUR NEXT ITEM. WE HAVE POTENTIALLY ADDED A PRODUCT TO CART AND NEED TO CHECKOUT.
                    Double total = 0.0;
                    for(Double val : cart.values()) {
                        total += val;
                    }

                    setPurchaseOptions();
                    while (flag) {
                        for (String option : menu.getOptions()) {
                            System.out.println(option);
                        }

                        String checkoutInput = scanner.next();
                    }

                    //ADD FUNDS?
                    //bring up purchasing menu

                case "3" :
                    flag = false;
                    System.out.println("Bye Bye Friend.");
                    break;

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