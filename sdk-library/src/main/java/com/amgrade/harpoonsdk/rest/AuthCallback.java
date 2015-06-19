package com.amgrade.harpoonsdk.rest;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by michael on 19.06.15.
 */
public class AuthCallback extends RestCallback {

    public AuthCallback(ApiListener listener) {
        super(listener, "data", "auth");
    }

    @Override
    public void success(JsonObject jsonObject, Response response) {
        JsonElement responseData = extractData(mKeys, jsonObject);
        String token = responseData.getAsJsonObject().get("token").getAsString();
        String token_type = responseData.getAsJsonObject().get("type").getAsString();
        HarpoonSDK.setToken(token_type, token);
        mListener.onSuccess(jsonObject);
    }

    @Override
    public void failure(RetrofitError error) {
        super.failure(error);
    }
}
