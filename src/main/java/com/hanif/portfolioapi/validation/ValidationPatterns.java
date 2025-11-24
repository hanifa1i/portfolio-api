package com.hanif.portfolioapi.validation;

import com.hanif.portfolioapi.enums.ExperienceLocation;
import com.hanif.portfolioapi.enums.SkillType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidationPatterns {

    public static final String URL =  "^(https?://).+";

    public static final String IMAGE_URL = "^(https?://).+\\.(png|jpg|jpeg|webp|gif)$";

    public static final String SKILL_TYPE_ENUMS = enumToRegex(SkillType.class);

    public static final String EXPERIENCE_LOCATION_ENUMS = enumToRegex(ExperienceLocation.class);

    private static String enumToRegex(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining("|"));
    }
}
