package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;
import com.mthree.vendingmachine.service.NoItemInventoryException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VendingMachineDao {

    List<Item>  getAllItems() throws VendingMachinePersistenceException;

    void removeItemFromInventory(String name) throws VendingMachinePersistenceException, NoItemInventoryException;

    int getItemInventory(String name) throws VendingMachinePersistenceException;

    Item getItem(String name) throws VendingMachinePersistenceException;

    Map<String, BigDecimal> getMapOfItemsInStockWithCosts() throws VendingMachinePersistenceException;

}
