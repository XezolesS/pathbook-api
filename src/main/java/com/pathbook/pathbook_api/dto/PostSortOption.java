package com.pathbook.pathbook_api.dto;

public enum PostSortOption {
    DEFAULT("NONE"),
    VIEW_DESC("VIEW_DESC"),
    LIKE_DESC("LIKE_DESC"),
    BOOKMARK_DESC("BOOKMARK_DESC");

    private final String option;

    PostSortOption(final String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }

    public static PostSortOption fromString(String optionName) {
        try {
            return PostSortOption.valueOf(optionName.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return DEFAULT;
        }
    }
}
