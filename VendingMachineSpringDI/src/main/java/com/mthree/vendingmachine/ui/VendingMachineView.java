package com.mthree.vendingmachine.ui;

import com.mthree.vendingmachine.dto.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class VendingMachineView {

    private UserIO io;

    @Autowired
    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayMenuBanner() {
        io.print("==== Vending Machine Menu ===");
    }

    public void displayMenu(Map<String, BigDecimal> itemsInStickWithCosts) {
        itemsInStickWithCosts.entrySet()
                .forEach(e -> System.out.println(e.getKey() + ": $" + e.getValue()));
    }

    public BigDecimal getMoney() {
        return io.readBigDecimal("Please enter the amount money before selection in dollars($)");
    }

    public String getItemSelection(){
        return io.readString("Please select an item from the menu or enter 'exit' to quit");
    }

    public void displayCompletedBanner(String name) {

        io.print("Enjoy your " + name + "!");
    }

    public void displayInsufficientFunds(BigDecimal money) {
        io.print("Insufficient funds, you only have $" + money);
    }

    public void displayItemOutOStock(String name) {
        io.print("Sorry! " + name + " is out of stock.");
    }

    public void displayChangeDuePerCoin(Map<Coin, Integer> change) {
        io.print("Change Vended...");
        io.print("Q:" + change.get(Coin.QUARTER) + " D:" + change.get(Coin.DIME)
                + " N:" + change.get(Coin.NICKEL) + " P:" + change.get(Coin.PENNY));
    }

    public void displayExitBanner() {
        io.print("Good Bye!");
    }

    public void displayUnknownCommandBanner(){
        io.print("Unknown Command!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== Error ===");
        io.print(errorMsg);
    }

    public void displayPleaseTryAgainMsg() {
        io.print("Please select something else.");
    }

    public BigDecimal displayAddMoreMoney() {
        return io.readBigDecimal("Insufficient amount, add more money:: ");
    }
    public void displayAddedFundsSuccess(BigDecimal funds, BigDecimal balance) {
        io.print("Added funds:: $" + funds + "\nNew Balance:: $" +balance);

    }

    public void displayMachineBalance(BigDecimal bal) {
        io.print("Balance:: $" +bal);
    }
}
