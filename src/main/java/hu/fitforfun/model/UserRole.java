package hu.fitforfun.model;

public enum UserRole {
    GUEST("Guest"),
    USER("User"),
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