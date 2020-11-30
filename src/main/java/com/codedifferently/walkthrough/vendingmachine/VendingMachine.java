package com.codedifferently.walkthrough.vendingmachine;

import com.codedifferently.walkthrough.vendingmachine.inventory.*;
import menu.Menu;

import java.io.File;
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
    private ArrayList<Product> cart = new ArrayList<>();
    private Double total = 0.00;
    private Double customerTotal;
    private Double moneyProvided = 0.00;
    private String receiptLineToWrite = "";


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
                            if(split[0].charAt(0) == 'A') return "Munch Munch, Yum!";
                            else if(split[0].charAt(0) == 'C') return "Crunch Crunch, Yum!";
                            else if (split[0].charAt(0) == 'D') return "Glug Glug, Yum!";
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

    // TODO: 11/30/20 These Should All Be One Method...
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
        Menu acceptFundsMenu = new Menu(setMenuOptions("Please Add Funds", "Press |   1   |  for $1.", "Press |   5   |  for $5.",  "Press |   10  |  for $10.", "Press |   q   |  To Add Additional Snacks."));
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

    public void transactionLogFile(String typeOfTransaction, Double amountBefore, Double amountAfter) throws IOException {
        File file = new File("log.txt");
        FileWriter writer = new FileWriter(file);
        Date date = new Date();
        receiptLineToWrite += ">" + date + " " + typeOfTransaction + ": $" + amountBefore + " $" + amountAfter + "\n";

        writer.write(receiptLineToWrite);
        writer.flush();
        writer.close();
    }

    public void logFileForEachItemInCart() throws IOException {
        Double tally = 0.0;
        for (Product prod : cart) {
            tally += prod.getPrice();
            transactionLogFile(prod.getName(), prod.getPrice(), (moneyProvided - tally));
        }
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

    public Boolean checkForProduct(String productID) { //inventoryRemaining
        if(inventoryRemaining.containsKey(productID) && inventoryRemaining.get(productID) > 0) { //if that choice exists and we have the inventory..
            return true;
        }
        return false;
    }

    public void addToCart(String usersProductChoice) {
        cart.add(allProductsByID.get(usersProductChoice));
    }

    public void removeFromInventory(String usersProductChoice) {
        Integer remainingStock = inventoryRemaining.get(usersProductChoice);
        inventoryRemaining.put(usersProductChoice, remainingStock - 1);
    }
    public void displayMessageAtCheckOut() {
        cart.forEach(ele-> System.out.println(ele.message()));
    }

    public void displayVendingItems() throws Exception {
        getAllProductsForDisplay("allProducts.txt");

        System.out.println("Type ID Number To Add Product To Your Cart!");
        System.out.println("Or Press |  q  | To Go Back");

        String usersProductChoice = scanner.next();
        if (usersProductChoice.equalsIgnoreCase("q") || usersProductChoice.equals(3)) {
            startVending();
        }

        Boolean productAvailable = checkForProduct(usersProductChoice);

        if(productAvailable) {
            addToCart(usersProductChoice);
            removeFromInventory(usersProductChoice);

            System.out.println("\n" + allProductsByID.get(usersProductChoice).toString());
            System.out.println("Has Been Added To Your Cart!\n");
        }
        else {
            System.out.println("Sorry, That Product Is Sold Out Or Not Available!");
        }
    }

    public void startVending() throws Exception {
        System.out.println("\nWelcome To VenDifferently! We Have Dairy Free, GF, Nut Free & Vegan Snacks!");

        boolean flag = true;
        setInventory();
        setIDtoProducts("allProducts.txt");

        while(flag) {

            startingMenu();
            String input = scanner.next();
            if (input.equalsIgnoreCase("q") || input.equals(3)) {
                System.out.println("Bye Bye Friend.");
                flag = false;
                break;
            }

            switch(input) {

                case "1" :
                    displayVendingItems();
                    break;

                case "2" : // (2) Purchase
                    if(cart.isEmpty()) {
                        System.out.println("Please Add A Product First!");
                        continue;
                    }
                    startPurchase();
                    continue;
                case "3" : // (3) Exit
                default:
                    System.out.println("Bye Bye Friend.");
                    flag = false;
                    break;
            }
        }
        scanner.close();
    }

    // TODO: 11/28/20
    public void startPurchase() throws Exception {
        System.out.println("Cart: " + cart.toString());

        Boolean innerFlag = true;

        purchaseOptionsMenu();
        String checkoutInput = scanner.next();

        customerTotal = calculateCartTotal();

        while(innerFlag) {

            switch (checkoutInput) {
                case "1": // (1) Feed Money

                    System.out.println("Your Total Is $" + customerTotal);
                    System.out.println("Cart: " + cart.toString() + "\n");
                    acceptFundsMenu();

                    String fundsInput = scanner.next();

                    if(fundsInput.equalsIgnoreCase("q")) {
                        if(moneyProvided > 0.0) {
                            Double prevMoneyProvided = moneyProvided;
                            moneyProvided = 0.0;
                            transactionLogFile("Refund", prevMoneyProvided, moneyProvided);
                        }
                        total = 0.0;
                        innerFlag = false;
                        break;
                    }
                    else acceptFunds(Double.parseDouble(fundsInput));

                    System.out.println("You Have Added $" + moneyProvided);

                    transactionLogFile("Payment", total, moneyProvided);

                    if (enoughFundingProvided()) {
                        System.out.println("...Dispensing Your Snacks!...");

                        displayMessageAtCheckOut(); // TODO: 11/30/20 Only Displaying Chew Chew, Yum!

                        Double change = calculateChange();
                        System.out.println("\nYour Change Is $" + change);
                        System.out.println(returnChange(change));

                        logFileForEachItemInCart();

                        transactionLogFile("Refund", change, moneyProvided);

                        total = 0.0;
                        moneyProvided = 0.0;
                        cart.clear();
                        innerFlag = false;
                        break;
                    }
                    else continue;

                default:
                    startVending();
                    continue;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.startVending();
    }
}