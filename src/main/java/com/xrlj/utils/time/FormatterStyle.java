package com.xrlj.utils.time;

public enum FormatterStyle {

    N1("yyyy-MM-dd"),
    N2("yyyy-MM-dd HH:mm"),
    N3("yyyy-MM-dd HH:mm:ss"),
    M1("yyyy/MM/dd"),
    M2("yyyy/MM/dd HH:mm"),
    M3("yyyy/MM/dd HH:mm:ss");

    private final String value;

    FormatterStyle(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
