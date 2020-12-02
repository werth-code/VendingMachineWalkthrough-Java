package com.codedifferently.walkthrough.vendingmachine.inventory;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DrinkTest {

    Drink drink;
    String id = "A1";
    String name = "Cola";
    Double price = 0.50;

    @Before
    public void setUp() {
        drink = new Drink(name, price);
    }

    @Test
    public void drinkConstructorTest(){
        // Given
        // When
        String expected = name + " $" + price;
        String actual = drink.toString();
        // Then
        Assert.assertEquals("Constructor test",expected, actual);
    }

    @Test
    public void drinkMessageTest(){
        //Given
        // When
        String expected = "Glug Glug, Yum!";
        String actual = drink.message();

        // Then
        Assert.assertEquals(expected, actual);
    }
}