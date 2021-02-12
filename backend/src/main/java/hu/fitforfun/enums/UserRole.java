package hu.fitforfun.enums;

public enum UserRole {
    GUEST("Guest"),
    USER("User"),
    INSTRUCTOR("Instructor"),
    ADMIN("Admin");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}