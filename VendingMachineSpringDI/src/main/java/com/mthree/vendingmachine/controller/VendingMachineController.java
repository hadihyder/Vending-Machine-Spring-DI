package com.mthree.vendingmachine.controller;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import com.mthree.vendingmachine.service.VendingMachineServiceLayer;
import com.mthree.vendingmachine.ui.UserIO;
import com.mthree.vendingmachine.ui.UserIOConsoleImpl;
import com.mthree.vendingmachine.ui.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class VendingMachineController {

    private UserIO io = new UserIOConsoleImpl();
    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    @Autowired
    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean keepGoing = true;
        String itemSelection = "";
        BigDecimal inputMoney;
        view.displayMenuBanner();
        try {
            getMenu();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        inputMoney = getMoney();

        while (keepGoing) {
           try {
                itemSelection = getItemSelection();

                if(itemSelection.equalsIgnoreCase("exit")) {
                    displayChange();
                    keepGoing = false;
                    break;
                }

                try {
                    getItem(itemSelection, inputMoney);
                    displayBalance();

                } catch (InsufficientFundsException e) {
                    addFunds(itemSelection, inputMoney);
                    displayBalance();
                }




            } catch (NoItemInventoryException | VendingMachinePersistenceException | InsufficientFundsException e) {
               view.displayErrorMessage(e.getMessage());
               view.displayPleaseTryAgainMsg();
           }
        }
        exitMessage();
    }

    private void displayBalance() {
        BigDecimal bal =  service.getBalance();
        view.displayMachineBalance(bal);
    }

    private void addFunds(String itemSelection, BigDecimal inputMoney) throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        BigDecimal addedFunds = view.displayAddMoreMoney();
        BigDecimal newBal = service.addFunds(addedFunds);
        view.displayAddedFundsSuccess(addedFunds, newBal);
        this.getItem(itemSelection, newBal);

    }

    private void displayChange() {

       view.displayChangeDuePerCoin(service.getChangePerCoin());

    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void getItem(String itemSelection, BigDecimal inputMoney) throws NoItemInventoryException, InsufficientFundsException, VendingMachinePersistenceException {

        Item wantedItem = service.getItem(itemSelection, inputMoney);

        view.displayCompletedBanner(itemSelection);
    }

    private String getItemSelection() {
       return view.getItemSelection();
    }

    private BigDecimal getMoney() {
        BigDecimal funds = view.getMoney();
        service.addFunds(funds);
        return funds;
    }

    private void getMenu() throws VendingMachinePersistenceException {
        Map<String, BigDecimal> itemsInStockWithCosts = service.getItemsInStockWithCosts();
        view.displayMenu(itemsInStockWithCosts);
    }


}
