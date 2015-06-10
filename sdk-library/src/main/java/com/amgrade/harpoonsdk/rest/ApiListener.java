package com.amgrade.harpoonsdk.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by michael on 03.06.15.
 */
public interface ApiListener {
    void onSuccess();
    void onSuccess(JsonObject response);
    void onSuccess(JsonArray response);
    void onError(String code, String message);
//    void onError(RetrofitError error);
}
