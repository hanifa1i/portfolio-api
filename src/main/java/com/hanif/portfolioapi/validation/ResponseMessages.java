package com.hanif.portfolioapi.validation;

public class ResponseMessages {

    //Successful
    public static final String ARTWORK_CREATED = "Artwork added successfully";
    public static final String ARTWORK_UPDATED = "Artwork updated successfully";
    public static final String ARTWORK_DELETED = "Artwork deleted successfully";
    public static final String VISIBILITY_UPDATED = "Visibility updated successfully";
    public static final String EDUCATION_CREATED = "Education added successfully";
    public static final String EDUCATION_UPDATED = "Education updated successfully";
    public static final String EDUCATION_DELETED = "Education deleted successfully";
    public static final String EXPERIENCE_CREATED = "Experience added successfully";
    public static final String EXPERIENCE_UPDATED = "Experience updated successfully";
    public static final String EXPERIENCE_DELETED = "Experience deleted successfully";
    public static final String SKILL_CREATED = "Skill added successfully";
    public static final String SKILL_UPDATED = "Skill updated successfully";
    public static final String SKILL_DELETED = "Skill deleted successfully";
    public static final String TAGS_CREATED = "Multiple tags added successfully";
    public static final String TAG_CREATED = "Tag added successfully";
    public static final String TAG_DELETED = "Tag deleted successfully";

    //Unsuccessful
    public static final String ARTWORK_NOT_FOUND = "Artwork with the id: %d, does not exist";
    public static final String IMAGE_NOT_FOUND = "Image with the id: %d, does not exist";
    public static final String EXAMPLE_NOT_FOUND = "Example Image with the id: %d, does not exist";

    public static final String TAG_NOT_FOUND = "Artworks with the tag: %s, does not exist";
    public static final String EDUCATION_NOT_FOUND = "Education with the id: %d, does not exist";
    public static final String EXPERIENCE_NOT_FOUND = "Experience with the id: %d, does not exist";
    public static final String SKILL_NOT_FOUND = "Skill with the id: %d, does not exist";
    public static final String USER_NOT_FOUND = "Username does not exist";
    public static final String PASSWORD_NOT_FOUND = "Password is incorrect";

    public static final String INVALID_TOKEN = "Invalid token";
    public static final String EXPIRED_TOKEN = "Expired token";
    public static final String AUTHENTICATION_FAIL = "Authentication failed";







}
