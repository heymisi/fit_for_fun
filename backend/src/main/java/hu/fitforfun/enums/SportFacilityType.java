package hu.fitforfun.enums;

public enum SportFacilityType {
    GYM("Gym"),
    FOOTBALL_COURT("Football court"),
    BASKETBALL_HALL("Basketball hall");

    private String name;

    SportFacilityType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}