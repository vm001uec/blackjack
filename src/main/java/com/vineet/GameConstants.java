package com.vineet;

public enum GameConstants {
    DEALER_NAME("Dearler");

    private String value;
    GameConstants(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }

}
