package com.amgrade.harpoonsdk.rest;

import com.google.gson.JsonObject;

/**
 * Listener for API requests without model. If no data should be returned, {@link #onSuccess()} will be called.<br/>
 * Otherwise {@link #onSuccess(JsonObject)} will be called.<br/>
 * Created by Michael Dontsov on 03.06.15.
 */
public interface ApiListener {
    void onSuccess();
    void onSuccess(JsonObject response);
//    void onSuccess(JsonArray response);
    void onError(/*String code,*/ String message);
}
