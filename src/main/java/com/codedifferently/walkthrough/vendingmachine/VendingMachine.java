package com.codedifferently.walkthrough.vendingmachine;

import com.codedifferently.walkthrough.vendingmachine.inventory.*;
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
    private Map<String, Product> allProductsByID;
    private Map<String, Double> productPrices;
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

    // TODO: 11/28/20 This method needs to be removed and taken out of start.. 
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
        allProductsByID.forEach((k,v) -> System.out.println(k + " " + v.toString()));
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

    public void createCart(String usersProductChoice) {
        cart = new HashMap<>();
        cart.put(usersProductChoice, productPrices.get(usersProductChoice));
    }

    public void removeFromInventory(String usersProductChoice) {
        Integer currentItemNumberRemaining = inventoryRemaining.get(usersProductChoice);
        inventoryRemaining.put(usersProductChoice, currentItemNumberRemaining -1);
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

                    if(fundsInput.equalsIgnoreCase("q")) displayVendingItems();
                    if(fundsInput.equalsIgnoreCase("p") && enoughFundingProvided()) // finishTransaction();
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

    public void displayVendingItems() throws IOException {
        getAllProductsForDisplay("allProducts.txt");

        System.out.println("Type ID Number To Add Product To Your Cart!");
        String usersProductChoice = scanner.next();

        Boolean productAvailable = checkForProduct(usersProductChoice); //see if the product is available and if so add to cart.

        if(productAvailable) {
            createCart(usersProductChoice);
            removeFromInventory(usersProductChoice);

            //Show user what they just added to cart
            System.out.println(allProductsByID.get(usersProductChoice).toString());
            System.out.println("Has Been Added To Your Cart!");
        }
        else {
            System.out.println("Sorry, That Product Is Sold Out Or Not Available!");
        }
    }

    // TODO: 11/28/20 Need to implement finalize transaction and receipt. 
    public void start() throws Exception {
        System.out.println("Welcome To VenDifferently! We Have Dairy Free, GF, Nut Free & Vegan Snacks!");

        boolean flag = true;
        menu = new Menu(setMenuOptions("(1) Display Vending Items", "(2) Purchase", "(3) Exit"));
        setInventory();
        setProductPrices();
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

    public static void main(String[] args) throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.start();
    }
}