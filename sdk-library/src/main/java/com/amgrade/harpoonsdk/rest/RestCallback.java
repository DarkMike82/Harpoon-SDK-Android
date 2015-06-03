package com.amgrade.harpoonsdk.rest;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.amgrade.harpoonsdk.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by michael on 03.06.15.
 */
class RestCallback implements Callback<JsonObject> {
    private static final String OK = "success";

    private ApiListener mListener;
    private String[] mKeys;

    public RestCallback(ApiListener listener, String... keys) {
        mListener = listener;
        mKeys = keys;
    }

    @Override
    public void success(JsonObject jsonObject, Response response) {
        if (jsonObject.has("status")) {
            String status = jsonObject.get("status").getAsString();
            if (OK.contentEquals(status)) {
                JsonObject responseData = extractData(mKeys, jsonObject);
                mListener.onSuccess(responseData);
            } else {
                mListener.onRequestError(getErrorMessage(jsonObject));
            }
        } else {
            mListener.onRequestError(HarpoonSDK.getContext().getString(R.string.wrong_response)+"\n"+jsonObject.getAsString());
        }
    }

    @Override
    public void failure(RetrofitError error) {
        mListener.onError(error);
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

    private String getErrorMessage(JsonObject response) {
        String message = null;
        JsonObject data = response.get("data").getAsJsonObject();
        message = data.get("message").getAsString();
        return message;
    }
}
