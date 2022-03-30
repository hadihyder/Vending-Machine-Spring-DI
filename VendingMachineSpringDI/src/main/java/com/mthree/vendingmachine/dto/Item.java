package com.mthree.vendingmachine.dto;

import java.math.BigDecimal;

public class Item {

    private String itemName;
    private BigDecimal itemCost;
    private int inventory;

    public Item(String name){
        this.itemName = name;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }


    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getInventory() {
        return inventory;
    }
}
