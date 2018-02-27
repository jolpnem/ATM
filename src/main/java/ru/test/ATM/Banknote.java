package ru.test.ATM;

import java.util.stream.Stream;

public enum Banknote {
    FIVE_THOUSANDS(5000),
    TWO_THOUSANDS(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDREDS(500),
    TWO_HUNDREDS(200),
    ONE_HUNDRED(100),
    FIFTY(50),
    TEN(10),
    ZERO(0);

    private final int value;

    Banknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Banknote byValue(int value) {
        return Stream.of(values())
                .filter(banknote -> Banknote.valueOf(banknote.name()).getValue() == value).findFirst()
                .orElse(Banknote.ZERO);
    }

    public static int minValue() {
        return Stream.of(values()).mapToInt(Banknote::getValue).filter(number -> number > 0).min().orElse(1);
    }
}
