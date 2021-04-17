package hu.fitforfun.enums;

public enum Gender {
    ACTUAL_MONTH(0),
    MONTH(-1),
    QUARTER(-3),
    PQUARTER(-4),
    ETERNAL(0);

    private int periodLength;

    Gender(int periodLength) {
        this.periodLength = periodLength;
    }

    public int getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(int periodLength) {
        this.periodLength = periodLength;
    }
}