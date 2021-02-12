package hu.fitforfun.enums;

public enum ExerciseType {
    SHOULDER("Shoulder"),
    ARM("Arm"),
    LEG("Leg"),
    CHEST("Chest"),
    BACK("Back");

    private String name;

    ExerciseType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}