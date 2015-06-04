package com.amgrade.harpoonsdk.rest;

import java.io.Serializable;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by michael on 27.05.15.
 */
interface ApiService {
    //-------------------------------------------------------------------
    //Application api methods
    //-------------------------------------------------------------------
    @GET("/{v}/application/setting")
    void getApplicationSettings(@Path("")String apiVersion, RestCallback callback);

    //-------------------------------------------------------------------
    //Brand api methods
    //-------------------------------------------------------------------
    @GET("/{v}/user/{userId}/brand")
    void getBrands(@Path("v")String apiVersion, @Path("userId")Integer user_id,
                   @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}")
    void getBrandById(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                      RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/business")
    void getBrandVenues(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                        @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/feed")
    void getBrandFeeds(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                       @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/follower")
    void getBrandFollowers(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                          @Body Serializable params, RestCallback callback);

    @PUT("{v}/user/{userId}/brand/{brandId}/follower")
    void followBrand(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                     RestCallback callback);

    @DELETE("{v}/user/{userId}/brand/{brandId}/follower")
    void unfollowBrand(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                     RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/product")
    void getBrandProducts(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                          @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/event")
    void getBrandEvents(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                          @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/offer")
    void getBrandOffers(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("brandId")Integer brand_id,
                          @Body Serializable params, RestCallback callback);

    //-------------------------------------------------------------------
    //Product api methods
    //-------------------------------------------------------------------
    @GET("/{v}/user/{userId}/product")
    void getProducts(@Path("v")String apiVersion, @Path("userId")Integer user_id,
                          @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/product/{productId}")
    void getProductById(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("productId")String product_id,
                        @Query("id_type")String id_type, RestCallback callback);

    //-------------------------------------------------------------------
    //Event api methods
    //-------------------------------------------------------------------
    @GET("/{v}/user/{userId}/event")
    void getEvents(@Path("v")String apiVersion, @Path("userId")Integer user_id,
                   @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/event/{eventId}")
    void getEventById(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("eventId")String event_id,
                      @Query("id_type")String id_type, RestCallback callback);

    @GET("/{v}/user/{userId}/event/{eventId}/customer")
    void getEventCustomers(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("eventId")String event_id,
                           @Query("id_type")String id_type, @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/event/{eventId}/venue")
    void getEventVenues(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("eventId")String event_id,
                           @Query("id_type")String id_type, @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/event/{eventId}/ticket")
    void getEventTickets(@Path("v")String apiVersion, @Path("userId")Integer user_id, @Path("eventId")String event_id,
                           @Query("id_type")String id_type, @Body Serializable params, RestCallback callback);

    //-------------------------------------------------------------------
    //User api methods
    //-------------------------------------------------------------------
//    @GET("/{v}/user")
//    void createUser();


}
