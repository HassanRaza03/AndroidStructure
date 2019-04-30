package com.android.structure.enums;

public enum EmployeeSessionState {
    ENROLLED,
    SCHEDULED,
    INPROGRESS,
    COMPLETED,
    CANCELLED;

    public String canonicalForm() {
        return this.name().toUpperCase();
    }

    public static EmployeeSessionState fromCanonicalForm(String canonical) {
        return valueOf(EmployeeSessionState.class, canonical.toUpperCase());
    }
}