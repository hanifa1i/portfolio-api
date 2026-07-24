package com.hanif.portfolioapi.enums;

public enum ExperienceLocation {
    SELF_STUDY("self-study"),
    UXBRIDGE_COLLAGE("uxbridge college"),
    BRUNEL_UNIVERSITY("brunel university"),
    SPARTA_GLOBAL("sparta global"),
    HMLR("land registry");

    private final String displayName;

    ExperienceLocation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
