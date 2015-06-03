package com.amgrade.harpoonsdk.rest;

import com.google.gson.JsonObject;

import retrofit.RetrofitError;

/**
 * Created by michael on 03.06.15.
 */
public interface ApiListener {
    void onSuccess(JsonObject response);
    void onRequestError(String message);
    void onError(RetrofitError error);
}
