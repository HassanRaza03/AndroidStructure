package com.android.structure.enums;

public enum BaseURLTypes {
    PACS_VIEWER,
    EHS_BASE_URL,
    PACS_IMAGE_DOWNLOAD,
    GET_EMP_DEPT_URL,
    AUTHENTICATE_USER_URL;



    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static BaseURLTypes fromCanonicalForm(String canonical) {
        return valueOf(BaseURLTypes.class, canonical.toUpperCase());
    }
}