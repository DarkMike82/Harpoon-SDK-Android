package com.amgrade.harpoonsdk.rest;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Callback for authentication
 * Created by Michael Dontsov on 19.06.15.
 */
public class AuthCallback extends RestCallback {

    public AuthCallback(ApiListener listener) {
        super(listener, "data", "auth");
    }

    @Override
    public void success(JsonObject jsonObject, Response response) {
//        Log.d("HARPOONSDK", "response: " + jsonObject.toString());
        JsonElement responseData = extractData(mKeys, jsonObject);
        String token = responseData.getAsJsonObject().get("token").getAsString();
        String token_type = responseData.getAsJsonObject().get("type").getAsString();
        Long token_expires = System.currentTimeMillis() +
                responseData.getAsJsonObject().get("expires_in").getAsLong()*1000;
        HarpoonSDK.setToken(token_type, token, token_expires);
        mListener.onSuccess();
    }

    @Override
    public void failure(RetrofitError error) {
        super.failure(error);
    }
}
