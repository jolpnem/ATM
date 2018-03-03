package ru.test.ATM;

import ru.test.ATM.exceptions.IllegalATMStateException;
import ru.test.ATM.exceptions.InsufficientFundsException;
import ru.test.ATM.exceptions.TooDetailedAmountException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ATM implements Cloneable {
    private Map<Integer, BanknoteCell> banknoteCells = new HashMap<>();

    void deposit(Banknote banknote) {
        deposit(banknote, 1);
    }

    void deposit(Banknote banknote, int count) {
        if (!banknoteCells.containsKey(banknote.getValue()))
            banknoteCells.put(banknote.getValue(), new BanknoteCell(banknote));

        banknoteCells.get(banknote.getValue()).add(count);
    }

    Map<Banknote, Integer> withdraw(int amount) {
        checkBalance(amount);
        checkAmountCanBeGiven(amount);

        if (isItPossibleToWithdrawWithOneBanknote(amount)) {
            return withdrawBanknote(amount);
        }

        return withdrawAmount(amount);
    }

    private boolean isItPossibleToWithdrawWithOneBanknote(int banknoteDenomination) {
        return banknoteCells.containsKey(banknoteDenomination) && !banknoteCells.get(banknoteDenomination).isEmpty();
    }

    private Map<Banknote, Integer> withdrawBanknote(int banknoteDenomination) {
        banknoteCells.get(banknoteDenomination).subtractIfPresent(1);
        
        return Map.of(Banknote.byValue(banknoteDenomination), 1);
    }

    int getBalance() {
        return banknoteCells.values().stream().mapToInt(BanknoteCell::getBalance).sum();
    }

    private Map<Banknote, Integer> withdrawAmount(int amount) {
        Map<Banknote, Integer> result = new HashMap<>();

        int remainingAmount = Stream.of(banknoteCells.keySet().toArray()).mapToInt(banknote -> (int) banknote)
                .reduce(amount, (remainingAmountToWithdraw, banknote) -> {
                    if (remainingAmountToWithdraw == 0) return 0;

                    int banknotesToWithdraw = remainingAmountToWithdraw / banknote;
                    int withdrawnBanknotes = banknoteCells.get(banknote).subtractHowManyIsPresent(banknotesToWithdraw);

                    result.putAll(Map.of(Banknote.byValue(banknote), withdrawnBanknotes));

                    return remainingAmountToWithdraw - banknote * withdrawnBanknotes;
        });

        if (remainingAmount > 0) {
            rollback(result);
            throw new IllegalATMStateException("Amount " + amount + " can not be given");
        }

        return result;
    }

    private void rollback(Map<Banknote, Integer> banknotes) {
        banknotes.forEach((key, value) -> banknoteCells.get(key.getValue()).add(value));
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

        for (Map.Entry<Integer, BanknoteCell> entry : banknoteCells.entrySet()) {
            clonedATM.banknoteCells.put(entry.getKey(), entry.getValue().clone());
        }

        return clonedATM;
    }
}
