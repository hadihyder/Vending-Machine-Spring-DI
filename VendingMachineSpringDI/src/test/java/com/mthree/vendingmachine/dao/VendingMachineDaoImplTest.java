package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class VendingMachineDaoImplTest {

    VendingMachineDao dao = new VendingMachineDaoImpl();

    @Test
    void testGetAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = dao.getAllItems();

        assertEquals(itemList.size(), 7, " There are 7 items in the menu.");
    }

    @Test
    void testRemoveItemFromInventory() throws VendingMachinePersistenceException, NoItemInventoryException {
        String itemName = "Snickers";

        int inventoryBefore = dao.getItemInventory(itemName);

        dao.removeItemFromInventory(itemName);

        int inventoryAfter = dao.getItemInventory(itemName);

        assertTrue(inventoryAfter < inventoryBefore, "The inventoryAfter should be less than inventoryBefore.");

    }

    @Test
    void testGetItemInventory() throws VendingMachinePersistenceException {
        String itemName = "KitKat";

        int retrievedItem = dao.getItemInventory(itemName);

        assertEquals(retrievedItem, 0, "Kitkat should have 0 quantity.");


    }

    @Test
    void testGetItem() throws VendingMachinePersistenceException {
        Item testRuffles = new Item("Ruffles");
        testRuffles.setItemCost(new BigDecimal("1.90"));
        testRuffles.setInventory(0);

        Item retrievedItem = dao.getItem("Ruffles");

        assertNotNull(retrievedItem, "Item should not be null");
        assertEquals(retrievedItem.getItemName(), testRuffles.getItemName(), "The retrieved item should be Ruffles.");

    }

    @Test
    void testGetMapOfItemsInStockWithCosts() throws VendingMachinePersistenceException {
        Map<String, BigDecimal> itemsInStock = dao.getMapOfItemsInStockWithCosts();

        assertFalse(itemsInStock.containsKey("Kitkat"), "KitKat is out of stock.");
        //There are total 7 items but 1 item is out of stock.
        assertEquals(itemsInStock.size(), 6, "There should be 6 items in menu.");
    }
}