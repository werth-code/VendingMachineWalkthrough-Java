package com.codedifferently.walkthrough.vendingmachine;

import com.codedifferently.walkthrough.vendingmachine.inventory.*;
import menu.Menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

public class VendingMachine {
    private Scanner scanner;
    private Map<String, Integer> inventoryRemaining; //product code, number of items remaining in inventory.
    private Map<String, Product> allProductsByID;
    private Map<String, Double> cart;
    private Double total = 0.00;
    private Double moneyProvided = 0.00;
    private Menu menu;


    public VendingMachine() throws IOException {
        this.scanner = new Scanner(System.in);
        setInventory();
    }

    public void setInventory() throws IOException {
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


    public void setIDtoProducts(String fileName) throws IOException {
        allProductsByID = new HashMap<>();
        Stream <String> products = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/" + fileName));
        products
                .forEach(line -> {
                    String[] split = line.split("\\|");
                    allProductsByID.put(split[0], new Product(split[1], Double.parseDouble(split[2])) {
                        @Override
                        public String message() {
                            if(split[1].charAt(0) == 'A') return "Munch Munch, Yum!";
                            else if(split[1].charAt(0) == 'C') return "Crunch Crunch, Yum!";
                            else if (split[2].charAt(0) == 'D') return "Glug Glug, Yum!";
                            else return "Chew Chew, Yum!";
                        }
                    });
                });
        products.close();
    }

    // TODO: 11/28/20 Get this working. Needs to be sorted... maybe stream it, sort then forEach.
    public void getAllProductsForDisplay(String fileName) throws IOException {
        ArrayList<String> productDisplay = new ArrayList<>();
        allProductsByID.forEach((k,v) -> productDisplay.add(k + " " + v.toString()));
        productDisplay.stream().sorted().forEach(System.out::println);
    }

    // TODO: 11/28/20 This method is for testing. Change this to bring back entire hashMap-uh, not single value.
    public String getIDtoTest(String value) {
        return allProductsByID.get(value).toString();
    }

    //Todo This method will keep adding total everytime you leave and return to checkout. Not Working correctly!
    public Double calculateCartTotal() {
        for(Double val : cart.values()) {
            total += val;
        }
        return total;
    }

    public Boolean checkForProduct(String productID) {
        if(inventoryRemaining.containsKey(productID) && inventoryRemaining.get(productID) > 0) { //if that choice exists and we have the inventory..
            return true;
        }
        return false;
    }

    public ArrayList<String> setMenuOptions(String... options) {
        ArrayList<String> menuOptions = new ArrayList<>();
        for(String option : options) {
            menuOptions.add(option);
        }
        return menuOptions;
    }

    public void purchaseOptionsMenu() {
        Menu purchaseOptionsMenu = new Menu(setMenuOptions("(1) Feed Money", "(2) Add Additional Products", "(3) Exit"));
        for (String option : purchaseOptionsMenu.getOptions()) {
            System.out.println(option);
        }
    }

    public void acceptFundsMenu() {
        Menu acceptFundsMenu = new Menu(setMenuOptions("Please Add Funds", "Press |   1   |  for $1.", "Press |   5   |  for $5.",  "Press |   10  |  for $10.", "Press |   p   |  To Process Transaction.", "Press |   q   |  To Add Additional Snacks."));
        for(String option : acceptFundsMenu.getOptions()) {
            System.out.println(option);
        }
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

    public Double calculateChange() {
        Double change = 0.0;
        if(total < moneyProvided) change = moneyProvided - total;
        return change;
    }

    public String returnChange() {
        String changeString = "";
        Double quarters = 0.0;
        Double dimes = 0.0;
        Double nickels = 0.0;

        if(calculateChange() % .25 == 0) {
            quarters = calculateChange() / 4;
        }
        changeString += "Quarters: " + quarters.toString() + "\n";
        changeString += "Dimes: " + dimes.toString() + "\n";
        changeString += "Nickels: " + nickels.toString() + "\n";

        return changeString;
    }
    //The customer's money is returned using nickels, dimes, and quarters (using the smallest amount of coins possible).

    public void createCart(String usersProductChoice) {
        cart = new HashMap<>();
        cart.put(usersProductChoice, allProductsByID.get(usersProductChoice).getPrice());
    }

    public void removeFromInventory(String usersProductChoice) {
        Integer currentItemNumberRemaining = inventoryRemaining.get(usersProductChoice);
        inventoryRemaining.put(usersProductChoice, currentItemNumberRemaining -1);
    }

    public void displayVendingItems() throws IOException {
        getAllProductsForDisplay("allProducts.txt");

        System.out.println("Type ID Number To Add Product To Your Cart!");
        String usersProductChoice = scanner.next();

        Boolean productAvailable = checkForProduct(usersProductChoice); //see if the product is available and if so add to cart.

        if(productAvailable) {
            createCart(usersProductChoice);
            removeFromInventory(usersProductChoice);

            //Show user what they just added to cart
            System.out.println("\n" + allProductsByID.get(usersProductChoice).toString());
            System.out.println("Has Been Added To Your Cart!\n");
        }
        else {
            System.out.println("Sorry, That Product Is Sold Out Or Not Available!");
        }
    }

    public void start() throws Exception {
        System.out.println("Welcome To VenDifferently! We Have Dairy Free, GF, Nut Free & Vegan Snacks!");

        boolean flag = true;

        menu = new Menu(setMenuOptions("(1) Display Vending Items", "(2) Purchase", "(3) Exit"));

        setInventory();
        setIDtoProducts("allProducts.txt");

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

    // TODO: 11/28/20
    public void startPurchase() throws Exception {

        Double customerTotal = calculateCartTotal();

        System.out.println("Your Total Is $" + customerTotal);
        purchaseOptionsMenu();
        String checkoutInput = scanner.next();

        switch (checkoutInput) {
            case "1" : // (1) Feed Money

                acceptFundsMenu();
                String fundsInput = scanner.next();

                acceptFunds(Double.parseDouble(fundsInput));
                System.out.println(moneyProvided);

                if(fundsInput.equalsIgnoreCase("q")) {
                    total = 0.0;
                    displayVendingItems();
                }
                if(fundsInput.equalsIgnoreCase("p") && enoughFundingProvided()) {
                    System.out.println("...Dispensing Your Snacks!...");

                    // TODO: 11/29/20 function here..
                    if(cart.keySet().stream().anyMatch(a -> a.charAt(0) == 'A')) System.out.println("Munch Munch, Yum!");
                    if(cart.keySet().stream().anyMatch(c -> c.charAt(0) == 'C')) System.out.println("Crunch Crunch, Yum!");
                    if(cart.keySet().stream().anyMatch(d -> d.charAt(0) == 'D')) System.out.println("Glug Glug, Yum!");
                    if(cart.keySet().stream().anyMatch(g -> g.charAt(0) == 'G')) System.out.println("Chew Chew, Yum!");

                    // // TODO: 11/29/20 function here..

                    if(calculateChange() > 0) System.out.println(returnChange());

                    //The machine's current balance should be updated to $0 remaining.
                    total = 0.0;
                }
                    break;

                // TODO: 11/28/20 Set Up Products As Class Items - Finalize Transaction.
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

    public static void main(String[] args) throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.start();
    }
}