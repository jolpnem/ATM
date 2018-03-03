package ru.test.ATM;

class BanknoteCell implements Cloneable {
    private final Banknote banknote;
    private int count;

    BanknoteCell(Banknote banknote, int count) {
        this.banknote = banknote;
        this.count = count;
    }

    BanknoteCell(Banknote banknote) {
        this(banknote, 0);
    }

    void add(int count) {
        this.count += count;
    }

    void subtractIfPresent(int count) {
        if (this.count >= count) {
            subtract(count);
        }
    }

    private void subtract(int count) {
        this.count -= count;
    }

    int subtractHowManyIsPresent(int count) {
        int possibleBanknotesToSubtract = Integer.min(this.count, count);
        subtract(possibleBanknotesToSubtract);

        return possibleBanknotesToSubtract;
    }

    int getBalance() {
        return banknote.getValue() * count;
    }

    boolean isEmpty() {
        return count == 0;
    }

    @Override
    public String toString() {
        return "Banknote - " + banknote.getValue() + ", count - " + count;
    }

    @Override
    public BanknoteCell clone() {
        return new BanknoteCell(banknote, count);
    }
}
