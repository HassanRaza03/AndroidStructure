package com.android.structure.constatnts;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by khanhamza on 09-Mar-17.
 */

public class WebServiceConstants {


    private static Map<String, String> headers;

    public static Map<String, String> getHeaders(String token) {
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("_token", token);
        }
        return headers;
    }


    // FIXME: 6/25/2018 If TRUE, for testing purpose only
    public static boolean record_found_bypass = false;

    /**
     * URLs
     */


    /**
     * BEFORE LIVE DEPLOYMENT
     * Change URL Live/ UAT
     * Change Image URL
     * Check Version Code
     * BaseApplication Fabric enable
     */
//    https://userauthentication.aku.edu
    //    public static final String BASE_URL_LOGIN = "http://userauthservicedev.aku.edu/api/";
    //    public static final String BASE_URL_EMAIL_VALIDATION = "http://userauthservicedev.aku.edu/api/";

    public static final String BASE_URL_LOGIN = "https://userauthentication.aku.edu/api/";
    public static final String BASE_URL_EMAIL_VALIDATION = "https://userauthentication.aku.edu/api/";
    public static final String GETIMAGE_BASE_URL = "https://familyhifazatmobileapi.aku.edu/getimage?path=";



    // UAT
    public static final String BASE_URL = "http://ehsapi.aku.edu/api/";
//    public static final String WS_AKU_DEPT_EMP_GET_BASE_URL = "https://uerpdmo.aku.edu/PSIGW/RESTListeningConnector/PSFT_HR/AKU_DEPT_EMPS_GET.v1/";

    // LIVE
//    public static final String BASE_URL = "https://ehsliveapi.aku.edu/api/";
    public static final String WS_AKU_DEPT_EMP_GET_BASE_URL = "https://erphweb.aku.edu/PSIGW/RESTListeningConnector/PSFT_HR/AKU_DEPT_EMPS_GET.v1/";



    public static final String WS_KEY_GET_TOKEN = "getToken";
    public static final String _token = "-Oj71a1_t8phbV5hzL19FqURXQ2R8VUU";

    // People soft
    public static final String WS_TOKEN_CONSTANT = "Authorization: Basic QUtVX1dBUE1fUkVTVF9FTVBfREVQVDo5N0FBNUI0QS0zNzEwLTRFREYtOTQxMS02QjVEQTlEMDBEQ0U=";
//    public static final String WS_TOKEN_CONSTANT = "Authorization: Basic QUtVX1RMX1JFU1RfRU1QX0RFUFQ6ezVDNEY0MkIzLUYyRDktNzQ1Ny0yQURDLTM5RTcxNDYyMDJCMn0=";


    public static final String WS_AKU_DEPT_EMP_PART = "SHARE/{type}/{value}";


    /**
     * API PARAMS
     */
    public static final String PARAMS_REQUEST_METHOD = "requestmethod";
    public static final String PARAMS_REQUEST_DATA = "requestdata";

    // People soft API Params
    public static final String DIV_KEY = "2V";
    public static final String DEPT_KEY = "1D";
    public static final String DEPT_DETAIL_KEY = "2D";
    public static final String EMPLOYEE_NO_KEY = "1E";
    public static final String MR_NO_KEY = "1M";
    public static final String GET_ALL_KEY = "*";


    /**
     * REQUEST METHODS NAMES
     */

    // UserManager
    public static final String METHOD_USER_LOGIN = "UserManager.Login";
    public static final String METHOD_USER_GET_USER_IMAGE = "UserManager.GetUserImage";

    //DictionaryManager
    public static final String METHOD_ADD_SESSION = "DictionaryManager.AddSession";
    public static final String METHOD_UPDATE_SESSION = "DictionaryManager.UpdateSession";
    public static final String METHOD_GET_ACTIVE_QUESTION_LIST = "DictionaryManager.GetActiveQuestionList";
    public static final String METHOD_GET_ACTIVE_MEASUREMENTS_LIST = "DictionaryManager.GetActiveMeasurementsList";

    //SessionManager
    public static final String METHOD_GET_SESSION_LIST = "SessionManager.GetSessionList";
    public static final String METHOD_GET_SESSION_EMPLOYEES = "SessionManager.GetSessionEmp";
    public static final String METHOD_ADD_SESSION_EMPLOYEE = "SessionManager.AddSessionEmp";
    public static final String METHOD_UPDATE_SESSION_EMPLOYEE = "SessionManager.UpdateSessionEmp";
    public static final String METHOD_EMAIL_SESSION = "SessionManager.EmailSession";
    public static final String METHOD_GET_EMPLOYEE_MEASUREMENTS = "SessionManager.GetEmployeeMeasurement";
    public static final String METHOD_SAVE_EMPLOYEE_MEASUREMENTS = "SessionManager.SaveEmployeeMeasurement";
    public static final String METHOD_GET_EMPLOYEE_ASSESSMENTS = "SessionManager.GetEmployeeAssessment";
    public static final String METHOD_SAVE_EMPLOYEE_ASSESSMENT = "SessionManager.SaveEmployeeAssessment";
    public static final String METHOD_GET_EMPLOYEE_LAB_TEST_RESULTS = "SessionManager.GetEmployeeLabTestResults";
    public static final String METHOD_SYNC_EMPLOYEE_LABS = "SessionManager.SyncEmployeeLabs";


    //Reporter Manager
    public static final String METHOD_GET_EMPLOYEE_SUMMARY_DETAIL = "ReportManager.GetEmployeeSummaryDetail";
    public static final String METHOD_GET_SESSION_STATS = "ReportManager.GetSessionStats";


    public static final String WS_KEY_AUTHENTICATE_USER = "AuthenticateUser";

    public static final String METHOD_GET_ONE_TIME_TOKEN = "SharedManager.GetOneTimeCode";

}
