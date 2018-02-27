package ru.test.ATM;

import ru.test.ATM.exceptions.IllegalATMStateException;
import ru.test.ATM.exceptions.InsufficientFundsException;
import ru.test.ATM.exceptions.TooDetailedAmountException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ATM {
    private Map<Integer, ATMBanknoteCell> banknoteCells = new HashMap<>();

    public void deposit(Banknote banknote) {
        deposit(banknote, 1);
    }

    public void deposit(Banknote banknote, int count) {
        if (!banknoteCells.containsKey(banknote.getValue()))
            banknoteCells.put(banknote.getValue(), new ATMBanknoteCell(banknote));

        banknoteCells.get(banknote.getValue()).add(count);
    }

    public Map<Banknote, Integer> withdraw(int amount) {
        checkBalance(amount);
        checkAmountCanBeGiven(amount);

        if (banknoteCells.containsKey(amount) && !banknoteCells.get(amount).isEmpty()) {
            banknoteCells.get(amount).subtract(1);
            return Map.of(Banknote.byValue(amount), 1);
        }

        return issueFunds(amount);
    }

    public int getBalance() {
        return banknoteCells.values().stream().mapToInt(ATMBanknoteCell::getBalance).sum();
    }

    private Map<Banknote, Integer> issueFunds(int amount) {
        Map<Banknote, Integer> result = new HashMap<>();

        int noWithdrawn = Stream.of(banknoteCells.keySet().toArray()).mapToInt(banknote -> (int) banknote)
                .reduce(amount, (remainingAmountToWithdraw, banknote) -> {
                    if (remainingAmountToWithdraw == 0) return 0;

                    int banknotesToWithdraw = remainingAmountToWithdraw / banknote;
                    int withdrawnBanknotes = banknoteCells.get(banknote).subtractHowManyIsPresent(banknotesToWithdraw);

                    result.putAll(Map.of(Banknote.byValue(banknote), withdrawnBanknotes));

                    return remainingAmountToWithdraw - banknote * withdrawnBanknotes;
        });

        if (noWithdrawn > 0) {
            rollback(result);
            throw new IllegalATMStateException("Amount " + amount + " can not be given");
        }

        return result;
    }

    private void rollback(Map<Banknote, Integer> data) {
        data.forEach((key, value) -> banknoteCells.get(key.getValue()).add(value));
    }

    private void checkAmountCanBeGiven(int amount) {
        int leastDigit = amount % Banknote.minValue();
        if (leastDigit != 0)
            throw new TooDetailedAmountException("Amount " + leastDigit + " can not be given");
    }

    private void checkBalance(int amount) {
        int balance = getBalance();

        if (balance < amount)
            throw new InsufficientFundsException(amount + " is required, but there are only " + balance);
    }

    @Override
    public ATM clone() {
        ATM clonedATM = new ATM();

        for (Map.Entry<Integer, ATMBanknoteCell> entry : banknoteCells.entrySet()) {
            clonedATM.banknoteCells.put(entry.getKey(), entry.getValue().clone());
        }

        return clonedATM;
    }
}
