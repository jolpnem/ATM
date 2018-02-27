package ru.test.ATM;

import org.junit.*;
import ru.test.ATM.exceptions.IllegalATMStateException;
import ru.test.ATM.exceptions.InsufficientFundsException;
import ru.test.ATM.exceptions.TooDetailedAmountException;

import java.util.Map;

import static org.junit.Assert.*;

public class ATMTest {
    private ATM atm;

    @Before
    public void setUp() {
        atm = new ATM();
    }

    @Test
    public void getBalance() {
        atm.deposit(Banknote.FIVE_THOUSANDS, 10);
        atm.deposit(Banknote.TWO_THOUSANDS, 20);
        atm.withdraw(2000);

        assertEquals(88000, atm.getBalance());
    }

    @Test
    public void depositOneBanknote() {
        atm.deposit(Banknote.ONE_THOUSAND);
        assertEquals(1000, atm.getBalance());
    }

    @Test
    public void depositBanknotes() {
        atm.deposit(Banknote.TWO_HUNDREDS, 10);
        assertEquals(2000, atm.getBalance());
    }

    @Test(expected = InsufficientFundsException.class)
    public void withdrawTooManyAmount() {
        atm.deposit(Banknote.ONE_THOUSAND);
        try {
            atm.withdraw(2000);
        } catch (InsufficientFundsException e) {
            assertEquals("2000 is required, but there are only 1000", e.getMessage());
            throw new InsufficientFundsException(e);
        }
    }

    @Test(expected = TooDetailedAmountException.class)
    public void withdrawWrongAmount() {
        atm.deposit(Banknote.ONE_THOUSAND);
        try {
            atm.withdraw(1);
        } catch (TooDetailedAmountException e) {
            assertEquals("Amount 1 can not be given", e.getMessage());
            throw new TooDetailedAmountException(e);
        }
    }

    @Test(expected = IllegalATMStateException.class)
    public void withdrawBanknoteThatDoesNotExist() {
        atm.deposit(Banknote.FIVE_THOUSANDS);
        atm.deposit(Banknote.ONE_THOUSAND);
        atm.deposit(Banknote.FIVE_HUNDREDS, 2);
        try {
            atm.withdraw(2500);
        } catch (IllegalATMStateException e) {
            assertEquals(7000, atm.getBalance());
            assertEquals("Amount 2500 can not be given", e.getMessage());
            throw new IllegalATMStateException(e);
        }
    }

    @Test
    public void shouldWithdrawAmountInSmallerBanknotesIfThereAreNoLarge() {
        atm.deposit(Banknote.ONE_THOUSAND);
        atm.deposit(Banknote.FIVE_HUNDREDS, 2);
        Map<Banknote, Integer> withdrawn = atm.withdraw(2000);

        assertEquals(2000, withdrawn.entrySet()
                .stream().mapToInt(entry -> entry.getKey().getValue() * entry.getValue()).sum());
        assertEquals(1, withdrawn.get(Banknote.ONE_THOUSAND).intValue());
        assertEquals(2, withdrawn.get(Banknote.FIVE_HUNDREDS).intValue());
    }
}
