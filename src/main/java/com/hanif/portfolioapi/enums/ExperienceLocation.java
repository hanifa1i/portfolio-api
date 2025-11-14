package com.hanif.portfolioapi.enums;

public enum ExperienceLocation {
    SELF_STUDY("Self-Study"),
    UXBRIDGE_COLLAGE("Uxbrdige College"),
    BRUNEL_UNIVERSITY("Brunel University"),
    SPARTA_GLOBAL("Sparta Global"),
    HMLR("HMLR");

    private final String displayName;

    ExperienceLocation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
