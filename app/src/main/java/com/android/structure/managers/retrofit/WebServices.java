package com.android.structure.managers.retrofit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.android.structure.constatnts.WebServiceConstants;
import com.android.structure.managers.FileManager;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;
import java.io.IOException;

import com.android.structure.enums.BaseURLTypes;
import com.android.structure.enums.FileType;
import com.android.structure.helperclasses.Helper;
import com.android.structure.helperclasses.ui.helper.UIHelper;
import com.android.structure.models.wrappers.WebResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hamzakhan on 6/30/2017.
 */

public class WebServices {
    private WebServiceProxy apiService;
    private KProgressHUD mDialog;
    private Context mContext;
    private static String bearerToken = "";

    public static String getBearerToken() {
        return bearerToken;
    }

    public static void setBearerToken(String bearerToken) {
        WebServices.bearerToken = bearerToken;
    }

    public WebServices(Context activity, String token, BaseURLTypes baseURLTypes) {
        switch (baseURLTypes) {

            case EHS_BASE_URL:
                apiService = WebServiceFactory.getInstanceBaseURL(token);
                break;
            case GET_EMP_DEPT_URL:
                apiService = WebServiceFactory.getInstanceXML();
            case AUTHENTICATE_USER_URL:
                apiService = WebServiceFactory.getInstanceAuthenicateUser();
        }


        mContext = activity;
        mDialog = UIHelper.getProgressHUD(mContext);
        if (!((Activity) mContext).isFinishing())
            mDialog.show();
    }

    public WebServices(Context activity, String token, BaseURLTypes baseURLTypes, boolean isShowLoader) {
        switch (baseURLTypes) {
            case EHS_BASE_URL:
                apiService = WebServiceFactory.getInstanceBaseURL(WebServiceConstants._token);
                break;
            case GET_EMP_DEPT_URL:
                apiService = WebServiceFactory.getInstanceXML();
            case AUTHENTICATE_USER_URL:
                apiService = WebServiceFactory.getInstanceAuthenicateUser();
        }


        mContext = activity;

        if (isShowLoader) {
            mDialog = UIHelper.getProgressHUD(mContext);

            if (!((Activity) mContext).isFinishing())
                mDialog.show();
        }

    }

    public WebServices(Context mContext) {
        this.mContext = mContext;
//        mDialog = new ProgressDialog(mContext);
//        mDialog.setMessage("Loading...");
//        mDialog.setTitle("Please Wait");
//        mDialog.setCancelable(true);
        mDialog = UIHelper.getProgressHUD(mContext);

        if (!((Activity) mContext).isFinishing())
            mDialog.show();
    }

    private static boolean IsResponseError(Response<WebResponse<JsonObject>> response) {
        return !(response != null && !response.isSuccessful() && response.errorBody() != null);
    }


    private boolean hasValidStatus(Response<WebResponse<JsonObject>> response) {
        if (response != null && response.body() != null) {
            if (response.body().isSuccess()) {

                if (WebServiceConstants.record_found_bypass) {
                    return true;
                }

                if (response.body().result.get("RecordFound") == null) {
                    if (response.body().result.get("StrStatus") == null) {
                        return false;
                    } else if (response.body().result.get("StrStatus").isJsonNull()) {
                        return false;
                    } else {
                        return !response.body().result.get("StrStatus").getAsString().isEmpty();
                    }
                } else if (response.body().result.get("RecordFound").isJsonNull()) {
                    return false;
                } else {
                    return response.body().result.get("RecordFound").getAsString().equals("true");
                }
            } else {
                return false;
            }

        } else {
            return false;
        }
    }


    public void webServiceUploadFileAPI(String requestMethod, String filePath, FileType fileType, String reqBody,
                                        final IRequestJsonDataCallBack callBack) {

        RequestBody bodyRequestMethod = getRequestBody(okhttp3.MultipartBody.FORM, requestMethod);
        RequestBody requestBody = getRequestBody(okhttp3.MultipartBody.FORM, reqBody);

        MultipartBody.Part part;
        if (filePath == null || !FileManager.isFileExits(filePath)) {
            dismissDialog();
            UIHelper.showShortToastInCenter(mContext, "File path is empty.");
            return;
        }

        File file = new File(filePath);

        part =
                MultipartBody.Part.createFormData(WebServiceConstants.PARAMS_REQUEST_DATA, file.getName(),
                        RequestBody.create(MediaType.parse(fileType.canonicalForm() + "/" + FileManager.getExtension(file.getName())), file)
                );


        try {
            if (Helper.isNetworkConnected(mContext, true)) {
                apiService.uploadFileRequestApi(bodyRequestMethod, requestBody, part).enqueue(
                        new Callback<WebResponse<JsonObject>>() {
                            @Override
                            public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                                dismissDialog();
                                if (!IsResponseError(response)) {
                                    String errorBody;
                                    try {
                                        errorBody = response.errorBody().string();
                                        UIHelper.showShortToastInCenter(mContext, errorBody);
                                        callBack.onError();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }

                                if (hasValidStatus(response)) {
                                    if (callBack != null)
                                        callBack.requestDataResponse(response.body());
                                } else {
                                    String message = response.body().responseMessage != null ? response.body().responseMessage : response.errorBody().toString();
//                                    UIHelper.showShortToastInCenter(mContext, responseMessage);
                                }
                            }

                            @Override
                            public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                                UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                                dismissDialog();
                                callBack.onError();
                            }
                        });
            } else {
                dismissDialog();
                callBack.onError();
            }

        } catch (Exception e) {
            dismissDialog();
            e.printStackTrace();

        }
    }

    public Call<WebResponse<JsonObject>> webServiceRequestAPIForJsonObject(final String requestMethod, String requestData, final IRequestJsonDataCallBack callBack) {

        RequestBody bodyRequestMethod = getRequestBody(okhttp3.MultipartBody.FORM, requestMethod);
        RequestBody bodyRequestData = getRequestBody(okhttp3.MultipartBody.FORM, requestData);
        Call<WebResponse<JsonObject>> webResponseCall = apiService.webServiceRequestAPI(bodyRequestMethod, bodyRequestData);

        try {
            if (Helper.isNetworkConnected(mContext, true)) {

                webResponseCall.enqueue(new Callback<WebResponse<JsonObject>>() {
                    @Override
                    public void onResponse(Call<WebResponse<JsonObject>> call, Response<WebResponse<JsonObject>> response) {
                        dismissDialog();
                        if (!IsResponseError(response)) {
                            String errorBody;
                            try {
                                errorBody = response.errorBody().string();
                                UIHelper.showShortToastInCenter(mContext, errorBody);
                                callBack.onError();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }

                        if (hasValidStatus(response)) {
                            if (callBack != null)
                                callBack.requestDataResponse(response.body());
                        } else {
                            if (response.body() != null) {
                                if (response.body().isSuccess()) {
                                    if (response.body().result.get("RecordMessage") == null) {

                                        if (response.body().result.get("Message") == null) {
                                            errorToastForObject(response);
                                        } else if (response.body().result.get("Message").isJsonNull()) {
                                            errorToastForObject(response);
                                        } else {
                                            String message = response.body().result.get("Message").toString();
                                            if (requestMethod.equals(WebServiceConstants.METHOD_USER_LOGIN)) {
                                                UIHelper.showShortToastInCenter(mContext, message);
                                            } else {
//                                            UIHelper.showShortToastInCenter(mContext, responseMessage);
                                            }
                                        }

                                    } else if (response.body().result.get("RecordMessage").isJsonNull()) {
                                        errorToastForObject(response);
                                    } else {
                                        // Show Toast here
                                        String message = response.body().result.get("RecordMessage").toString();
//                                        if (requestMethod.equals(WebServiceConstants.METHOD_USER_LOGIN)
//                                                || requestMethod.equals(WebServiceConstants.METHOD_USER_GENERATE_RESET)
//                                                || requestMethod.equals(WebServiceConstants.METHOD_USER_VERIFY_AND_UPDATE)
//
//                                                ) {
//                                            UIHelper.showShortToastInCenter(mContext, responseMessage);
//                                        } else {
//                                            UIHelper.showShortToastInCenter(mContext, responseMessage);
//                                        }
                                        if (callBack != null) {
                                            callBack.onError();
                                        }
                                    }

                                } else {
                                    String message = response.body().responseMessage != null ? response.body().responseMessage : response.errorBody().toString();
//                                    UIHelper.showToast(mContext, responseMessage);
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<WebResponse<JsonObject>> call, Throwable t) {
                        UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                        dismissDialog();
                        if (callBack != null) {
                            callBack.onError();
                        }
                    }
                });
            } else {
                dismissDialog();
                if (callBack != null) {
                    callBack.onError();
                }
            }

        } catch (
                Exception e)

        {
            dismissDialog();
            e.printStackTrace();

        }


        return webResponseCall;
    }

    public Call<WebResponse<Object>> webServiceRequestAPIAnyObject(String requestMethod, String requestData, final IRequestWebResponseAnyObjectCallBack callBack) {
        RequestBody bodyRequestMethod = getRequestBody(okhttp3.MultipartBody.FORM, requestMethod);
        RequestBody bodyRequestData = getRequestBody(okhttp3.MultipartBody.FORM, requestData);
        Call<WebResponse<Object>> webResponseCall = apiService.webServiceRequestAPIForWebResponseAnyObject(bodyRequestMethod, bodyRequestData);

        try {
            if (Helper.isNetworkConnected(mContext, true)) {
                webResponseCall.enqueue(new Callback<WebResponse<Object>>() {
                    @Override
                    public void onResponse(Call<WebResponse<Object>> call, Response<WebResponse<Object>> response) {
                        dismissDialog();

                        if (response.body() == null) {
                            callBack.onError("empty response");
                            return;
                        }

                        if (response.isSuccessful() && response.body().isSuccess()) {
                            if (callBack != null)
                                callBack.requestDataResponse(response.body());
                        } else {
                            String message = response.body().responseMessage != null ? response.body().responseMessage : response.errorBody().toString();
                            callBack.onError(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<WebResponse<Object>> call, Throwable t) {
                        UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                        dismissDialog();
                        callBack.onError(null);
                    }
                });
            } else {
                dismissDialog();
            }

        } catch (Exception e) {
            dismissDialog();
            e.printStackTrace();

        }

        return webResponseCall;
    }

    public void webServiceAuthenticateUser(String userName, String password, IRequestWebResponseJustObjectCallBack iRequestWebResponseJustObjectCallBack) {
        try {
            if (Helper.isNetworkConnected(mContext, true)) {
                WebServiceFactory.getInstanceAuthenicateUser().authenticateUser(userName, password, "EHS", "localhost").enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        dismissDialog();
                        if (response.body() == null) {
                            iRequestWebResponseJustObjectCallBack.onError(call);
                        } else {
                            iRequestWebResponseJustObjectCallBack.requestDataResponse(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        iRequestWebResponseJustObjectCallBack.onError(call);
                        UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                        dismissDialog();

                    }
                });
            } else {
                dismissDialog();
            }

        } catch (Exception e) {
            dismissDialog();
            e.printStackTrace();

        }

    }


    public void webServiceGetAuthenticatedUserDetail(String userName, IRequestWebResponseJustObjectCallBack iRequestWebResponseJustObjectCallBack) {
        try {
            if (Helper.isNetworkConnected(mContext, true)) {
                WebServiceFactory.getInstanceAuthenicateUser().getAuthenticatedUserDetails(userName, "EHS").enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        dismissDialog();
                        if (response.body() == null) {
                            iRequestWebResponseJustObjectCallBack.onError(call);
                        } else {
                            iRequestWebResponseJustObjectCallBack.requestDataResponse(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        iRequestWebResponseJustObjectCallBack.onError(call);
                        UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                        dismissDialog();

                    }
                });
            } else {
                dismissDialog();
            }

        } catch (Exception e) {
            dismissDialog();
            e.printStackTrace();

        }

    }


    public void webServiceAuthenicateValidationEmail(String emailAddresses, IRequestWebResponseJustObjectCallBack iRequestWebResponseJustObjectCallBack) {
        try {
            if (Helper.isNetworkConnected(mContext, true)) {
                WebServiceFactory.getInstanceAuthenicateEmail().getAuthenticateEmailValidations(emailAddresses).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        dismissDialog();
                        if (response.body() == null) {
                            iRequestWebResponseJustObjectCallBack.onError(call);
                        } else {
                            iRequestWebResponseJustObjectCallBack.requestDataResponse(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        iRequestWebResponseJustObjectCallBack.onError(call);
                        UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                        dismissDialog();

                    }
                });
            } else {
                dismissDialog();
            }

        } catch (Exception e) {
            dismissDialog();
            e.printStackTrace();

        }

    }


    @NonNull
    public RequestBody getRequestBody(MediaType form, String trim) {
        return RequestBody.create(
                form, trim);
    }


    private void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    private void errorToastForObject(Response<WebResponse<JsonObject>> response) {
//        String responseMessage = response.body().responseMessage != null ? response.body().responseMessage : response.errorBody().toString();
//        UIHelper.showShortToastInCenter(mContext, "API Error in RecordFound");
    }


    public interface IRequestJsonDataCallBack {
        void requestDataResponse(WebResponse<JsonObject> webResponse);

        void onError();
    }


    public interface IRequestWebResponseAnyObjectCallBack {
        void requestDataResponse(WebResponse<Object> webResponse);

        void onError(Object object);
    }

    public interface IRequestWebResponseJustObjectCallBack {
        void requestDataResponse(Object webResponse);

        void onError(Object object);
    }

}
