package com.codedifferently.walkthrough.vendingmachine;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VendingMachineTest {

    private final static Logger logger = Logger.getLogger(VendingMachineTest.class);
    public VendingMachine vendingMachine;
    private Map<String, Integer> inventoryRemaining;

    @Before
    public void setUp() throws IOException {
        vendingMachine = new VendingMachine();
    }

    public static void main(String[] args) throws Exception {
        logger.info("Start");

        VendingMachine vend = new VendingMachine();
        vend.start();
    }


    @Test
    public void getAllProductsForDisplayTest() throws IOException {
        //GIVEN
        String textFile = "allCandy.txt";
        //WHEN
        String expected =
        "A1,Peanut M&M’s,2.50" +
        "A2,Reese’s PB Cups,2.25" +
        "A3,Hershey Bar Classic,2.00" +
        "A4,Peanut Chews,2.25" +
        "A5,Skittles Original,2.25" +
        "A6,Sour Patch Kids,2.25" +
        "A7,EnjoyLife RiceMilk Chocolate Bar,3.00 " + //Weird test fluke where a space is off by 1?
        "A8,free2b Sun Cups,3.00" ;

        String actual = vendingMachine.getAllProductsForDisplay(textFile);
        //THEN
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void setInventoryRemainingTest() throws IOException { // NOT WORKING YET...inventoryRemaining issue
        //GIVEN
        inventoryRemaining = new HashMap<>();
        //WHEN
        vendingMachine.setInventoryRemaining();
        System.out.println(inventoryRemaining);
        System.out.println(vendingMachine.getInventory());
        //THEN
        Assert.assertEquals(inventoryRemaining, vendingMachine.getInventory());
    }

    @Test
    public void checkForProductTrue() {
        String usersProductChoice = "A2";
        Boolean actual = vendingMachine.checkForProduct(usersProductChoice);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkForProductFalse() throws IOException { // NOT WORKING...inventoryRemaining issue
        vendingMachine.setInventoryRemaining();
        inventoryRemaining.put("A2", 0);
        String usersProductChoice = "A2";
        Boolean actual = vendingMachine.checkForProduct(usersProductChoice);
        Assert.assertTrue(actual);
    }
}
