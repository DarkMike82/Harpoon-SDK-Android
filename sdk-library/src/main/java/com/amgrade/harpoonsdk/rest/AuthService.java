package com.amgrade.harpoonsdk.rest;

import java.io.Serializable;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by michael on 19.06.15.
 */
public interface AuthService {
    @POST("/{v}/auth/token/")
    void getToken(@Path("v") String apiVersion, @Body Serializable authData, RestCallback callback);
}
