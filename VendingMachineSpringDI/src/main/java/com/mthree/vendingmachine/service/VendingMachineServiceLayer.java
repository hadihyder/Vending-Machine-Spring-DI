package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Coin;
import com.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.Map;

public interface VendingMachineServiceLayer {

    Item getItem(String name, BigDecimal inputMoney) throws NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException;

    Map<String, BigDecimal> getItemsInStockWithCosts() throws VendingMachinePersistenceException;

    void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws InsufficientFundsException;

    void removeItemFromInventory(String name) throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException;

    Map<Coin, Integer> getChangePerCoin();

    BigDecimal addFunds(BigDecimal inputMoney);

    BigDecimal getBalance();
}
