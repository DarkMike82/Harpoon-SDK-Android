package com.amgrade.harpoonsdk.rest;

import com.amgrade.harpoonsdk.Constants;
import com.amgrade.harpoonsdk.HarpoonSDK;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
//import retrofit.converter.SimpleXMLConverter;

/**
 * Created by michael on 26.05.15.
 */
public class RestClient implements Constants{
//    public static final String JSON = ".json";
//    public static final String XML = ".xml";
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private static RestClient sInstance;

    private ApiService mApiService;

//    private String mFormat;

    /**
     * Singleton to use api client.
     * @return RestClient to work with api methods
     */
    public static RestClient getInstance() {
        if (sInstance==null) {
            sInstance = new RestClient();
        }
        return sInstance;
    }

    public RestClient() {
        Converter converter = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JsonTypeAdapterFactory())
                .setDateFormat(DATE_FORMAT)
                .create();
        converter = new GsonConverter(gson);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(BASE_URL)
                .setConverter(converter)
                .setRequestInterceptor(createInterceptor())
                .build();
        mApiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }

    private RequestInterceptor createInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("appid", HarpoonSDK.getAppId());
                request.addHeader("appsecret", HarpoonSDK.getAppSecret());
                request.addHeader("appbundle", HarpoonSDK.getAppBundle());
                request.addHeader("device", null/*?*/); //TODO
                request.addHeader("visitor", null/*?*/); //TODO
                request.addHeader("Accept", "application/json");
                request.addHeader("Content-Type", "application/json");
            }
        };
    }

    //-----------------------------------------------------------------------------------------------------------------------
    // version of methods with ability to choose response format (json/xml)
    //-----------------------------------------------------------------------------------------------------------------------

    /**
     * Singleton to use api client.
     * @param format data format for response. Acceptable values: {@link #JSON} or {@link #XML} otherwise exception will be thrown.
     * @return RestClient to work with api methods
     */
/*    public static RestClient getInstance(@NonNull String format) throws Exception {
        if (sInstance==null || !sInstance.getFormat().contentEquals(format)) {
            sInstance = new RestClient(format);
        }
        //TODO maybe set format instead of create with format
        return sInstance;
    }

    public RestClient(@NonNull String format) throws Exception {
        Converter converter = null;
        if (format==null) {
            throw new NullPointerException("Data format can't be null");
        } else if (format.contentEquals(JSON)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new JsonTypeAdapterFactory())
                    .setDateFormat(DATE_FORMAT)
                    .create();
            converter = new GsonConverter(gson);
        } else if (format.contentEquals(XML)) {
            converter = new SimpleXMLConverter();
        } else {
            throw new Exception("Wrong data format");
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(BASE_URL)
                .setConverter(converter)
                .build();
    }

    public String getFormat() {
        return mFormat;
    }*/
}
