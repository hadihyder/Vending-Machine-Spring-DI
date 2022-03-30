package com.mthree.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Change {

    private int pennies;
    private int nickels;
    private int dimes;
    private int quarters;


    public Change(int changeDueInCents) {
        quarters = changeDueInCents/25;
        changeDueInCents %= 25;
        dimes = changeDueInCents / 10;
        changeDueInCents %= 10;
        nickels = changeDueInCents / 5;
        changeDueInCents %= 5;
        pennies = changeDueInCents;
    }

    public int getPennies() {
        return pennies;
    }

    public int getNickels() {
        return nickels;
    }

    public int getDimes() {
        return dimes;
    }

    public int getQuarters() {
        return quarters;
    }


}
