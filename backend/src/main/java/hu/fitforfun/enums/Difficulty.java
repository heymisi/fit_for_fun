package hu.fitforfun.enums;

public enum Difficulty {
    BEGINNER(1),
    BASIC(2),
    INTERMEDIATE(3),
    ADVANCED(4),
    EXPERT(5);

    private int level;

    Difficulty(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
