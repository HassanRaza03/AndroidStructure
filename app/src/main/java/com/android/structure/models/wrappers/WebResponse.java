package com.android.structure.models.wrappers;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khanhamza on 09-Mar-17.
 */

public class WebResponse<T> {

    @SerializedName("ResponseMessage")
    public String responseMessage;

    @SerializedName("ResponseCode")
    public int responseCode;

    @SerializedName("ResponseType")
    public String responseType;

    @SerializedName("ResponseResult")
    public T result;

    public boolean isSuccess() {
        return responseCode == 200;
    }
}
