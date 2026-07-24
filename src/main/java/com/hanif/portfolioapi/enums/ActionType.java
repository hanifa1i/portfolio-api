package com.hanif.portfolioapi.enums;

public enum ActionType {
    CREATE("created"),
    UPDATE("updated"),
    DELETE("deleted");

    private final String displayName;

    ActionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
