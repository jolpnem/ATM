package ru.test.ATM;

import java.util.HashSet;
import java.util.Set;

class ATMDepartment {
    private Set<ATM> atmSet;
    private Set<ATM> initialAtmSet;

    public ATMDepartment() {
        initialAtmSet = new HashSet<>();
        atmSet = new HashSet<>();
    }

    public void addATM(ATM atm) {
        initialAtmSet.add(atm.clone());
        atmSet.add(atm);
    }

    public int calculateCashBalance() {
        int cashBalance = 0;

        for (ATM atm : atmSet)
            cashBalance += atm.getBalance();

        return cashBalance;
    }

    public void restoreATMsStates() {
        atmSet.clear();
        atmSet.addAll(initialAtmSet);
    }
}
