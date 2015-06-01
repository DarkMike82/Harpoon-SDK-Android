package com.amgrade.harpoonsdk.rest;

import com.amgrade.harpoonsdk.rest.model.EventData;
import com.amgrade.harpoonsdk.rest.model.FilterObject;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
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
    @FormUrlEncoded
    @GET("/v1/{user_id}/brand/{format}")
    void getAvailableBrands(@Header("Content-Type")String type, @Header("appid")String appId,
                            @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                            @Path("user_id")Integer id, @Path("format")String format, @Query("token")String token,
                            @Field("limit")Integer limit, @Field("offset")Integer offset,
                            Callback<JsonObject> callback);

    @GET("/v1/{user_id}/brand/{brand_id}/{format}")
    void getBrandDetails(@Header("Content-Type")String type, @Header("appid")String appId,
                         @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                         @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                         @Path("format")String format, @Query("token")String token,
                         Callback<JsonObject> callback);

    //-------------------------------------------------------------------
    //Campaign api methods
    //-------------------------------------------------------------------
    @FormUrlEncoded
    @POST("/v1/{user_id}/brand/{brand_id}/campaign/{format}")
    void createCampaign(@Header("Content-Type")String type, @Header("appid")String appId,
                        @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                        @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                        @Path("format")String format, @Query("token")String token,
                        @Field("name")String name, @Field("description")String decsription,
                        Callback<JsonObject> callback);

    @FormUrlEncoded
    @GET("/v1/{user_id}/brand/{brand_id}/campaign/{format}")
    void getCampaigns(@Header("Content-Type")String type, @Header("appid")String appId,
                      @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                      @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                      @Path("format")String format, @Query("token")String token,
                      @Field("status")String status, @Field("limit")Integer limit, @Field("offset")Integer offset,
                      Callback<JsonObject> callback);

    @FormUrlEncoded
    @GET("/v1/{user_id}/brand/{brand_id}/campaign/{campaign_id}/{format}")
    void updateCampaign(@Header("Content-Type")String type, @Header("appid")String appId,
                        @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                        @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                        @Path("campaign_id")Integer campaign_id, @Path("format")String format, @Query("token")String token,
                        @Field("status")String status, @Field("limit")Integer limit, @Field("offset")Integer offset,
                        Callback<JsonObject> callback);

    //-------------------------------------------------------------------
    //Event api methods
    //-------------------------------------------------------------------
    @GET("/v1/{user_id}/brand/{brand_id}/event/{event_id}/{format}")
    void getEventDetails(@Header("Content-Type")String type, @Header("appid")String appId,
                         @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                         @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                         @Path("event_id")Object event_id, @Path("format")String format,
                         @Query("token")String token, @Query("event_id_type")String event_type_id,
                         Callback<JsonObject> callback);

    @FormUrlEncoded
    @GET("/v1/{user_id}/brand/{brand_id}/event/{format}")
    void getEventList(@Header("Content-Type")String type, @Header("appid")String appId,
                      @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                      @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                      @Path("format")String format, @Query("token")String token,
                      @Field("filter")FilterObject filter,
                      Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/v1/{user_id}/brand/{brand_id}/event/{format}")
    void createEvent(@Header("Content-Type")String type, @Header("appid")String appId,
                     @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                     @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                     @Path("format")String format, @Query("token")String token,
                     @Field("campaign_id")Integer cmpId, @Field("external_id")String extId, @Field("data")EventData data,
                     Callback<JsonObject> callback);

    @FormUrlEncoded
    @PUT("/v1/{user_id}/brand/{brand_id}/event/{event_id}/{format}")
    void updateEvent(@Header("Content-Type")String type, @Header("appid")String appId,
                     @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                     @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                     @Path("event_id")Object event_id, @Path("format")String format,
                     @Query("token")String token, @Query("event_id_type")String event_type_id,
                     @Field("data")EventData data,
                     Callback<JsonObject> callback);

    @POST("/v1/{user_id}/brand/{brand_id}/event/{event_id}/validate/{format}")
    void validateEvent(@Header("Content-Type")String type, @Header("appid")String appId,
                       @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                       @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                       @Path("event_id")Object event_id, @Path("format")String format,
                       @Query("token")String token, @Query("event_id_type")String event_type_id,
                       Callback<JsonObject> callback);

    @POST("/v1/{user_id}/brand/{brand_id}/event/{event_id}/publish/{format}")
    void publishEvent(@Header("Content-Type")String type, @Header("appid")String appId,
                      @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                      @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                      @Path("event_id")Integer event_id, @Path("format")String format,
                      @Query("token")String token, @Query("event_id_type")String event_type_id,
                      Callback<JsonObject> callback);

    @DELETE("/v1/{user_id}/brand/{brand_id}/event/{event_id}/{format}")
    void deleteEvent(@Header("Content-Type")String type, @Header("appid")String appId,
                     @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                     @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                     @Path("event_id")Object event_id, @Path("format")String format,
                     @Query("token")String token, @Query("event_id_type")String event_type_id,
                     Callback<JsonObject> callback);

    //-------------------------------------------------------------------
    //Event-media api methods
    //-------------------------------------------------------------------


}
