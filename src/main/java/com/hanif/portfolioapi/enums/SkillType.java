package com.hanif.portfolioapi.enums;

public enum SkillType {
    LANGUAGE_AND_FRAMEWORK("languages & frameworks"),
    BACKEND_AND_DEVOPS("back-end & devOps"),
    TESTING("testing"),
    DATABASES_AND_MESSAGE_BROKERS("databases & message brokers"),
    API_AND_INTEGRATION("API & design awareness"),
    FRONTEND_AND_DESIGN_AWARENESS("front-end & design awareness"),
    SECURITY_AND_BEST_PRACTICES("security & best practices"),
    COLLABORATION_AND_WORKFLOW("collaboration & workflow");

    private final String displayName;

    SkillType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
