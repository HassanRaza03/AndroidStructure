package com.android.structure.managers.retrofit;

import com.android.structure.constatnts.WebServiceConstants;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


/**
 * Created by khanhamza on 09-Mar-17.
 */

public class WebServiceFactory {

    private static Retrofit retrofitBase;
    private static Retrofit retrofitPACSViewer;
    private static Retrofit retrofitGETDeptEmployee;
    private static Retrofit retrofitAuthenticateUser;
    private static Retrofit retrofitAuthenticateEmail;
    private static String staticToken = "";

    /***
     *      SINGLETON Design Pattern
     */
    static WebServiceProxy getInstanceBaseURL(final String _token) {

        if (retrofitBase == null || staticToken.isEmpty() || !staticToken.equals(_token)) {
            staticToken = _token;

//            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                    .create();


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // set your desired log level
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(60, TimeUnit.SECONDS);
            httpClient.readTimeout(60, TimeUnit.SECONDS);


//             add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    requestBuilder.addHeader("_token", staticToken + "");

                    // Request customization: add request headers

                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                }
            });

            // add logging as last interceptor
//            httpClient.addNetworkInterceptor(interceptor).addInterceptor(interceptor);  // <-- this is the important line!
            httpClient.addInterceptor(interceptor);  // <-- this is the important line!
            retrofitBase = new Retrofit.Builder()
//                    .baseUrl(WebServiceConstants.BASE_URL_LIVE)
                    .baseUrl(WebServiceConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSimpleGson()))
                    .client(httpClient.build())
                    .build();
        }

        return retrofitBase.create(WebServiceProxy.class);
    }



    public static WebServiceProxy getInstanceXML() {

        if (retrofitGETDeptEmployee == null) {


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // set your desired log level
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(120, TimeUnit.SECONDS);
            httpClient.readTimeout(121, TimeUnit.SECONDS);


//             add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
//                    requestBuilder.addHeader("_token", _token + "");

                    // Request customization: add request headers

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            // add logging as last interceptor
//            httpClient.addNetworkInterceptor(interceptor).addInterceptor(interceptor);  // <-- this is the important line!
            httpClient.addInterceptor(interceptor);  // <-- this is the important line!
            retrofitGETDeptEmployee = new Retrofit.Builder()
                    .baseUrl(WebServiceConstants.WS_AKU_DEPT_EMP_GET_BASE_URL)
                    .addConverterFactory(
                            SimpleXmlConverterFactory.createNonStrict(
                                    new Persister(new AnnotationStrategy() // important part!
                                    )))
                    .client(httpClient.build())
                    .build();
        }

        return retrofitGETDeptEmployee.create(WebServiceProxy.class);
    }


    public static WebServiceProxy getInstanceAuthenicateUser() {

        if (retrofitAuthenticateUser == null) {


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // set your desired log level
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(120, TimeUnit.SECONDS);
            httpClient.readTimeout(121, TimeUnit.SECONDS);


//             add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
//                    requestBuilder.addHeader("_token", _token + "");

                    // Request customization: add request headers

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            // add logging as last interceptor
//            httpClient.addNetworkInterceptor(interceptor).addInterceptor(interceptor);  // <-- this is the important line!
            httpClient.addInterceptor(interceptor);  // <-- this is the important line!
            retrofitAuthenticateUser = new Retrofit.Builder()
                    .baseUrl(WebServiceConstants.BASE_URL_LOGIN)
                    .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSimpleGson()))
                    .client(httpClient.build())
                    .build();
        }

        return retrofitAuthenticateUser.create(WebServiceProxy.class);
    }


    public static WebServiceProxy getInstanceAuthenicateEmail() {

        if (retrofitAuthenticateEmail == null) {


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // set your desired log level
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(120, TimeUnit.SECONDS);
            httpClient.readTimeout(121, TimeUnit.SECONDS);


//             add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
//                    requestBuilder.addHeader("_token", _token + "");

                    // Request customization: add request headers

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            // add logging as last interceptor
//            httpClient.addNetworkInterceptor(interceptor).addInterceptor(interceptor);  // <-- this is the important line!
            httpClient.addInterceptor(interceptor);  // <-- this is the important line!
            retrofitAuthenticateEmail = new Retrofit.Builder()
                    .baseUrl(WebServiceConstants.BASE_URL_EMAIL_VALIDATION)
                    .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSimpleGson()))
                    .client(httpClient.build())
                    .build();
        }

        return retrofitAuthenticateEmail.create(WebServiceProxy.class);
    }
}