package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class VendingMachineDaoImpl implements VendingMachineDao{

    private Map<String, Item> items = new HashMap<>();

    public static final String DELIMITER = "::";
    private final String VENDING_MACHINE_FILE = "C:\\Users\\hyder\\git\\c181-a4-vending-machine-with-spring-di-hadihyder\\VendingMachineSpringDI\\inventory.txt";


//    public VendingMachineDaoImpl() {
//
//        VENDING_MACHINE_FILE = "C:\\Users\\hyder\\git\\c181-a4-vending-machine-with-spring-di-hadihyder\\VendingMachineSpringDI\\inventory.txt";
//    }

//    public VendingMachineDaoImpl(String file) {
//        this.VENDING_MACHINE_FILE = file;
//    }


    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadItem();
        return new ArrayList<>(items.values());
    }

    @Override
    public void removeItemFromInventory(String name) throws VendingMachinePersistenceException {
        loadItem();
        int prevInventory = items.get(name).getInventory();
        items.get(name).setInventory(prevInventory - 1);
        writeItems();
    }

    @Override
    public int getItemInventory(String name) throws VendingMachinePersistenceException {
        loadItem();
        return items.get(name).getInventory();
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        loadItem();
        return items.get(name);
    }

    @Override
    public Map<String, BigDecimal> getMapOfItemsInStockWithCosts() throws VendingMachinePersistenceException {
        loadItem();
        Map<String, BigDecimal> itemsInStockWithCosts = items.entrySet()
                .stream()
                .filter(map -> map.getValue().getInventory() > 0)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue().getItemCost()));
        return itemsInStockWithCosts;
    }

    private String marshallItem(Item anItem) {
        String itemAsText = anItem.getItemName() + DELIMITER;
        itemAsText += anItem.getItemCost() + DELIMITER;
        itemAsText += anItem.getInventory();
        return itemAsText;
    }

    private Item unmarshallItem(String itemAsText) {
        String[] itemTokens = itemAsText.split("::");
        String name = itemTokens[0];
        Item itemFromFile = new Item(name);
        BigDecimal cost = new BigDecimal(itemTokens[1]);
        itemFromFile.setItemCost(cost);
        itemFromFile.setInventory(Integer.parseInt(itemTokens[2]));
        return itemFromFile;
    }

    private void loadItem() throws VendingMachinePersistenceException {
        Scanner sc;

        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_FILE)
                    )
            );
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new VendingMachinePersistenceException(
                    "Error file not found"
            );
        }

        String currLine;
        Item currItem;

        while (sc.hasNextLine()) {
            currLine = sc.nextLine();
            currItem = unmarshallItem(currLine);
            items.put(currItem.getItemName(), currItem);
        }
        sc.close();

    }

    private void writeItems() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(
                    new FileWriter(VENDING_MACHINE_FILE)
            );
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Error, could not save data."
            );
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();

        for(Item currItem: itemList) {
            itemAsText = marshallItem(currItem);
            out.println(itemAsText);

            out.flush();
        }
        out.close();
    }

}
