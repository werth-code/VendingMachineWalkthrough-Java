package com.codedifferently.walkthrough.vendingmachine;

import com.codedifferently.walkthrough.vendingmachine.inventory.*;
import menu.Menu;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class VendingMachine {
    private Scanner scanner;
    private Map<String, Integer> inventoryRemaining; //product code, number of items remaining in inventory.
    private Map<String, Product> allProductsByID;
    private ArrayList<Product> cart;
    private Double total = 0.00;
    private Double customerTotal;
    private Double moneyProvided = 0.00;


    public VendingMachine() throws IOException {
        this.scanner = new Scanner(System.in);
        setInventory();
    }

    public Double getTotal() {
        return total;
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

    public void getAllProductsForDisplay(String fileName) throws IOException {
        ArrayList<String> productDisplay = new ArrayList<>();
        allProductsByID.forEach((k,v) -> productDisplay.add(k + " " + v.toString()));
        productDisplay.stream().sorted().forEach(System.out::println);
    }

    // TODO: 11/28/20 This method is for testing. Change this to bring back entire hashMap-uh, not single value.
    public String getIDtoTest(String value) {
        return allProductsByID.get(value).toString();
    }

    public Double calculateCartTotal() {
        for(Product item : cart) {
            total += item.getPrice();
        }
        return total;
    }

    public ArrayList<String> setMenuOptions(String... options) {
        ArrayList<String> menuOptions = new ArrayList<>();
        for(String option : options) {
            menuOptions.add(option);
        }
        return menuOptions;
    }

    public void startingMenu() {
        Menu startingMenu = new Menu(setMenuOptions("(1) Display Vending Items", "(2) Purchase", "(3) Exit"));
        for (String option : startingMenu.getOptions()) {
            System.out.println(option);
        }
    }

    public void purchaseOptionsMenu() {
        Menu purchaseOptionsMenu = new Menu(setMenuOptions("(1) Feed Money", "(2) Go Back To Add More Snacks"));
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
        if(moneyProvided > total) change = moneyProvided - total;
        return change;
    }

    //// TODO: 11/30/20 Get This Working! Not writing to file.
    public void transactionLogFile(String typeOfTransaction, Double amountBefore, Double amountAfter) throws IOException {
        FileWriter fileWriter = new FileWriter("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/log.txt");
        Date date = new Date();
        String lineToWrite = ">" + date + " " + typeOfTransaction + ": $" + amountBefore + " $" + amountAfter;
        fileWriter.write(lineToWrite);

        System.out.println(lineToWrite);
    }

    public String returnChange(Double change) {
        Double amount = change;
        String changeString = "";

        Double quarters = 0.0, dimes = 0.0, nickels = 0.0;

        while(amount > 0.0) {
            if(amount - 1.00 > 0) {
                amount -= 1.00;
                quarters += 4;
            }
            else if(amount - 0.25 >= 0) {
                amount -= 0.25;
                quarters += 1;
            }
            else if(amount - .10 >= 0) {
                amount -= .10;
                dimes += 1;
            }
            else if (amount - .05 >= 0) {
                amount -= .05;
                nickels += 1;
            }
            else break;
        }

        changeString += "Quarters: " + quarters.toString() + "\n" + "Dimes: " + dimes.toString() + "\n" + "Nickels: " + nickels.toString() + "\n";
        return changeString;
    }

    //TODO We need to increment the count of items - if they add multiples of the same item!
    public void createCart() {
        cart = new ArrayList<>();
    }

    public Boolean checkForProduct(String productID) {
        if(allProductsByID.containsKey(productID) && allProductsByID.get(productID).getQuantity() > 0) { //if that choice exists and we have the inventory..
            return true;
        }
        return false;
    }

    public void addToCart(String usersProductChoice) {
        cart.add(allProductsByID.get(usersProductChoice));
    }

    public void removeFromInventory(String usersProductChoice) {
        Integer remainingStock = allProductsByID.get(usersProductChoice).getQuantity();
        allProductsByID.get(usersProductChoice).setQuantity(remainingStock - 1);
    }


    public void dispenseVendingItems() {

    }

    public void displayVendingItems() throws IOException {
        getAllProductsForDisplay("allProducts.txt");
        // TODO: 11/30/20
        createCart();

        System.out.println("Type ID Number To Add Product To Your Cart!");
        String usersProductChoice = scanner.next();

        Boolean productAvailable = checkForProduct(usersProductChoice); //see if the product is available and if so add to cart.

        if(productAvailable) {
            addToCart(usersProductChoice);
            // TODO: 11/30/20 ADD TO CART!
            removeFromInventory(usersProductChoice);

            //Show user what they just added to cart
            System.out.println("\n" + allProductsByID.get(usersProductChoice).toString());
            System.out.println("Has Been Added To Your Cart!\n");

            customerTotal = calculateCartTotal();
            System.out.println("Your Total Is $" + customerTotal);
        }
        else {
            System.out.println("Sorry, That Product Is Sold Out Or Not Available!");
        }
    }

    public void startVending() throws Exception {
        System.out.println("Welcome To VenDifferently! We Have Dairy Free, GF, Nut Free & Vegan Snacks!");

        boolean flag = true;

        setInventory();
        setIDtoProducts("allProducts.txt");

        while(flag) {

            startingMenu();
            String input = scanner.next();

            switch(input) { // We press 1 and want to see all products

                case "1" : // (1) Display Vending Items
                    //total = 0.0;
                    displayVendingItems();
                    break;

                case "2" : // (2) Purchase
                    if(total == 0) {
                        System.out.println("Please Add A Product First!");
                        continue;
                    }
                    startPurchase();
                    continue;
                case "3" : // (3) Exit
                    System.out.println("Bye Bye Friend.");
                    flag = false;
                    break;

                default:
                    //startPurchase();
                    flag = false;
                    break;
            }
        }
        scanner.close();
    }

    // TODO: 11/28/20
    public void startPurchase() throws Exception {
        System.out.println("Your Total Is $" + customerTotal);

        Boolean innerFlag = true;

        purchaseOptionsMenu();
        String checkoutInput = scanner.next();

        while(innerFlag) {

            switch (checkoutInput) {
                case "1": // (1) Feed Money

                    acceptFundsMenu();
                    String fundsInput = scanner.next();

                    acceptFunds(Double.parseDouble(fundsInput));
                    System.out.println("You Have Added $" + moneyProvided);

                    transactionLogFile("Payment", 0.0, moneyProvided);

                    if (enoughFundingProvided()) {
                        System.out.println("...Dispensing Your Snacks!...");

                        //// TODO: 11/30/20 Get actual products from our hashMap and return their message.
                        // Should know exactly which items are being dispensed and return Object.message(), not sout.
//                        if (cart.keySet().stream().anyMatch(a -> a.charAt(0) == 'A'))
//                            System.out.println("Munch Munch, Yum!");
//                        if (cart.keySet().stream().anyMatch(c -> c.charAt(0) == 'C'))
//                            System.out.println("Crunch Crunch, Yum!");
//                        if (cart.keySet().stream().anyMatch(d -> d.charAt(0) == 'D'))
//                            System.out.println("Glug Glug, Yum!");
//                        if (cart.keySet().stream().anyMatch(g -> g.charAt(0) == 'G'))
//                            System.out.println("Chew Chew, Yum!");

                        Double change = calculateChange();
                        System.out.println(change);
                        System.out.println(returnChange(change));

                        transactionLogFile("Payment", 0.0, moneyProvided);

                        total = 0.0;
                        moneyProvided = 0.0;
                        innerFlag = false;
                        break;
                    }
                    else continue;

                case "2":
                    innerFlag = false;
                    continue;

                default:
                    innerFlag = false;
                    break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.startVending();
    }
}