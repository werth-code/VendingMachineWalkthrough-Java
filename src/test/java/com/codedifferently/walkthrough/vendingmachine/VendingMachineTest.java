package com.codedifferently.walkthrough.vendingmachine;

import com.codedifferently.walkthrough.vendingmachine.inventory.Product;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class VendingMachineTest {

    private final static Logger logger = Logger.getLogger(VendingMachineTest.class);
    public VendingMachine vendingMachine;

    @Before
    public void setUp() throws IOException {
        vendingMachine = new VendingMachine();
    }

    public static void main(String[] args) throws Exception {
        logger.info("Start");

        VendingMachine vend = new VendingMachine();
        vend.startVending();
    }


    @Test
    public void testSetIDToProducts() throws IOException {
        //GIVEN
        String textFile = "allProducts.txt";
        vendingMachine.setIDtoProducts(textFile);
        //WHEN
        String actual = vendingMachine.getAllProductsByID().get("A4").toString();
        String expected = "Peanut Chews $2.25";
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetInventory() throws IOException {
        //GIVEN
        vendingMachine.setInventory();
        //WHEN
        Integer actual = vendingMachine.getInventory().get("G1");
        Integer expected = 5;
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetInventory() throws IOException {
        vendingMachine.setInventory();
        System.out.println(vendingMachine.getInventory());
        Assert.assertNotNull(vendingMachine.getInventory());
    }

    @Test
    public void checkForProductTrue() {
        String usersProductChoice = "A2";
        Boolean actual = vendingMachine.checkForProduct(usersProductChoice);
        Assert.assertEquals(true, actual);
    }

    @Test
    public void testSetInitialOptions() {
        //GIVEN
        //WHEN
        System.out.println(vendingMachine.setMenuOptions("(1) Display Vending Items", "(2) Purchase", "(3) Exit"));
        String actual = vendingMachine.setMenuOptions("(1) Display Vending Items", "(2) Purchase", "(3) Exit").toString();
        String expected = "[(1) Display Vending Items, (2) Purchase, (3) Exit]";
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetPurchaseOptions() {
        //GIVEN
        //WHEN
        System.out.println(vendingMachine.setMenuOptions("(1) Feed Money", "(2) Add Additional Products", "(3) Exit"));
        String actual = vendingMachine.setMenuOptions("(1) Feed Money", "(2) Add Additional Products", "(3) Exit").toString();
        String expected = "[(1) Feed Money, (2) Add Additional Products, (3) Exit]";
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetAcceptFinalPaymentOptions() {
        //GIVEN
        //WHEN
        System.out.println(vendingMachine.setMenuOptions("Please Add Funds", "Press |   1   |  for $1.", "Press |   5   |  for $5.",  "Press |   10  |  for $10.", "Press |   p   |  To Process Transaction.", "Press |   q   |  To Add Additional Snacks.").toString());
        String actual =    vendingMachine.setMenuOptions("Please Add Funds", "Press |   1   |  for $1.", "Press |   5   |  for $5.",  "Press |   10  |  for $10.", "Press |   p   |  To Process Transaction.", "Press |   q   |  To Add Additional Snacks.").toString();
        String expected = "[Please Add Funds, Press |   1   |  for $1., Press |   5   |  for $5., Press |   10  |  for $10., Press |   p   |  To Process Transaction., Press |   q   |  To Add Additional Snacks.]";
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAcceptFunds() throws Exception {
        //GIVEN
        //WHEN
        Double expected = 5.0;
        Double actual = vendingMachine.acceptFunds(5.0);
        //THEN
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testAcceptFundsFAIL() throws Exception {
        //GIVEN
        //WHEN
        Double actual = vendingMachine.acceptFunds(-  0.1);
        Assert.fail("Expecting IAE Not A Valid Amount!");
    }

    @Test
    public void testEnoughFundingProvided() {
        //GIVEN
        //WHEN
        Double moneyProvided = 1.0;
        Double total = 1.0;
        //THEN
        Assert.assertTrue(vendingMachine.enoughFundingProvided());
    }

    @Test
    public void testReturnChange() { //// TODO: 11/30/20 Strange rounding error in function. Slight hack to make work.
        //GIVEN
        String expected = "Quarters: 10.0\nDimes: 1.0\nNickels: 1.0\n";
        String actual = vendingMachine.returnChange(2.65);
        System.out.println(vendingMachine.returnChange(2.65));
        //Then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testRemoveFromInventory() {
        //GIVEN
        vendingMachine.getInventory();
        vendingMachine.removeFromInventory("A1");
        //WHEN
        Integer actual = vendingMachine.getInventory().get("A1");
        Integer expected = 4;
        //THEN
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testGetAllProductsByID() throws IOException {
        //GIVEN
        vendingMachine.setIDtoProducts("allProducts.txt");
        //WHEN
        String expected = "Sprite $2.5";
        String actual = vendingMachine.getAllProductsByID().get("D4").toString();;
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAllProductsForDisplay() throws IOException {
        //GIVEN
        vendingMachine.setIDtoProducts("allProducts.txt");
        vendingMachine.getAllProductsForDisplay("allProducts");
        //WHEN
        //THEN
        //Console Should Show All Products With ID#
    }

    @Test
    public void testCalculateCartTotal() throws IOException {
        //GIVEN
        vendingMachine.setIDtoProducts("allProducts.txt");
        vendingMachine.addToCart("D2");
        System.out.println(vendingMachine.getAllProductsByID().get("D2"));
        //WHEN
        Double expected = 3.5;
        Double actual = vendingMachine.calculateCartTotal();
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetMenuOptions() {
        //GIVEN
        String actual = vendingMachine.setMenuOptions("Option 1", "Option 2").toString();
        System.out.println(actual);
        //WHEN
        String expected = "[Option 1, Option 2]";
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testStartingMenu() {
        //GIVEN
        vendingMachine.startingMenu();
        //Should Display

        //(1) Display Vending Items
        //(2) Purchase
        //(3) Exit

    }

    @Test
    public void testPurchaseOptionsMenu() {
        //GIVEN
        vendingMachine.purchaseOptionsMenu();
        //Should Display

        //(1) Feed Money
        //(2) Go Back To Add More Snacks

    }

    @Test
    public void testAcceptFundsMenu() {
        //GIVEN
        vendingMachine.acceptFundsMenu();
        //Should Display

        //Please Add Funds
        //Press |   1   |  for $1.
        //Press |   5   |  for $5.
        //Press |   10  |  for $10.
        //Press |   q   |  To Add Additional Snacks.

    }

    @Test
    public void testTestAcceptFunds() throws Exception {
        //GIVEN
        Double fundsFromUser = 10.0;
        vendingMachine.acceptFunds(fundsFromUser);
        //WHEN
        Double expected = 10.0;
        Double actual = vendingMachine.getMoneyProvided();
        System.out.println(actual);
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTestEnoughFundingProvided() {
        //GIVEN
        vendingMachine.getMoneyProvided();
        vendingMachine.setTotal(5.0);
        System.out.println(vendingMachine.enoughFundingProvided());
        //THEN
        Assert.assertFalse(vendingMachine.enoughFundingProvided());
    }

    @Test
    public void testCalculateChange() {
        //GIVEN
        vendingMachine.setMoneyProvided(10.0);
        vendingMachine.setTotal(8.0);
        //WHEN
        Double expected = 2.0;
        Double actual = vendingMachine.calculateChange();
        System.out.println(actual);
        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTransactionLogFile() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testLogFileForEachItemInCart() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testTestReturnChange() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testCheckForProduct() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testAddToCart() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testDisplayMessageAtCheckOut() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testTestDisplayVendingItems() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testStartVending() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testStartPurchase() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void testTestMain() {
        //GIVEN

        //WHEN

        //THEN
    }
}
