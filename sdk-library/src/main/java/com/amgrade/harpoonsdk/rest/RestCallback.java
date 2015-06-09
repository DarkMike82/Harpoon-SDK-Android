package com.amgrade.harpoonsdk.rest;

import android.util.Log;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.amgrade.harpoonsdk.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.ConversionException;

/**
 * Created by michael on 03.06.15.
 */
class RestCallback implements Callback<JsonObject> {
    private static final String OK = "success";

    public static final int SET_USER = 1;

    private ApiListener mListener;
    private String[] mKeys;
    private int mSuccessAction = -1;

    public RestCallback(ApiListener listener, String... keys) {
        mListener = listener;
        mKeys = keys;
    }

    @Override
    public void success(JsonObject jsonObject, Response response) {
        //since 09.06.2015 API rules mean that only "success" responses will be processed here
//        if (jsonObject.has("status")) {
//            String status = jsonObject.get("status").getAsString();
//            if (OK.contentEquals(status)) {
                JsonObject responseData = extractData(mKeys, jsonObject);
                performAction(mSuccessAction, responseData);
                mListener.onSuccess(responseData);
//            } else {
//                mListener.onRequestError(getErrorMessage(jsonObject));
//            }
//        } else {
//            mListener.onRequestError(HarpoonSDK.getContext().getString(R.string.wrong_response)+"\n"+jsonObject.getAsString());
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
            mListener.onError(null, responseMsg);
            return;
        }
        //if response received, but is not valid JSON
        JsonObject body = null;
        try {
            body = (JsonObject)error.getBodyAs(JsonObject.class);
        } catch (Exception e) {
            Log.e(ConversionException.class.getName(), e.getLocalizedMessage());
            responseMsg = getString(R.string.error_conv);
            mListener.onError(null, responseMsg);
            return;
        }
        //if response is valid JSON
        String[] errorInfo = getErrorInfo(body);
        mListener.onError(errorInfo[0], errorInfo[1]);
    }

    public void setOnSuccessAction(int action) {
        mSuccessAction = action;
    }

    //---------------------------------------------------------------------
    //internal methods
    //---------------------------------------------------------------------
    private JsonObject extractData(String[] keys, JsonObject container) {
        JsonObject data = null;
        if (keys!=null) {
            for (int i = 0; i < keys.length; i++) {
                data = container.get(keys[i]).getAsJsonObject();
            }
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
            case SET_USER:
                HarpoonSDK.setUser(data.get("id").getAsString(),
                        data.get("token").getAsString());
                break;
            default:
                break;
        }
    }

    private String getString(int resId) {
        return HarpoonSDK.getContext().getString(resId);
    }
}
