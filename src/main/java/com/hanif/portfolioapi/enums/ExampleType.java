package com.hanif.portfolioapi.enums;

public enum ExampleType {
    LINK("Link"),
    IMAGE("Image");

    private final String displayName;

    ExampleType (String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
