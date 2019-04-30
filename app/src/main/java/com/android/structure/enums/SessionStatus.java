package com.android.structure.enums;

public enum SessionStatus {
    OPENED,
    CLOSED;

    public String canonicalForm() {
        return this.name().toUpperCase();
    }

    public static SessionStatus fromCanonicalForm(String canonical) {
        return valueOf(SessionStatus.class, canonical.toUpperCase());
    }
}