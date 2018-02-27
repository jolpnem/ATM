package ru.test.ATM;

class ATMBanknoteCell {
    private final Banknote banknote;
    private int count;

    ATMBanknoteCell(Banknote banknote, int count) {
        this.banknote = banknote;
        this.count = count;
    }

    ATMBanknoteCell(Banknote banknote) {
        this(banknote, 0);
    }

    void add(int count) {
        this.count += count;
    }

    void subtract(int count) {
        if (this.count >= count)
            this.count -= count;
    }

    int subtractHowManyIsPresent(int count) {
        int toSubtract = Integer.min(this.count, count);
        this.count -= toSubtract;

        return toSubtract;
    }

    int getBalance() {
        return
                banknote.getValue() * count;
    }

    boolean isEmpty() {
        return count == 0;
    }

    @Override
    public String toString() {
        return "Banknote - " + banknote.getValue() + ", count - " + count;
    }

    @Override
    public ATMBanknoteCell clone() {
        return new ATMBanknoteCell(banknote, count);
    }
}
