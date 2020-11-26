package com.codedifferently.walkthrough.vendingmachine.inventory;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CandyTest {

    Candy candy;
    String name = "M&M's";
    Double price = 1.50;

    @Before
    public void setUp() {
        candy = new Candy(name, price);
    }

    @Test
    public void candyConstructorTest(){
        // Given
        // When
        String expected = name + " " + price;
        String actual = candy.toString();
        // Then
        Assert.assertEquals("Constructor test",expected, actual);
    }

    @Test
    public void candyMessageTest(){
        //Given
        // When
        String expected = "Munch Munch, Yum!";
        String actual = candy.message();

        // Then
        Assert.assertEquals(expected, actual);
    }
}

//candy menu

//        Peanut M&M’s, 2.50
//        Reese’s PB Cups, 2.25
//        Hershey Bar Classic, 2.00
//        Peanut Chews, 2.25
//        Skittles Original, 2.25
//        Sour Patch Kids, 2.25
//        EnjoyLife RiceMilk Chocolate Bar, 3.00
//        free2b Sun Cups, 3.00
