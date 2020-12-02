package com.codedifferently.walkthrough.vendingmachine.inventory;

// Importing JUNIT testing - Assert & Test.
import org.junit.Assert;
import org.junit.Test;

// Creating a public testable class named GumTest.
public class GumTest {

    // @Test fixture annotation. Designates this as a test for JUNIT.
    // Here we are testing that the constructor function works in the Gum class by creating a new Gum object.
    // We pass in our expected and actual variables as arguments into the constructor.
    // Then we assert that the values are passed through and our gumRef object contains the correct values.
    // Since we have overwritten our toString() method, it will return our message from the Gum class constructor.
    @Test
    public void constructorTest(){
        // Given
        String name = "Big Red";
        Double price = 0.25;
        Gum gumRef = new Gum(name, price);

        // When
        String expected = "Big Red $0.25";
        String actual = gumRef.toString();
        // Then
        Assert.assertEquals("Constructor test",expected, actual);
    }

//    @Test
//    public void setIndividualProductMenuTest(){
//        //Given
//
//        //When
//        ArrayList<Product> actual = setIndividualProductMenu("allGum.txt");
//        //Then
//        Assert.assertEquals(actual[0], "gobble wobble");
//    }


    // Here we are creating a new object from Gum without passing any arguments into the constructor.
    // This should create a new Gum object using our default values from Gum class.
    // Our message will stay the same, although we could pass in a different msg with this constructor if needed.
    // Again we assert that our given string "Chew Chew, Yum!" equals our message by calling gumRef.toString().

    @Test
    public void messageTest(){
        //Given
        Gum gumRef = new Gum();

        // When
        String expected = "Chew Chew, Yum!";
        String actual = gumRef.message();

        // Then

        Assert.assertEquals(expected, actual);
    }
}
