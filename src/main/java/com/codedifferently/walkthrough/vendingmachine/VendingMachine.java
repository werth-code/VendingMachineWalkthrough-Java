package com.codedifferently.walkthrough.vendingmachine;

import menu.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class VendingMachine {
    private Scanner scanner;

    public VendingMachine() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome To This Thing!");
        boolean flag = true;
        ArrayList<String> options = new ArrayList<>();
        options.add("(1) Display Vending Items");
        options.add("(2) Purchase");
        options.add("(3) Exit");
        Menu menu = new Menu(options);

        while(flag) {

            for (String option : menu.getOptions()) {
                System.out.println(option);
            }
            String input = scanner.next();

            if(input.equals("3")) {
                flag = false;
                System.out.println("Bye Bye");
            } else {
                System.out.println("Try Again");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.start();
    }
}
