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

    //-------------------------------------------------------------------
    //Brand api methods
    //-------------------------------------------------------------------
    @GET("/{version}/{user_id}/brand/{format}")
    void getAvailableBrands(@Header("Content-Type")String type, @Header("appid")String appId,
                            @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                            @Path("version")String version, @Path("user_id")Integer id, @Path("format")String format,
                            @Query("token")String token, @Body ListParams params);

    @GET("/{version}/{user_id}/brand/{brand_id}/{format}")
    void getBrandDetails(@Header("Content-Type")String type, @Header("appid")String appId,
                         @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                         @Path("version")String version, @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                         @Path("format")String format, @Query("token")String token);

    //-------------------------------------------------------------------
    //Campaign api methods
    //-------------------------------------------------------------------
    @POST("/{version}/{user_id}/brand/{brand_id}/campaign/{format}")
    void createCampaign(@Header("Content-Type")String type, @Header("appid")String appId,
                        @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                        @Path("version")String version, @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                         @Path("format")String format, @Query("token")String token);

    @GET("/{version}/{user_id}/brand/{brand_id}/campaign/{format}")
    void getCampaigns(@Header("Content-Type")String type, @Header("appid")String appId,
                      @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                      @Path("version")String version, @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                      @Path("format")String format, @Query("token")String token);

    @GET("/{version}/{user_id}/brand/{brand_id}/campaign/{campaign_id}/{format}")
    void updateCampaign(@Header("Content-Type")String type, @Header("appid")String appId,
                        @Header("appsecret")String appSecret, @Header("appbundle")String appBundle,
                        @Path("version")String version, @Path("user_id")Integer user_id, @Path("brand_id")Integer brand_id,
                        @Path("campaign_id")Integer campaign_id, @Path("format")String format, @Query("token")String token);

    //-------------------------------------------------------------------
    //Event api methods
    //-------------------------------------------------------------------


}
