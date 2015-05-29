package com.amgrade.harpoonsdk.rest;

import com.amgrade.harpoonsdk.rest.model.ListParams;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by michael on 27.05.15.
 */
public interface ApiService {

    //TODO add "Callback" params, implement response classes for methods!!!!!!!!!!!!!!!!

    //You can use "{version}" in path (instead of "v1") and "@Path("version")String version" in method params
    //if you want to let user choose an API version to work with

    //-------------------------------------------------------------------
    //Brand api methods
    //-------------------------------------------------------------------
    @GET("/v1/{user_id}/brand/{format}")
    void getAvailableBrands(@Header("Content-Type")String type, @Header("appid")String appId,
                            @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                            @Path("user_id")Integer id, @Path("format")String format,
                            @Query("token")String token, @Body ListParams params);

    @GET("/v1/{user_id}/brand/{brand_id}/{format}")
    void getBrandDetails(@Header("Content-Type")String type, @Header("appid")String appId,
                         @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                         @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                         @Path("format")String format, @Query("token")String token);

    //-------------------------------------------------------------------
    //Campaign api methods
    //-------------------------------------------------------------------
    @POST("/v1/{user_id}/brand/{brand_id}/campaign/{format}")
    void createCampaign(@Header("Content-Type")String type, @Header("appid")String appId,
                        @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                        @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                        @Path("format")String format, @Query("token")String token);

    @GET("/v1/{user_id}/brand/{brand_id}/campaign/{format}")
    void getCampaigns(@Header("Content-Type")String type, @Header("appid")String appId,
                      @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                      @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                      @Path("format")String format, @Query("token")String token);

    @GET("/v1/{user_id}/brand/{brand_id}/campaign/{campaign_id}/{format}")
    void updateCampaign(@Header("Content-Type")String type, @Header("appid")String appId,
                        @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                        @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                        @Path("campaign_id")Integer campaign_id, @Path("format")String format, @Query("token")String token);

    //-------------------------------------------------------------------
    //Event api methods
    //-------------------------------------------------------------------
    @GET("/v1/{user_id}/brand/{brand_id}/event/{event_id}/{format}")
    void getEventDetails(@Header("Content-Type")String type, @Header("appid")String appId,
                         @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                         @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                         @Path("event_id")Integer event_id, @Path("event_id_type")String event_type_id,
                         @Path("format")String format, @Query("token")String token);

    @GET("/v1/{user_id}/brand/{brand_id}/event/{format}")
    void getEventList(@Header("Content-Type")String type, @Header("appid")String appId,
                      @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                      @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                      @Path("format")String format, @Query("token")String token,
                      @Body("filter"));

//    @GET("")

}
