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
    private Double total = 0.00;
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
                    String[] splitLine = product.split(","); // split on pipe "\\|"
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
        purchaseOptions.add("(2) Add Additional Products");
        purchaseOptions.add("(3) Exit");
        return purchaseOptions;
    }

    public ArrayList<String> setAcceptFinalPaymentOptions() {
        ArrayList<String> finalPurchaseOptions = new ArrayList<>();
        finalPurchaseOptions.add("Please Add Funds. Press 1 for $1.00  |  5 for $5.00  |  10 for $10.00");
        finalPurchaseOptions.add("Press  |   p   | To Process Transaction.");
        finalPurchaseOptions.add("Press |   q   | To Add Additional Snacks");
        return finalPurchaseOptions;
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

    public Double calculateCartTotal() {
        for(Double val : cart.values()) {
            total += val;
        }
        return total;
    }

    public Double acceptFunds(Double fundsFromUser) throws Exception {
        if(fundsFromUser <= 0 || fundsFromUser.isNaN()) throw new IllegalArgumentException("Not A Valid Amount. Incoming Funds Must Be Greater Than 0.");
        moneyProvided += fundsFromUser;
        return moneyProvided;
    }

    public Boolean enoughFundingProvided() {
        if (moneyProvided >= total) return true;
        return false;
    }

    // TODO: 11/28/20
    public void startPurchase() throws Exception {

        Double customerTotal = calculateCartTotal();
        System.out.println("Your Total Is $" + customerTotal);

        for (String option : setPurchaseOptions()) {
            System.out.println(option);
        }

        String checkoutInput = scanner.next();
// TODO: 11/28/20 create a menu object for 3 sout
            switch (checkoutInput) {
                case "1" : // (1) Feed Money

                    Menu purchaseMenu = new Menu(setAcceptFinalPaymentOptions());
                    for(String option : purchaseMenu.getOptions()) {
                        System.out.println(option);
                    }

                    String fundsInput = scanner.next();

                    acceptFunds(Double.parseDouble(fundsInput));
                    System.out.println(moneyProvided);

                    if(fundsInput.equalsIgnoreCase("q")) displayVendingItems();
                    if(fundsInput.equalsIgnoreCase("p") && enoughFundingProvided()) // finishTransaction();
                    break;

                    //Dispensing an item prints the item name, cost, and the money remaining. Dispensing also returns a message:
                    //All chip items print "Crunch Crunch, Yum!"
                    //All candy items print "Munch Munch, Yum!"
                    //All drink items print "Glug Glug, Yum!"
                    //All gum items print "Chew Chew, Yum!"

                case "2" : // (2) Add Additional Products
                    displayVendingItems();
                default:
                    System.out.println("Try Again");

        }
    }

    public void displayVendingItems() throws IOException {
        getAllProductsForDisplay("allCandy.txt");
        getAllProductsForDisplay("allChips.txt");
        getAllProductsForDisplay("allDrinks.txt");
        getAllProductsForDisplay("allGum.txt");

        // Instead of creating a new Menu object we are just SOUT each product as we create it.. This may need to be refactored.
        System.out.println("Type ID Number To Add Product To Your Cart!");

        String usersProductChoice = scanner.next();
        Boolean productAvailable = checkForProduct(usersProductChoice); //see if the product is available and if so add to cart.
//new func
        if(productAvailable) {
            cart = new HashMap<>();
            cart.put(usersProductChoice, productPrices.get(usersProductChoice));
            Integer currentItemNumberRemaining = inventoryRemaining.get(usersProductChoice);
            inventoryRemaining.put(usersProductChoice, currentItemNumberRemaining -1);
//user prod choice should show product not just "G1" code
            System.out.println(usersProductChoice + " Has Been Added To Your Cart!");
        }
        else {
            System.out.println("Sorry, That Product Is Sold Out Or Not Available!");
        }
    }


    public void start() throws Exception {
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

            switch(input) { // We press 1 and want to see all products

                case "1" : // (1) Display Vending Items
                    displayVendingItems();
                    break;
                case "2" : // (2) Purchase
                    startPurchase();
                    break;
                case "3" : // (3) Exit
                    flag = false;
                    System.out.println("Bye Bye Friend.");
                    break;
                default:
                    System.out.println("Try Again");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.start();
    }
}