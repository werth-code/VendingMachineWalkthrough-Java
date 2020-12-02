package com.codedifferently.walkthrough.vendingmachine.inventory;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChipTest {

    Chip chip;
    String name = "Bugles";
    Double price = 2.50;

    @Before
    public void setUp() {
        chip = new Chip(name, price);
    }

    @Test
    public void drinkConstructorTest(){
        // Given
        // When
        String expected = name + " $" + price;
        String actual = chip.toString();
        // Then
        Assert.assertEquals("Constructor test",expected, actual);
    }

    @Test
    public void drinkMessageTest(){
        //Given
        // When
        String expected = "Crunch Crunch, Yum!";
        String actual = chip.message();

        // Then
        Assert.assertEquals(expected, actual);
    }
}