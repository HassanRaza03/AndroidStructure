package com.android.structure.models.sending_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.android.structure.managers.retrofit.GsonFactory;

/**
 * Created by hamza.ahmed on 4/25/2018.
 */

public class InsertRegisteredDeviceModel {


    @Expose
    @SerializedName("REGCARDNO")
    private String regcardno;
    @Expose
    @SerializedName("DEVICEID")
    private String deviceid;
    @Expose
    @SerializedName("DEVICETOKEN")
    private String devicetoken;
    @Expose
    @SerializedName("DEVICEOS")
    private String deviceos;



    public InsertRegisteredDeviceModel() {
    }

    public InsertRegisteredDeviceModel(String regcardno, String deviceid, String devicetoken, String deviceos) {
        this.regcardno = regcardno;
        this.deviceid = deviceid;
        this.devicetoken = devicetoken;
        this.deviceos = deviceos;
    }

    public String getRegcardno() {
        return regcardno;
    }

    public void setRegcardno(String regcardno) {
        this.regcardno = regcardno;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public String getDeviceos() {
        return deviceos;
    }

    public void setDeviceos(String deviceos) {
        this.deviceos = deviceos;
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
