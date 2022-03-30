package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VendingMachineDaoStubImpl implements VendingMachineDao{

    public Item onlyItem;
    public BigDecimal money = new BigDecimal(0);


    public VendingMachineDaoStubImpl(){
        onlyItem = new Item("Pepsi");
        onlyItem.setItemCost(new BigDecimal("2.40"));
        onlyItem.setInventory(5);
    }

    public VendingMachineDaoStubImpl(Item testItem) {
        this.onlyItem = testItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public void removeItemFromInventory(String name) throws NoItemInventoryException, VendingMachinePersistenceException {

            if (!name.equals(onlyItem.getItemName())) {
                throw new NoItemInventoryException( name + " not in inventory");
           }

    }

    @Override
    public int getItemInventory(String name) throws VendingMachinePersistenceException {
        if(name.equals(onlyItem.getItemName())) {
            return onlyItem.getInventory();
        } else {
            return 0;
        }
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        if(name.equals(onlyItem.getItemName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, BigDecimal> getMapOfItemsInStockWithCosts() throws VendingMachinePersistenceException {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put(onlyItem.getItemName(), onlyItem.getItemCost());
        return map;
    }
}
