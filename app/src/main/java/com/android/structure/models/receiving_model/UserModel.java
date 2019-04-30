package com.android.structure.models.receiving_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @Expose
    @SerializedName("CAC")
    private String CAC;
    @Expose
    @SerializedName("isRecordExist")
    private String isRecordExist;
    @Expose
    @SerializedName("PIN")
    private String PIN;
    @Expose
    @SerializedName("Password")
    private String Password;
    @Expose
    @SerializedName("UserID")
    private String UserID;
    @Expose
    @SerializedName("UserPicture")
    private String UserPicture;
    @Expose
    @SerializedName("UserPictureExists")
    private boolean UserPictureExists;
    @Expose
    @SerializedName("Role")
    private String Role;
    @Expose
    @SerializedName("PhysicianNumber")
    private String PhysicianNumber;
    @Expose
    @SerializedName("Speciality")
    private String Speciality;
    @Expose
    @SerializedName("Mnemonic")
    private String Mnemonic;
    @Expose
    @SerializedName("Name")
    private String Name;

    public String getCAC() {
        return CAC;
    }

    public void setCAC(String CAC) {
        this.CAC = CAC;
    }

    public String getIsRecordExist() {
        return isRecordExist;
    }

    public void setIsRecordExist(String isRecordExist) {
        this.isRecordExist = isRecordExist;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserPicture() {
        return UserPicture;
    }

    public void setUserPicture(String UserPicture) {
        this.UserPicture = UserPicture;
    }

    public boolean getUserPictureExists() {
        return UserPictureExists;
    }

    public void setUserPictureExists(boolean UserPictureExists) {
        this.UserPictureExists = UserPictureExists;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getPhysicianNumber() {
        return PhysicianNumber;
    }

    public void setPhysicianNumber(String PhysicianNumber) {
        this.PhysicianNumber = PhysicianNumber;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String Speciality) {
        this.Speciality = Speciality;
    }

    public String getMnemonic() {
        return Mnemonic;
    }

    public void setMnemonic(String Mnemonic) {
        this.Mnemonic = Mnemonic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}