package com.memsys.web.enums;

public enum UserCount {
    ADM(0),
    OKY(0),
    SUS(0),
    DEL(0);

    private int value;
    UserCount(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
