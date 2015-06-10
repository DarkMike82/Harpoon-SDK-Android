package com.amgrade.harpoonsdk.rest;

import java.io.Serializable;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
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
    @GET("/{v}/application/environment/")
    void getApplicationSettings(@Path("v") String apiVersion, RestCallback callback);

    //-------------------------------------------------------------------
    //eCommerce api methods
    //-------------------------------------------------------------------

    @POST("/{v}/user/{userId}/ecommerce/cart")
    void createCart(@Path("v") String apiVersion, @Path("userId") String user_id, RestCallback callback);

    @POST("/{v}/user/{userId}/ecommerce/cart/{cartId}/product")
    void addProducts(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("cartId") String cart_id,
                     @Body Serializable params, RestCallback callback);

    @PUT("/{v}/user/{userId}/ecommerce/cart/{cartId}/product")
    void updateProducts(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("cartId") String cart_id,
                        @Body Serializable params, RestCallback callback);


    @DELETE("/{v}/user/{userId}/ecommerce/cart/{cartId}/product")
    void removeProducts(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("cartId") String cart_id,
                        @Body Serializable params, RestCallback callback);


    @GET("/{v}/user/{userId}/ecommerce/cart/{cartId}/product")
    void cartProducts(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("cartId") String cart_id,
                      RestCallback callback);

    @GET("/{v}/user/{userId}/ecommerce/cart/{cartId}/totals")
    void cartTotals(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("cartId") String cart_id,
                    RestCallback callback);

    @GET("/{v}/user/{userId}/ecommerce/cart/{cartId}")
    void cartById(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("cartId") String cart_id,
                  RestCallback callback);

    @GET("/{v}/user/{userId}/ecommerce/cart/checkout_url")
    void getCheckoutURLWithProducts(@Path("v") String apiVersion, @Path("userId") String user_id,
                                    @Body Serializable params, RestCallback callback);

    //-------------------------------------------------------------------
    //Brand api methods
    //-------------------------------------------------------------------
    @GET("/{v}/user/{userId}/brand")
    void getBrands(@Path("v") String apiVersion, @Path("userId") String user_id,
                   @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}")
    void getBrandById(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                      RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/business")
    void getBrandVenues(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                        @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/feed")
    void getBrandFeeds(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                       @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/follower")
    void getBrandFollowers(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                           @Body Serializable params, RestCallback callback);

    @PUT("{v}/user/{userId}/brand/{brandId}/follower")
    void followBrand(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                     RestCallback callback);

    @DELETE("{v}/user/{userId}/brand/{brandId}/follower")
    void unfollowBrand(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                       RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/product")
    void getBrandProducts(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                          @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/event")
    void getBrandEvents(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                        @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/brand/{brandId}/offer")
    void getBrandOffers(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("brandId") Integer brand_id,
                        @Body Serializable params, RestCallback callback);

    //-------------------------------------------------------------------
    //Product api methods
    //-------------------------------------------------------------------
    @GET("/{v}/user/{userId}/product")
    void getProducts(@Path("v") String apiVersion, @Path("userId") String user_id,
                     @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/product/{productId}")
    void getProductById(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("productId") String product_id,
                        @Query("id_type") String id_type, RestCallback callback);

    //-------------------------------------------------------------------
    //Event api methods
    //-------------------------------------------------------------------
    @GET("/{v}/user/{userId}/event")
    void getEvents(@Path("v") String apiVersion, @Path("userId") String user_id,
                   @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/event/{eventId}")
    void getEventById(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("eventId") String event_id,
                      @Query("id_type") String id_type, RestCallback callback);

    @GET("/{v}/user/{userId}/event/{eventId}/customer")
    void getEventCustomers(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("eventId") String event_id,
                           @Query("id_type") String id_type, @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/event/{eventId}/venue")
    void getEventVenues(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("eventId") String event_id,
                        @Query("id_type") String id_type, @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/event/{eventId}/ticket")
    void getEventTickets(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("eventId") String event_id,
                         @Query("id_type") String id_type, @Body Serializable params, RestCallback callback);

    //-------------------------------------------------------------------
    //Offer api methods
    //-------------------------------------------------------------------
    @GET("/{v}/user/{userId}/offer")
    void getOffers(@Path("v") String apiVersion, @Path("userId") String user_id,
                   @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/offer/{offerId}")
    void getOfferById(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("offerId") String offer_id,
                      @Query("id_type") String id_type, RestCallback callback);

    @GET("/{v}/user/{userId}/offer/{offerId}/customer")
    void getOfferCustomers(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("offerId") String offer_id,
                           @Query("id_type") String id_type, @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/offer/{offerId}/venue")
    void getOfferVenues(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("offerId") String offer_id,
                        @Query("id_type") String id_type, @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/{userId}/offer/{offerId}/coupon")
    void getOfferCoupons(@Path("v") String apiVersion, @Path("userId") String user_id, @Path("offerId") String offer_id,
                         @Query("id_type") String id_type, RestCallback callback);

    //-------------------------------------------------------------------
    //User api methods
    //-------------------------------------------------------------------
    @POST("/{v}/user/")
    void createUser(@Path("v") String apiVersion, @Body Serializable userData, RestCallback callback);


    @GET("/{v}/{user.id}/")
    void getUser(@Path("v") String apiVersion, @Path("user.id") String userId,
                 @Query("token") String token, RestCallback callback);

    @GET("/{v}/{user.id}/user/{id}/")
    void getUserById(@Path("v") String apiVersion, @Path("user.id") String currentUserId, @Path("id") String userId,
                     @Query("token") String token, RestCallback callback);

    @GET("/{v}/{user.id}/user/")
    void getUsers(@Path("v") String apiVersion, @Path("user.id") String userId, @Query("token") String token,
                  @Body Serializable params, RestCallback callback);

    @PUT("/{v}/{user.id}/user/")
    void updateUser(@Path("v") String apiVersion, @Path("user.id") String userId, @Query("token") String token,
                    @Body Serializable userData, RestCallback callback);

    @POST("/{v}/user/login/{provider}/")
    void login(@Path("v") String apiVersion, @Path("provider") String provider,
               @Body Serializable params, RestCallback callback);

    @GET("/{v}/user/password/")
    void resetPassword(@Path("v") String apiVersion, @Body Serializable params, RestCallback callback);

    @GET("/{v}/{user.id}/user/password/")
    void resetPasswordWithAuth(@Path("v") String apiVersion, @Path("user.id") String userId, @Query("token") String token,
                               RestCallback callback);

    @PUT("/{v}/{user.id}/user/password/")
    void updatePassword(@Path("v") String apiVersion, @Path("user.id") String userId, @Query("token") String token,
                        @Body Serializable params, RestCallback callback);

    @POST("/{v}/{user.id}/user/connection/{provider}/")
    void addConnection(@Path("v") String apiVersion, @Path("user.id") String userId, @Path("provider") String provider,
                       @Query("token") String token, @Body Serializable params, RestCallback callback);

    @PUT("/{v}/{user.id}/user/connection/{provider}/")
    void updateConnection(@Path("v") String apiVersion, @Path("user.id") String userId, @Path("provider") String provider,
                          @Query("token") String token, @Body Serializable params, RestCallback callback);

    @DELETE("/{v}/{user.id}/user/connection/{provider}/")
    void deleteConnection(@Path("v") String apiVersion, @Path("user.id") String userId, @Path("provider") String provider,
                          @Query("token") String token, RestCallback callback);

    @GET("/{v}/{user.id}/user/activity/")
    void getActivities(@Path("v") String apiVersion, @Path("user.id") String userId, @Query("token") String token,
                       @Body Serializable params, RestCallback callback);

    @GET("/{v}/{user.id}/user/{id}/activity/")
    void getActivitiesByUserId(@Path("v") String apiVersion, @Path("user.id") String userId, @Path("id") String id,
                       @Query("token") String token, @Body Serializable params, RestCallback callback);

    @GET("/{v}/{user.id}/user/follower/")
    void getFollowers(@Path("v") String apiVersion, @Path("user.id") String userId, @Query("token") String token,
                      @Body Serializable params, RestCallback callback);

    @GET("/{v}/{user.id}/user/{id}/follower/")
    void getFollowersByUserId(@Path("v") String apiVersion, @Path("user.id") String userId, @Path("id") String id,
                      @Query("token") String token, @Body Serializable params, RestCallback callback);

    @PUT("/{v}/{user.id}/user/{id}/following/")
    void followUser(@Path("v") String apiVersion, @Path("user.id") String userId, @Path("id") String id,
                    @Query("token") String token, RestCallback callback);

    @DELETE("/{v}/{user.id}/user/{id}/follower/")
    void unfollowUser(@Path("v") String apiVersion, @Path("user.id") String userId, @Path("id") String id,
                    @Query("token") String token, RestCallback callback);

    @GET("/{v}/{user.id}/user/following/")
    void getFollowings(@Path("v") String apiVersion, @Path("user.id") String userId, @Query("token") String token,
                      @Body Serializable params, RestCallback callback);

    @GET("/{v}/{user.id}/user/{id}/following/")
    void getFollowingsByUserId(@Path("v") String apiVersion, @Path("user.id") String userId, @Path("id") String id,
                              @Query("token") String token, @Body Serializable params, RestCallback callback);


}
