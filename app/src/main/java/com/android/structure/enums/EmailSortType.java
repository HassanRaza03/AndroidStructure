package com.android.structure.enums;

public enum EmailSortType {
    ALL,
    INCOMPLETE,
    COMPLETED;


    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static EmailSortType fromCanonicalForm(String canonical) {
        return valueOf(EmailSortType.class, canonical.toUpperCase());
    }
}