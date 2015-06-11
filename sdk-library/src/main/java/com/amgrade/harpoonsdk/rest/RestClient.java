package com.amgrade.harpoonsdk.rest;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.amgrade.harpoonsdk.rest.model.Data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.HashMap;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
//import retrofit.converter.SimpleXMLConverter;

/**
 * Created by michael on 26.05.15.
 */
public class RestClient {
    //api settings
    public static final Integer DEF_LIMIT = 20;
    public static final Integer DEF_OFFSET = 0;

    private static final String BASE_URL = "https://api.harpoonconnect.com";
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private static String sApiVersion = "v1";
    private static String sAccept = "application/json";
    private static String sContentType = "application/json";

    private static RestClient sInstance;

    private static Converter sConverter; //TODO

    private ApiService mApiService;

    /**
     * Singleton to use api client.
     * @return RestClient to work with api methods
     */
    public static RestClient getInstance() {
        if (sInstance==null) {
            sInstance = new RestClient();
        }
        return sInstance;
    }

    public static Converter getConverter() {
        return sConverter;
    }

    private RestClient() {
        if (sConverter == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new JsonTypeAdapterFactory())
                    .setDateFormat(DATE_FORMAT)
                    .create();
            sConverter = new GsonConverter(gson);
        }
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(BASE_URL)
                .setConverter(sConverter)
                .setRequestInterceptor(createInterceptor())
                .build();
        mApiService = restAdapter.create(ApiService.class);
    }

    private ApiService getApiService() {
        return mApiService;
    }

    private RequestInterceptor createInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("appid", HarpoonSDK.getAppId());
                request.addHeader("appsecret", HarpoonSDK.getAppSecret());
                request.addHeader("appbundle", HarpoonSDK.getAppBundle());
//                request.addHeader("device", null/*?*/); //TODO
//                request.addHeader("visitor", null/*?*/); //TODO
                request.addHeader("Accept", sAccept);
                request.addHeader("Content-Type", sContentType);
            }
        };
    }

    //-----------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------
    //Rest API facade
    //-----------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------

    //------Application api methods--------------------------------------

    public void getApplicationInfo(ApiListener listener) {
        getApiService().getApplicationInfo(sApiVersion, new RestCallback(listener, "data", "application", "environment"));
    }

    //------eCommerce api methods----------------------------------------

    public void createCart(ApiListener listener) {
        getApiService().createCart(sApiVersion, HarpoonSDK.getUserId(),
                new RestCallback(listener, "data", "ecommerce", "cart"));
    }

    public void addProducts(String cartId, Serializable[] products, ApiListener listener) {
        HashMap<String, Serializable[]> params = new HashMap();
        params.put("products", products);
        getApiService().addProducts(sApiVersion, HarpoonSDK.getUserId(), cartId, params,
                new RestCallback(listener, "data", "ecommerce", "cart", "product"));
    }

    public void updateProducts(String cartId, Serializable[] products, ApiListener listener) {
        HashMap<String, Serializable[]> params = new HashMap();
        params.put("products", products);
        getApiService().updateProducts(sApiVersion, HarpoonSDK.getUserId(), cartId, params,
                new RestCallback(listener, "data", "ecommerce", "cart", "product"));
    }

    public void removeProducts(String cartId, Serializable[] products, ApiListener listener) {
        HashMap<String, Serializable[]> params = new HashMap();
        params.put("products", products);
        getApiService().removeProducts(sApiVersion, HarpoonSDK.getUserId(), cartId, params,
                new RestCallback(listener, "data", "ecommerce", "cart", "product"));
    }

    public void cartProducts(String cartId, ApiListener listener) {
        getApiService().cartProducts(sApiVersion, HarpoonSDK.getUserId(), cartId,
                new RestCallback(listener, "data", "ecommerce", "cart", "product"));
    }

    public void cartTotals(String cartId, ApiListener listener) {
        getApiService().cartTotals(sApiVersion, HarpoonSDK.getUserId(), cartId,
                new RestCallback(listener, "data", "ecommerce", "cart"));
    }

    public void cartById(String cartId, ApiListener listener) {
        getApiService().cartById(sApiVersion, HarpoonSDK.getUserId(), cartId,
                new RestCallback(listener, "data", "ecommerce", "cart"));
    }

    public void getCheckoutURLWithProducts(Serializable[] products, ApiListener listener) {
        HashMap<String, Object> params = new HashMap();
        params.put("products", products);
        params.put("params", "sdk=true");
        getApiService().getCheckoutURLWithProducts(sApiVersion, HarpoonSDK.getUserId(),
                params, new RestCallback(listener, "data", "ecommerce", "cart"));
    }


    //------User api methods--------------------------------------------

    public void createUser(ApiListener listener) {
        getApiService().createUser(sApiVersion, Data.getUser(), new RestCallback(listener, "data", "user"));
    }

    public void getUser(ApiListener listener) {
        getApiService().getUser(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                new RestCallback(listener, "data", "user"));
    }

    public void getUserById(String userId, ApiListener listener) {
        getApiService().getUserById(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                userId, new RestCallback(listener, "data", "user"));
    }

    /**
     * Get users as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     * @param listener
     */
    public void getUsers(ApiListener listener) {
        getUsers(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getUsers(HashMap<String, Object> filter, ApiListener listener) {
        getUsers(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getUsers(Integer limit, Integer offset, HashMap<String, Object> filter,
                         ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getUsers(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(), params,
                new RestCallback(true, listener, "data", "user"));
    }

    public void updateUser(ApiListener listener) {
        getApiService().updateUser(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                Data.getUser(), new RestCallback(listener, "data", "user"));
    }

    public void login(String email, String pwd, ApiListener listener) {
        HashMap<String, String> params = Data.userParams(false, email, pwd);
        getApiService().login(sApiVersion, "email", params, new RestCallback(listener, "data", "user"));
    }
    public void loginWithFacebook(String fbUserId, String fbUserToken, ApiListener listener) {
        HashMap<String, String> params = Data.userParams(true, fbUserId, fbUserToken);
        getApiService().login(sApiVersion, "facebook", params, new RestCallback(listener, "data", "user"));
    }
    public void loginWithTwitter(String twUserId, String twUserToken, ApiListener listener) {
        HashMap<String, String> params = Data.userParams(true, twUserId, twUserToken);
        getApiService().login(sApiVersion, "twitter", params, new RestCallback(listener, "data", "user"));
    }

    /**
     * Init password reset for user with provided email<br/>
     * No data will be returned, {@link ApiListener#onSuccess()} will be called on success
     * @param email email of user to reset password for
     * @param listener
     */
    public void resetPassword(String email, ApiListener listener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        getApiService().resetPassword(sApiVersion, params, new RestCallback(listener, "data"));
    }

    /**
     * Init password reset for current user<br/>
     * No data will be returned, {@link ApiListener#onSuccess()} will be called on success
     * @param listener
     */
    public void resetPasswordForCurrentUser(ApiListener listener) {
        getApiService().resetPasswordWithAuth(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                new RestCallback(listener, "data"));
    }

    /**
     * Update password for current user<br/>
     * No data will be returned, {@link ApiListener#onSuccess()} will be called on success
     * @param cur_pwd   current password
     * @param new_pwd   new password
     * @param listener
     */
    public void updatePassword(String cur_pwd, String new_pwd, ApiListener listener) {
        HashMap<String, String> params = Data.pwdParams(cur_pwd, new_pwd);
        getApiService().updatePassword(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(listener, "data"));
    }

    /**
     * Connect user to social account
     * @param provider  "facebook" or "twitter"
     * @param userId    user id from social network
     * @param userToken user token from social network
     * @param listener
     */
    public void addConnection(String provider, String userId, String userToken, ApiListener listener) {
        HashMap<String, String> params = Data.userParams(true, userId, userToken);
        getApiService().addConnection(sApiVersion, HarpoonSDK.getUserId(), provider, HarpoonSDK.getUserToken(),
                params, new RestCallback(listener, "data", "user"));
    }

    /**
     * Update user's social connection
     * @param provider  "facebook" or "twitter"
     * @param userToken user token from social network
     * @param listener
     */
    public void updateConnection(String provider, String userToken, ApiListener listener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_token", userToken);
        getApiService().addConnection(sApiVersion, HarpoonSDK.getUserId(), provider, HarpoonSDK.getUserToken(),
                params, new RestCallback(listener, "data", "user"));
    }

    /**
     * Delete connection to social account
     * @param provider "facebook" or "twitter"
     * @param listener
     */
    public void deleteConnection(String provider, ApiListener listener) {
        getApiService().deleteConnection(sApiVersion, HarpoonSDK.getUserId(), provider, HarpoonSDK.getUserToken(),
                new RestCallback(listener, "data", "user"));
    }

    /**
     * Get activities of current user as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     * @param listener
     */
    public void getActivities(ApiListener listener) {
        getActivities(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getActivities(HashMap<String, Object> filter, ApiListener listener) {
        getActivities(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getActivities(Integer limit, Integer offset, HashMap<String, Object> filter,
                              ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getActivities(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "activity"));
    }

    /**
     * Get activities (by user id) as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     * @param userId requested user id
     * @param listener
     */
    public void getActivitiesByUserId(String userId, ApiListener listener) {
        getActivitiesByUserId(userId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getActivitiesByUserId(String userId, HashMap<String, Object> filter, ApiListener listener) {
        getActivitiesByUserId(userId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getActivitiesByUserId(String userId, Integer limit, Integer offset, HashMap<String, Object> filter,
                              ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getActivitiesByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "activity"));
    }

    /**
     * Get followers of current user as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     * @param listener
     */
    public void getFollowers(ApiListener listener) {
        getFollowers(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getFollowers(Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getFollowers(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "follower"));
    }

    /**
     * Get followers of user as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     * @param userId id of the user whose followings will be returned
     * @param listener
     */
    public void getFollowersByUserId(String userId, ApiListener listener) {
        getFollowersByUserId(userId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getFollowersByUserId(String userId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getFollowersByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "follower"));
    }

    /**
     * Follow user with provided id
     * @param userId requested user id
     * @param listener
     * Followings of current user will be returned as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     */
    public void followUser(String userId, ApiListener listener) {
        getApiService().followUser(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                new RestCallback(true, listener, "data", "user", "following"));
    }

    /**
     * Unfollow user with provided id
     * @param userId requested user id
     * @param listener
     * Followings of current user will be returned as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     */
    public void unfollowUser(String userId, ApiListener listener) {
        getApiService().unfollowUser(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                new RestCallback(true, listener, "data", "user", "following"));
    }

    /**
     * Get followings of current user as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     * @param listener
     */
    public void getFollowings(ApiListener listener) {
        getFollowings(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getFollowings(Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getFollowings(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "following"));
    }

    /**
     * Get followings of user as {@link JsonArray} <br/>
     * {@link ApiListener#onSuccess(JsonArray)} will be called on success
     * @param userId
     * @param listener
     */
    public void getFollowingsByUserId(String userId, ApiListener listener) {
        getFollowingsByUserId(userId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getFollowingsByUserId(String userId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getFollowingsByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "following"));
    }

    public void getAttendingEvents(ApiListener listener) {
        getAttendingEvents(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getAttendingEvents(HashMap<String, Object> filter, ApiListener listener) {
        getAttendingEvents(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getAttendingEvents(Integer limit, Integer offset, HashMap<String, Object> filter,
                                   ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getAttendingEvents(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "event"));
    }

    public void getAttendingEventsByUserId(String userId, ApiListener listener) {
        getAttendingEventsByUserId(userId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getAttendingEventsByUserId(String userId, HashMap<String, Object> filter, ApiListener listener) {
        getAttendingEventsByUserId(userId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getAttendingEventsByUserId(String userId, Integer limit, Integer offset, HashMap<String, Object> filter,
                                   ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getAttendingEventsByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "event"));
    }

    public void getAttendedEvents(ApiListener listener) {
        getAttendedEvents(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getAttendedEvents(HashMap<String, Object> filter, ApiListener listener) {
        getAttendedEvents(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getAttendedEvents(Integer limit, Integer offset, HashMap<String, Object> filter,
                                   ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getAttendedEvents(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "event"));
    }

    public void getAttendedEventsByUserId(String userId, ApiListener listener) {
        getAttendedEventsByUserId(userId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getAttendedEventsByUserId(String userId, HashMap<String, Object> filter, ApiListener listener) {
        getAttendingEventsByUserId(userId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getAttendedEventsByUserId(String userId, Integer limit, Integer offset, HashMap<String, Object> filter,
                                   ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getAttendedEventsByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "event"));
    }

    public void getEventTickets(String eventId, ApiListener listener) {
        getEventTickets(eventId, null, null, listener);
    }
    public void getEventTickets(String eventId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getEventTickets(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "event", "ticket"));
    }

    public void getCouponTickets(String couponId, ApiListener listener) {
        getCouponTickets(couponId, null, null, listener);
    }
    public void getCouponTickets(String couponId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getCouponTickets(sApiVersion, HarpoonSDK.getUserId(), couponId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "coupon", "ticket"));
    }

    public void getSimpleDealTickets(String dealId, ApiListener listener) {
        getSimpleDealTickets(dealId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getSimpleDealTickets(String dealId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getSimpleDealTickets(sApiVersion, HarpoonSDK.getUserId(), dealId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "deal", "simple", "ticket"));
    }

    public void getGroupDealTickets(String dealId, ApiListener listener) {
        getGroupDealTickets(dealId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getGroupDealTickets(String dealId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getGroupDealTickets(sApiVersion, HarpoonSDK.getUserId(), dealId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "deal", "group", "ticket"));
    }

    public void getCards(ApiListener listener) {
        getCards(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getCards(Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getCards(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "card"));
    }

    public void getCardInfo(String cardId, ApiListener listener) {
        getApiService().getCardInfo(sApiVersion, HarpoonSDK.getUserId(), cardId, HarpoonSDK.getUserToken(),
                new RestCallback(listener, "data", "user", "card"));
    }

    public void updateCard(String cardId, HashMap<String, String> cardInfo, ApiListener listener) {
        getApiService().updateCard(sApiVersion, HarpoonSDK.getUserId(), cardId, HarpoonSDK.getUserToken(),
                cardInfo, new RestCallback(true, listener, "data", "user", "card"));
    }

    public void addCard(HashMap<String, String> cardInfo, ApiListener listener) {
        getApiService().addCard(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                cardInfo, new RestCallback(listener, "data", "user", "card"));
    }

    public void removeCard(String cardId, ApiListener listener) {
        getApiService().removeCard(sApiVersion, HarpoonSDK.getUserId(), cardId, HarpoonSDK.getUserToken(),
                new RestCallback(true, listener, "data", "user", "card"));
    }

    public void getCurrentWallet(ApiListener listener) {
        getCurrentWallet(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getCurrentWallet(Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getCurrentWallet(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "wallet", "current"));
    }

    public void getWalletHistory(ApiListener listener) {
        getWalletHistory(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getWalletHistory(Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getWalletHistory(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "user", "wallet", "history"));
    }


    //------Brand api methods--------------------------------------------

    public void getBrands(ApiListener listener) {
        getBrands(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getBrands(Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getBrands(sApiVersion, HarpoonSDK.getUserId(),
                params, new RestCallback(listener, "data", "brand"));
    }

    public void getBrandById(Integer brandId, ApiListener listener) {
        getApiService().getBrandById(sApiVersion, HarpoonSDK.getUserId(), brandId,
                new RestCallback(listener, "data", "brand"));
    }

    public void getBrandVenues(Integer brandId, ApiListener listener) {
        getBrandVenues(brandId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getBrandVenues(Integer brandId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getBrandVenues(sApiVersion, HarpoonSDK.getUserId(), brandId,
                params, new RestCallback(listener, "data", "brand", "venue"));
    }

    public void getBrandFeeds(Integer brandId, ApiListener listener) {
        getBrandVenues(brandId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getBrandFeeds(Integer brandId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getBrandFeeds(sApiVersion, HarpoonSDK.getUserId(), brandId,
                params, new RestCallback(listener, "data", "brand", "feed"));
    }

    public void getBrandFollowers(Integer brandId, ApiListener listener) {
        getBrandFollowers(brandId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getBrandFollowers(Integer brandId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getBrandFollowers(sApiVersion, HarpoonSDK.getUserId(), brandId,
                params, new RestCallback(listener, "data", "brand", "follower"));
    }

    public void followBrand(Integer brandId, ApiListener listener) {
        getApiService().followBrand(sApiVersion, HarpoonSDK.getUserId(), brandId,
                new RestCallback(listener, "data", "brand", "follower"));
    }

    public void unfollowBrand(Integer brandId, ApiListener listener) {
        getApiService().unfollowBrand(sApiVersion, HarpoonSDK.getUserId(), brandId,
                new RestCallback(listener, "data", "brand", "follower"));
    }

    public void getBrandProducts(Integer brandId, ApiListener listener) {
        getBrandProducts(brandId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getBrandProducts(Integer brandId, HashMap<String, Object> filter, ApiListener listener) {
        getBrandProducts(brandId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getBrandProducts(Integer brandId, Integer limit, Integer offset,
                                 HashMap<String, Object> filter, ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getBrandProducts(sApiVersion, HarpoonSDK.getUserId(), brandId,
                params, new RestCallback(listener, "data", "brand", "product"));
    }

    public void getBrandEvents(Integer brandId, ApiListener listener) {
        getBrandEvents(brandId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getBrandEvents(Integer brandId, HashMap<String, Object> filter, ApiListener listener) {
        getBrandEvents(brandId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getBrandEvents(Integer brandId, Integer limit, Integer offset,
                               HashMap<String, Object> filter, ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getBrandEvents(sApiVersion, HarpoonSDK.getUserId(), brandId,
                params, new RestCallback(listener, "data", "brand", "event"));
    }

    public void getBrandOffers(Integer brandId, ApiListener listener) {
        getBrandOffers(brandId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getBrandOffers(Integer brandId, HashMap<String, Object> filter, ApiListener listener) {
        getBrandOffers(brandId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getBrandOffers(Integer brandId, Integer limit, Integer offset,
                               HashMap<String, Object> filter, ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getBrandOffers(sApiVersion, HarpoonSDK.getUserId(), brandId,
                params, new RestCallback(listener, "data", "brand", "event"));
    }

    //------Product api methods--------------------------------------------

    /*public void getProducts(ApiListener listener) {
        getProducts(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getProducts(HashMap<String, Object> filter, ApiListener listener) {
        getProducts(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getProducts(Integer limit, Integer offset,
                            HashMap<String, Object> filter, ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getProducts(sApiVersion, HarpoonSDK.getUserId(),
                params, new RestCallback(listener, "data", "product"));
    }

    public void getProductById(String productId, ApiListener listener) {
        getProductById(productId, "ecommerce", listener);
    }
    public void getProductById(String productId, String id_type, ApiListener listener) {
        getApiService().getProductById(sApiVersion, HarpoonSDK.getUserId(), productId, id_type,
                new RestCallback(listener, "data", "product"));
    }*/

    //------Event api methods--------------------------------------------

    public void getEvents(ApiListener listener) {
        getEvents(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getEvents(HashMap<String, Object> filter, ApiListener listener) {
        getEvents(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getEvents(Integer limit, Integer offset,
                          HashMap<String, Object> filter, ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getEvents(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "event"));
    }

    public void getEventById(String eventId, ApiListener listener) {
        getApiService().getEventById(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                new RestCallback(listener, "data", "event"));
    }

    public void getEventAttendees(String eventId, ApiListener listener) {
        getEventAttendees(eventId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getEventAttendees(String eventId, Integer limit, Integer offset,
                                  ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getEventAttendees(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "event", "attendee"));
    }

    public void getEventVenues(String eventId, ApiListener listener) {
        getEventVenues(eventId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getEventVenues(String eventId, Integer limit, Integer offset,
                               ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getEventVenues(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "event", "venue"));
    }

    public void checkoutEvent(String[] ids, Integer[] quantities, String eventId,
                              ApiListener listener) {
        HashMap<String, Object> params = Data.checkoutList(ids, quantities);
        getApiService().checkoutEvent(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                params, new RestCallback(listener, "data", "event", "checkout"));
    }

    //------Offer api methods--------------------------------------------

    public void getOffers(ApiListener listener) {
        getOffers(null, null, null, listener);
    }
    public void getOffers(HashMap<String, Object> filter, ApiListener listener) {
        getOffers(filter, null, null, listener);
    }
    public void getOffers(HashMap<String, Object> filter, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getOffers(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "offer"));
    }

    /*public void getOfferById(String offerId, ApiListener listener) {
        getEventById(offerId, null, listener);
    }
    public void getOfferById(String offerId, String id_type, ApiListener listener) {
        getApiService().getOfferById(sApiVersion, HarpoonSDK.getUserId(), offerId, id_type,
                new RestCallback(listener, "data", "offer"));
    }

    public void getOfferCustomers(String offerId, ApiListener listener) {
        getOfferCustomers(offerId, null, null, null, listener);
    }
    public void getOfferCustomers(String offerId, Integer limit, Integer offset, ApiListener listener) {
        getOfferCustomers(offerId, null, limit, offset, listener);
    }
    public void getOfferCustomers(String offerId, String id_type,
                                Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getOfferCustomers(sApiVersion, HarpoonSDK.getUserId(), offerId, id_type,
                params, new RestCallback(listener, "data", "offer"));
    }

    public void getOfferVenues(String offerId, ApiListener listener) {
        getOfferVenues(offerId, null, null, null, listener);
    }
    public void getOfferVenues(String offerId, Integer limit, Integer offset, ApiListener listener) {
        getOfferVenues(offerId, null, limit, offset, listener);
    }
    public void getOfferVenues(String offerId, String id_type,
                                Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getOfferVenues(sApiVersion, HarpoonSDK.getUserId(), offerId, id_type,
                params, new RestCallback(listener, "data", "offer", "venue"));
    }

    public void getOfferCoupons(String offerId, ApiListener listener) {
        getOfferCoupons(offerId, null, listener);
    }

    public void getOfferCoupons(String offerId, String id_type, ApiListener listener) {
        getApiService().getOfferCoupons(sApiVersion, HarpoonSDK.getUserId(), offerId, id_type,
                new RestCallback(listener, "data", "offer", "coupon"));
    }*/

    //------Coupon api methods--------------------------------------------

    public void getCoupons(ApiListener listener) {
        getCoupons(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getCoupons(HashMap<String, Object> filter, ApiListener listener) {
        getCoupons(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getCoupons(Integer limit, Integer offset, HashMap<String, Object> filter, ApiListener listener) {
        HashMap<String, Object> params = Data.listFilterParams(limit, offset, filter);
        getApiService().getCoupons(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "coupon"));
    }

    public void getCouponInfo(String couponId, ApiListener listener) {
        getApiService().getCouponInfo(sApiVersion, HarpoonSDK.getUserId(), couponId, HarpoonSDK.getUserToken(),
                new RestCallback(listener, "data", "coupon"));
    }

    public void getCouponVenues(String couponId, ApiListener listener) {
        getCouponVenues(couponId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getCouponVenues(String couponId, Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getCouponVenues(sApiVersion, HarpoonSDK.getUserId(), couponId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "coupon", "venue"));
    }

    public void couponCheckout(String couponId, Integer quantity, ApiListener listener) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("qty", quantity);
        getApiService().couponCheckout(sApiVersion, HarpoonSDK.getUserId(), couponId, HarpoonSDK.getUserToken(),
                params, new RestCallback(true, listener, "data", "coupon", "checkout"));
    }

}
