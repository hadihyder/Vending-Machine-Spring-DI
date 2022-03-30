package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineAuditDao;
import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Change;
import com.mthree.vendingmachine.dto.Coin;
import com.mthree.vendingmachine.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{

    @Autowired
    @Qualifier("vendingMachineDaoImpl")
    private VendingMachineDao dao;
    @Autowired
    private VendingMachineAuditDao auditDao;

//    @Autowired
//    public VendingMachineServiceLayerImpl(VendingMachineAuditDao auditDao, VendingMachineDao dao) {
//        this.auditDao = auditDao;
//        this.dao = dao;
//    }
    BigDecimal balance = new BigDecimal("0");

    @Override
    public Item getItem(String name, BigDecimal inputMoney) throws NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException {
        Item wantedItem = dao.getItem(name);

        if(wantedItem == null) {
            throw new NoItemInventoryException(
                    "Error there are no " + name + "'s in the vending machine."
            );
        }

        checkIfEnoughMoney(wantedItem, inputMoney);

        removeItemFromInventory(name);

        balance = balance.subtract(wantedItem.getItemCost());

        return wantedItem;
    }

    @Override
    public Map<String, BigDecimal> getItemsInStockWithCosts() throws VendingMachinePersistenceException{
        Map<String, BigDecimal> itemInStockWithCosts = dao.getMapOfItemsInStockWithCosts();

        return itemInStockWithCosts;
    }

    @Override
    public void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws InsufficientFundsException {
        if(item.getItemCost().compareTo(inputMoney) == 1) {
            throw new InsufficientFundsException(
                    "Error insufficient funds"
            );
        }
    }

    @Override
    public void removeItemFromInventory(String name) throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {

        if(dao.getItemInventory(name) > 0) {
            dao.removeItemFromInventory(name);
            auditDao.writeAuditEntry("One " + name + " removed");
        } else {
            throw new NoItemInventoryException(
                    "Error:: " + name + " is out of stock."
            );
        }
    }

    @Override
    public BigDecimal addFunds(BigDecimal funds) {
        balance =  balance.add(funds);
        return balance;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public Map<Coin, Integer> getChangePerCoin() {

        int cents = balance.multiply(new BigDecimal("100")).intValue();
        balance = new BigDecimal("0");
        Change change = new Change(cents);
        Map<Coin,Integer> changeMap = new HashMap<>();
        changeMap.put(Coin.QUARTER, change.getQuarters());
        changeMap.put(Coin.DIME, change.getDimes());
        changeMap.put(Coin.NICKEL, change.getNickels());
        changeMap.put(Coin.PENNY, change.getPennies());

        return changeMap;
    }

}
