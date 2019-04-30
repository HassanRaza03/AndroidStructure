package com.android.structure.enums;

public enum EmployeeAssessmentState {
    INPROGRESS,
    COMPLETED;

    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static EmployeeAssessmentState fromCanonicalForm(String canonical) {
        return valueOf(EmployeeAssessmentState.class, canonical.toUpperCase());
    }
}