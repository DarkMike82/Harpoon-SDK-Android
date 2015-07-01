package com.amgrade.harpoonsdk.rest;

import android.util.Log;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.amgrade.harpoonsdk.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.ConversionException;

/**
 * Generic callback for Harpoon server requests.<br/>
 * Created by Michael Dontsov on 22.06.15.
 * @param <T> type of data to receive from server.
 */
class RestCallback1<T extends Serializable> implements Callback<JsonObject> {
    private static final String OK = "success";

    public static final int SET_USER_ID = 1;

    protected ApiListener1<T> mListener;
    protected String[] mKeys;
    private Class mDataClass;
    private int mSuccessAction = -1;
    private boolean isArrayResponse = false;

    public RestCallback1(Class c, ApiListener1<T> listener, String... keys) {
        this(c, false, listener, keys);
    }

    public RestCallback1(Class c, boolean receiveArray, ApiListener1<T> listener, String... keys) {
        mDataClass = c;
        mListener = listener;
        mKeys = keys;
        isArrayResponse = receiveArray;
    }

    @Override
    public void success(JsonObject jsonObject, Response response) {
        //since 09.06.2015 API settings changed, so only "success" responses will be processed here
        JsonElement responseData = extractData(mKeys, jsonObject);
        performAction(mSuccessAction, responseData.getAsJsonObject());
//        if (responseData==null) {
//            mListener.onSuccess();
//        } else {
            if (isArrayResponse) {
                JsonArray array = responseData.getAsJsonArray();
                ArrayList<T> parsedArray = new ArrayList<>();
                for (int i=0;i<array.size();i++) {
                    T item = parseJson(new JsonTypedInput(array.get(i)));
                    if (item!=null) {
                        parsedArray.add(item);
                    }
                }
                mListener.onSuccess(parsedArray);
            } else {
                T parsedObject = parseJson(new JsonTypedInput(responseData));
                mListener.onSuccess(parsedObject);
            }
//        }
    }

    @Override
    public void failure(RetrofitError error) {
        Response response = error.getResponse();
        String responseMsg = null;
        //if request failed and either no response received or internal error occurred
        if (response == null) {
            switch (error.getKind()) {
                case NETWORK:
                    responseMsg = getString(R.string.error_net);
                    break;
                case CONVERSION:
                    responseMsg = getString(R.string.error_conv);
                    break;
                case UNEXPECTED:
                    responseMsg = getString(R.string.error_unknown);
                    break;
                default:
                    break;

            }
            mListener.onError(/*null,*/ responseMsg);
            return;
        }
        //if response received, but is not valid JSON
        JsonObject body = null;
        try {
            body = (JsonObject)error.getBodyAs(JsonObject.class);
        } catch (Exception e) {
            Log.e(ConversionException.class.getName(), e.getLocalizedMessage());
//            responseMsg = getString(R.string.error_conv);
            mListener.onError(response.getReason()+"\nError code: "+response.getStatus());
            return;
        }
        //if response is valid JSON
        String[] errorInfo = getErrorInfo(body);
        mListener.onError(errorInfo[1]+"\nError code: "+errorInfo[0]);
    }

    public void setOnSuccessAction(int action) {
        mSuccessAction = action;
    }

    //---------------------------------------------------------------------
    //internal methods
    //---------------------------------------------------------------------
    protected JsonElement extractData(String[] keys, JsonObject container) {
        if (keys==null || keys.length==0) {
            return null;
        }
        JsonElement data = container.get(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            data = data.getAsJsonObject().get(keys[i]);
        }
        return data;
    }

    private String[] getErrorInfo(JsonObject responseBody) {
        String[] errorInfo = new String[2];
        JsonObject error = responseBody.get("data").getAsJsonObject().
                                        get("error").getAsJsonObject();
        errorInfo[0] = error.get("code").getAsString();
        errorInfo[1] = error.get("message").getAsString();
        return errorInfo;
    }

    private void performAction(int action, JsonObject data) {
        switch (action) {
            case SET_USER_ID:
                HarpoonSDK.setUser(data.get("id").getAsString(),
                        data.get("authorization_code").getAsString());
                break;
            default:
                break;
        }
    }

    private String getString(int resId) {
        if (HarpoonSDK.getContext()==null) {
            throw new NullPointerException("SDK context is not initialised!");
        }
        return HarpoonSDK.getContext().getString(resId);
    }

    private T parseJson(JsonTypedInput input) {
        T parsedObject = null;
        try {
            parsedObject = (T) RestClient.getConverter().fromBody(input, mDataClass);
        } catch (ConversionException e) {
            Log.e("Response conversion", e.getLocalizedMessage());
        }
        return parsedObject;
    }
}
