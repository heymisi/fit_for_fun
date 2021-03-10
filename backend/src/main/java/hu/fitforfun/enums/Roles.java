package hu.fitforfun.enums;

public enum Roles {
    GUEST("Guest"),
    ROLE_USER("ROLE_USER"),
    ROLE_INSTRUCTOR("ROLE_INSTRUCTOR"),
    ROLE_ADMIN("Admin");

    private String name;

    Roles(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}