package com.amgrade.harpoonsdk.rest;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Method definitions for authentication
 * Created by Michael Dontsov on 19.06.15.
 */
public interface AuthService {
    @POST("/{v}/auth/token/")
    void getToken(@Path("v") String apiVersion, RestCallback callback);

    @FormUrlEncoded
    @POST("/{v}/auth/token/")
    void getToken(@Path("v") String apiVersion, @Field("code") String authCode, @Field("grant_type") String type,
                  RestCallback callback);
}
