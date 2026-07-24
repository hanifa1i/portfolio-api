package com.hanif.portfolioapi.enums;

public enum EntityType {
    ARTWORK("artwork"),
    SKETCHBOOK_PAGE("sketchbook page"),
    SKILL("skill"),
    EDUCATION("qualification"),
    EXPERIENCE("work experience");

    private final String displayName;

    EntityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
