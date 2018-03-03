package ru.test.ATM;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ATMDepartmentTest {
    private ATMDepartment atmDepartment;

    @Before
    public void setUp() {
        this.atmDepartment = new ATMDepartment();
    }

    @Test
    public void testAddATM() {
        atmDepartment.addATM(new ATM());
        atmDepartment.addATM(new ATM());
        atmDepartment.addATM(new ATM());

        int atmCount = 0;
        try {
            Field atmSet = atmDepartment.getClass().getDeclaredField("ATMs");
            atmSet.setAccessible(true);

            atmCount = ((Set)atmSet.get(atmDepartment)).size();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        assertEquals(3, atmCount);
    }

    @Test
    public void testCalculateCashBalance() {
        ATM firstATM = new ATM();
        firstATM.deposit(Banknote.FIVE_THOUSANDS);
        firstATM.deposit(Banknote.FIVE_HUNDREDS);
        atmDepartment.addATM(firstATM);

        ATM secondATM = new ATM();
        secondATM.deposit(Banknote.ONE_HUNDRED, 1);
        atmDepartment.addATM(secondATM);

        assertEquals(5600, atmDepartment.calculateCashBalance());
    }

    @Test
    public void testRestoreATMsStates() {
        ATM firstATM = new ATM();
        firstATM.deposit(Banknote.FIVE_THOUSANDS);
        atmDepartment.addATM(firstATM);

        ATM secondATM = new ATM();
        secondATM.deposit(Banknote.ONE_HUNDRED);
        atmDepartment.addATM(secondATM);

        assertEquals(5100, atmDepartment.calculateCashBalance());

        firstATM.withdraw(5000);
        secondATM.deposit(Banknote.TWO_HUNDREDS, 2);

        assertEquals(500, atmDepartment.calculateCashBalance());

        atmDepartment.restoreATMsStates();

        assertEquals(5100, atmDepartment.calculateCashBalance());
    }
}
