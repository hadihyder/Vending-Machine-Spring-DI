package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.*;
import com.mthree.vendingmachine.dto.Coin;
import com.mthree.vendingmachine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class VendingMachineServiceLayerImplTest {

    VendingMachineServiceLayerImpl testService;



    public VendingMachineServiceLayerImplTest(){



      ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
      testService = ctx.getBean("serviceLayer", VendingMachineServiceLayerImpl.class);

//
            }
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetItem() throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {

        Item testItem = new Item("Pepsi");
        testItem.setItemCost(new BigDecimal("2.40"));
        testItem.setInventory(5);

        BigDecimal money = new BigDecimal("2.50");

        Item itemWanted = null;

        try {
            itemWanted = testService.getItem("KitKat", money);
            assertNull(itemWanted, "Kitkat is out of stock");
        }catch (NoItemInventoryException e){

        }



        itemWanted = testService.getItem("Pepsi", money);

        assertEquals("Pepsi", itemWanted.getItemName(), "Item should be Ruffles.");


    }

    @Test
    void testGetItemsInStockWithCosts() throws VendingMachinePersistenceException {
        Map<String, BigDecimal> itemList = testService.getItemsInStockWithCosts();

        assertFalse(itemList.containsKey("KitKat"), "Kitkat is out of stock.");
        assertEquals(itemList.size(), 1, "There should be 1 item in the menu.");
    }

    @Test
    void testCheckIfEnoughMoney() throws InsufficientFundsException {
        Item itemWanted = new Item("Pepsi");
        itemWanted.setItemCost(new BigDecimal("2.40"));
        itemWanted.setInventory(5);
        BigDecimal validMoney = new BigDecimal("3");
        BigDecimal inValidMoney = new BigDecimal("1");
        try {
            testService.checkIfEnoughMoney(itemWanted, validMoney);
        } catch (InsufficientFundsException e){
            fail("There is sufficient funds.");
        }

        try {
            testService.checkIfEnoughMoney(itemWanted, inValidMoney);
            fail("Insufficient funds");
        }catch (InsufficientFundsException e) {

        }


    }

    @Test
    void testRemoveItemFromInventory() throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {

        try {
            testService.removeItemFromInventory("Pepsi");
        } catch (NoItemInventoryException e) {
            fail("Pepsi is in stock, should not fail");
        }
        try{
            testService.removeItemFromInventory("KitKat");
            fail("KitKat out of stock");
        } catch (NoItemInventoryException e) {
    }
    }

    @Test
    void testGetChangePerCoin() throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {

//

        BigDecimal money = new BigDecimal("3");

        testService.addFunds(money);

        testService.getItem("Pepsi", money);

        Map<Coin, Integer> change = testService.getChangePerCoin();
        System.out.println(change.get(Coin.QUARTER));
        assertEquals(change.get(Coin.QUARTER), 2, "Should be 2");
        assertEquals(change.get(Coin.DIME), 1);
        assertEquals(change.get(Coin.NICKEL), 0);
        assertEquals(change.get(Coin.PENNY), 0);

    }
}