package ru.test.ATM;

import java.util.HashSet;
import java.util.Set;

class ATMDepartment {
    private Set<ATM> ATMs;
    private Set<ATM> ATMsWithInitialStates;
    //TODO: refactor ATMsWithInitialStates with using Memento pattern

    ATMDepartment() {
        ATMsWithInitialStates = new HashSet<>();
        ATMs = new HashSet<>();
    }

    void addATM(ATM atm) {
        ATMsWithInitialStates.add(atm.clone());
        ATMs.add(atm);
    }

    int calculateCashBalance() {
        int cashBalance = 0;

        for (ATM atm : ATMs)
            cashBalance += atm.getBalance();

        return cashBalance;
    }

    void restoreATMsStates() {
        ATMs.clear();
        ATMs.addAll(ATMsWithInitialStates);
    }
}
