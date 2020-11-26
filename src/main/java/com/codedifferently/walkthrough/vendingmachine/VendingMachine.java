package com.codedifferently.walkthrough.vendingmachine;

import com.codedifferently.walkthrough.vendingmachine.inventory.Product;
import menu.Menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class VendingMachine {
    private Scanner scanner;
    private Map<String, Product> inventory;
    private Menu menu;


    public VendingMachine() {
        this.scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        System.out.println("Welcome To VenDifferently! We Have Dairy Free, GF, Nut Free & Vegan Snacks!");

        boolean flag = true;

        ArrayList<String> options = new ArrayList<>();

        options.add("(1) Display Vending Items");
        options.add("(2) Purchase");
        options.add("(3) Exit");

        menu = new Menu(options);

        while(flag) {

            for (String option : menu.getOptions()) {
                System.out.println(option);
            }

            String input = scanner.next();

            switch(input) {
                case "1" :

                    System.out.println("Choose A Product"); // Or Type In A Category. Choose: Candy, Chips, Drinks, Gum or 0 For Our Allergy Menu.");


                case "2" :
                    System.out.println("Hey");
                    //bring up purchasing menu

                case "3" :
                    flag = false;
                    System.out.println("Bye Bye");

                default:
                    System.out.println("Try Again");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) throws IOException {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.start();
    }
}


//                    Stream <String> names = Files.lines(Paths.get("/Users/m21/dev/labs/VendingMachineWalkthrough-Java/allProducts.txt"));
//    names
//        .forEach(name -> {
//                    name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
//                    System.out.println(name);
//                });
//    names.close();
//}