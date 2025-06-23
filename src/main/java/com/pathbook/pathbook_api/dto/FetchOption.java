package com.pathbook.pathbook_api.dto;

public enum FetchOption {
    NONE("NONE"),
    COUNT("COUNT"),
    FULL("FULL");

    private final String option;

    FetchOption(final String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }

    public static FetchOption fromString(String optionName) {
        try {
            return FetchOption.valueOf(optionName.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return NONE;
        }
    }
}
