package com.codedifferently.walkthrough.vendingmachine;

import org.apache.log4j.Logger;

import java.io.IOException;

public class VendingMachineTest {
    private final static Logger logger = Logger.getLogger(VendingMachineTest.class);

    public static void main(String[] args) throws IOException {
        logger.info("Start");

        VendingMachine vend = new VendingMachine();
        vend.start();
    }
}
