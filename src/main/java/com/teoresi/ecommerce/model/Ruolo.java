package com.teoresi.ecommerce.model;

public enum Ruolo {
    USER("User"),
    ADMIN("Admin");

    private final String value;

    private Ruolo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
