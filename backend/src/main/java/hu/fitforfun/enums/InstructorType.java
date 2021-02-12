package hu.fitforfun.enums;

public enum InstructorType {
    GUEST("Guest"),
    USER("User"),
    ADMIN("Admin");

    private String name;

    InstructorType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}