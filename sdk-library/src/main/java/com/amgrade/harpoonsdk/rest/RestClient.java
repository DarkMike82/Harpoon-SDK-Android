package com.amgrade.harpoonsdk.rest;

import android.util.Base64;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.amgrade.harpoonsdk.rest.model.Checkout;
import com.amgrade.harpoonsdk.rest.model.Coords;
import com.amgrade.harpoonsdk.rest.model.Coupon;
import com.amgrade.harpoonsdk.rest.model.CouponCheckout;
import com.amgrade.harpoonsdk.rest.model.ParamsHelper;
import com.amgrade.harpoonsdk.rest.model.Venue;
import com.amgrade.harpoonsdk.rest.model.brand.Brand;
import com.amgrade.harpoonsdk.rest.model.brand.BrandFeed;
import com.amgrade.harpoonsdk.rest.model.brand.BrandFollower;
import com.amgrade.harpoonsdk.rest.model.deal.GroupDeal;
import com.amgrade.harpoonsdk.rest.model.deal.SimpleDeal;
import com.amgrade.harpoonsdk.rest.model.event.Event;
import com.amgrade.harpoonsdk.rest.model.event.EventAttendee;
import com.amgrade.harpoonsdk.rest.model.event.EventTicket;
import com.amgrade.harpoonsdk.rest.model.user.User;
import com.amgrade.harpoonsdk.rest.model.user.UserAction;
import com.amgrade.harpoonsdk.rest.model.user.UserCard;
import com.amgrade.harpoonsdk.rest.model.user.UserCouponTicket;
import com.amgrade.harpoonsdk.rest.model.user.UserDealTicket;
import com.amgrade.harpoonsdk.rest.model.user.UserEventTicket;
import com.amgrade.harpoonsdk.rest.model.user.UserFollower;
import com.amgrade.harpoonsdk.rest.model.user.UserFollowing;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonObject;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
//import retrofit.converter.SimpleXMLConverter;

/**
 * Created by Michael Dontsov on 26.05.15.
 */
public class RestClient {
    //api settings
    public static final Integer DEF_LIMIT = 20;
    public static final Integer DEF_OFFSET = 0;

    private static final String BASE_URL = "https://api.harpoonconnect.com";
    private static final String BASE_ALPHA_URL = "http://alpha.api.harpoonconnect.com";
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    private static String sApiVersion = "v1";
    private static String sAccept = "application/json";
    private static String sContentType = "application/json";
    private static String sContentType1 = "application/x-www-form-urlencoded";

    private static RestClient sInstance;

    private static Converter sConverter;
    private static Gson sGson;
    private static ReflectiveTypeAdapterFactory sTypeAdapterFactory;

    private ApiService mApiService;
    private AuthService mAuthService;

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

    public static Gson getGson() {
        return sGson;
    }

    private RestClient() {
        sTypeAdapterFactory = createModelAdapterFactory();
        Gson auxGson = new Gson();
        if (sConverter == null) {
            sGson = new GsonBuilder()
                    .registerTypeAdapterFactory(new JsonTypeAdapterFactory())
//                    .registerTypeAdapterFactory(createMapAdapterFactory())
//                    .registerTypeAdapterFactory(createModelAdapterFactory())
                    .registerTypeAdapter(User.class, sTypeAdapterFactory.create(auxGson, TypeToken.get(User.class)))
                    .setDateFormat(DATE_FORMAT)
                    .create();
            sConverter = new GsonConverter(sGson);
        }
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(BASE_ALPHA_URL)
                .setConverter(sConverter)
                .setRequestInterceptor(createInterceptor())
                .build();
        mApiService = restAdapter.create(ApiService.class);
        RestAdapter authAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(BASE_ALPHA_URL)
                .setConverter(sConverter)
                .setRequestInterceptor(createAuthInterceptor())
                .build();
        mAuthService = authAdapter.create(AuthService.class);
    }

    private ApiService getApiService() {
        return mApiService;
    }

    private AuthService getAuthService() {
        return mAuthService;
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

    private RequestInterceptor createAuthInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("appid", HarpoonSDK.getAppId());
                request.addHeader("appsecret", HarpoonSDK.getAppSecret());
                request.addHeader("appbundle", HarpoonSDK.getAppBundle());
//                request.addHeader("device", null/*?*/); //TODO
//                request.addHeader("visitor", null/*?*/); //TODO
                request.addHeader("Accept", sAccept);
                request.addHeader("Content-Type", sContentType1);
                String credentials = HarpoonSDK.getAppId() + ":" + HarpoonSDK.getAppSecret();
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                request.addHeader("Authorization", auth);
            }
        };
    }

    private ReflectiveTypeAdapterFactory createModelAdapterFactory() {
        Map<Type, InstanceCreator<?>> typeMap = new HashMap<>();
        addTypeToMap(typeMap, User.class);
        addTypeToMap(typeMap, UserFollower.class);
        addTypeToMap(typeMap, UserFollowing.class);
        addTypeToMap(typeMap, Event.class);
        addTypeToMap(typeMap, EventAttendee.class);
        addTypeToMap(typeMap, EventTicket.class);
//        addTypeToMap(typeMap, Ticket.class);
        addTypeToMap(typeMap, Brand.class);
        addTypeToMap(typeMap, Venue.class);
        addTypeToMap(typeMap, Coords.class);
        ConstructorConstructor cc = new ConstructorConstructor(typeMap);
        Excluder exc = new Excluder().withExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }, false, true);
        return new ReflectiveTypeAdapterFactory(cc, FieldNamingPolicy.IDENTITY, exc);
    }

    /**
     * Add type to map in TypeAdapterFactory for proper deserialization
     * @param typeMap map of types for TypeAdapterFactory
     * @param clazz Class<> object for type that needs to be added
     */
    private void addTypeToMap(Map<Type, InstanceCreator<?>> typeMap, Class clazz) {
        typeMap.put(clazz, new InstanceCreator<Object>() {
            @Override
            public Object createInstance(Type type) {
                Constructor[] cons = ((Class)type).getConstructors();
                try {
                    return cons[0].newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    /*private MapTypeAdapterFactory createMapAdapterFactory() {
        Map<Type, InstanceCreator<?>> typeMap = new HashMap<>();
        typeMap.put(HashMap.class, new InstanceCreator<HashMap>() {
            @Override
            public HashMap createInstance(Type type) {
                return new HashMap();
            }
        });
        ConstructorConstructor cc = new ConstructorConstructor(typeMap);
        return new MapTypeAdapterFactory(cc, true);
    }*/

    /*private TypeAdapter createModelAdapter() {
        //prepare tools for typeAdapter factory
        Map<Type, InstanceCreator<?>> typeMap = new HashMap<>();
        typeMap.put(User.class, new InstanceCreator<User>() {
            @Override
            public User createInstance(Type type) {
                return new User();
            }
        });
        ConstructorConstructor cc = new ConstructorConstructor(typeMap);
        Excluder exc = new Excluder().withExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }, false, true);
        //create factory
        ReflectiveTypeAdapterFactory factory = new ReflectiveTypeAdapterFactory(cc, FieldNamingPolicy.IDENTITY, exc);
        //return adapter
        return factory.create(new Gson(), TypeToken.get(User.class));
    }*/

    //-----------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------
    //Rest API facade
    //-----------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------

    //------Auth api methods--------------------------------------

    /**
     * Authenticate application or user, and receive token for further requests.<br/>
     * For app token request, basic auth will be added (look at {@link #createAuthInterceptor()}).<br/>
     * Token (and its expiration time) will be saved in preferences and will be accessible through {@link HarpoonSDK}.<br/>
     * @param listener listener for result. {@link ApiListener#onSuccess()} is called on successful response.
     * @param forUser {@code true} if you want to get access token for user, {@code false} otherwise.
     */
    public void getAuthToken(ApiListener listener, boolean forUser) {
        if (forUser) {
            String code = HarpoonSDK.getUserAuthCode();
            String type = "authorization_code";
            getAuthService().getToken(sApiVersion, code, type, new AuthCallback(listener));
        } else {
            getAuthService().getToken(sApiVersion, new AuthCallback(listener));
        }
    }

    //------Application api methods--------------------------------------

    /**
     * Get application environment variables as {@link JsonObject}. Result contains date-of-birth restriction ("dob_restriction")<br/>
     * and other custom application variables.
     * @param listener listener for result. {@link ApiListener#onSuccess(JsonObject)} is called on successful response.
     */
    public void getApplicationInfo(ApiListener listener) {
        getApiService().getApplicationInfo(sApiVersion, HarpoonSDK.getAppToken(),
                new RestCallback(listener, "data", "application", "environment"));
    }

    //------eCommerce api methods----------------------------------------

    /*public void createCart(ApiListener listener) {
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
    }*/


    //------User api methods--------------------------------------------

    /**
     * Register new user (information is taken from {@link ParamsHelper}).
     * @param listener listener for result.
     */
    public void createUser(ApiListener1<User> listener) {
        RestCallback1<User> callback = new RestCallback1<>(User.class, listener, "data", "user");
        callback.setOnSuccessAction(RestCallback1.SET_USER_ID);
        getApiService().createUser(sApiVersion, HarpoonSDK.getAppToken(), ParamsHelper.getUser(), callback);
    }

    public void getUser(ApiListener1<User> listener) {
        getApiService().getUser(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                new RestCallback1<>(User.class, listener, "data", "user"));
    }

    public void getUserById(String userId, ApiListener1<User> listener) {
        getApiService().getUserById(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(User.class, listener, "data", "user"));
    }

    /**
     * Get users as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<User>)} will be called on success<br/>
     * Other method implementations use filter (as {@link HashMap} of criterias), limit and offset for pagination.
     * @param listener listener for result.
     */
    public void getUsers(ApiListener1<User> listener) {
        getUsers(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getUsers(HashMap<String, Object> filter, ApiListener1<User> listener) {
        getUsers(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getUsers(Integer limit, Integer offset, HashMap<String, Object> filter, ApiListener1<User> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getUsers(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(), paramString,
                new RestCallback1<>(User.class, true, listener, "data", "user"));
    }

    public void updateUser(ApiListener1<User> listener) {
        getApiService().updateUser(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                ParamsHelper.getUser(), new RestCallback1<>(User.class, listener, "data", "user"));
    }

    /**
     * Validate user's email and password to enter the app. User data including {@code auth_code} will be returned on success<br/>
     * (in {@code listener.onSuccess(User)} method).<br/>
     * {@code auth_code} is automatically stored in SDK, hence you need to call {@link #getAuthToken(ApiListener, boolean)}<br/>
     * (with {@code true} as a second parameter) to load access token for further API requests
     * @param email user email
     * @param pwd   password
     * @param listener listener for result.
     */
    public void login(String email, String pwd, ApiListener1<User> listener) {
        HashMap<String, String> params = ParamsHelper.userParams(email, pwd);
        RestCallback1<User> callback = new RestCallback1<>(User.class, listener, "data", "user");
        callback.setOnSuccessAction(RestCallback1.SET_USER_ID);
        getApiService().login(sApiVersion, "email", HarpoonSDK.getAppToken(),
                params, callback);
    }
    /*public void loginWithFacebook(String fbUserId, String fbUserToken, ApiListener listener) {
        HashMap<String, String> params = Data.userParams(true, fbUserId, fbUserToken);
        getApiService().login(sApiVersion, "facebook", params, new RestCallback(listener, "data", "user"));
    }
    public void loginWithTwitter(String twUserId, String twUserToken, ApiListener listener) {
        HashMap<String, String> params = Data.userParams(true, twUserId, twUserToken);
        getApiService().login(sApiVersion, "twitter", params, new RestCallback(listener, "data", "user"));
    }*/

    /**
     * Init password reset for user with provided email<br/>
     * No data will be returned, {@link ApiListener#onSuccess()} will be called on success
     * @param email email of user to reset password for
     * @param listener listener for result.
     */
    public void resetPassword(String email, ApiListener listener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        String paramString = sGson.toJson(params);
        getApiService().resetPassword(sApiVersion, HarpoonSDK.getUserToken(), paramString, new RestCallback(listener, "data"));
    }

    /**
     * Init password reset for current user<br/>
     * No data will be returned, {@link ApiListener#onSuccess()} will be called on success
     * @param listener listener for result.
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
     * @param listener listener for result.
     */
    public void updatePassword(String cur_pwd, String new_pwd, ApiListener listener) {
        HashMap<String, String> params = ParamsHelper.pwdParams(cur_pwd, new_pwd);
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
    /*public void addConnection(String provider, String userId, String userToken, ApiListener listener) {
        HashMap<String, String> params = Data.userParams(true, userId, userToken);
        getApiService().addConnection(sApiVersion, HarpoonSDK.getUserId(), provider, HarpoonSDK.getUserToken(),
                params, new RestCallback(listener, "data", "user"));
    }*/

    /**
     * Update user's social connection
     * @param provider  "facebook" or "twitter"
     * @param userToken user token from social network
     * @param listener
     */
    /*public void updateConnection(String provider, String userToken, ApiListener listener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_token", userToken);
        getApiService().updateConnection(sApiVersion, HarpoonSDK.getUserId(), provider, HarpoonSDK.getUserToken(),
                params, new RestCallback(listener, "data", "user"));
    }*/

    /**
     * Delete connection to social account
     * @param provider "facebook" or "twitter"
     * @param listener
     */
    /*public void deleteConnection(String provider, ApiListener listener) {
        getApiService().deleteConnection(sApiVersion, HarpoonSDK.getUserId(), provider, HarpoonSDK.getUserToken(),
                new RestCallback(listener, "data", "user"));
    }*/

    /**
     * Get activities of current user as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<UserAction>)} will be called on success<br/>
     * Other method implementations use filter (as {@link HashMap} of criterias), limit and offset for pagination.
     * @param listener listener for result
     */
    public void getActivities(ApiListener1<UserAction> listener) {
        getActivities(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getActivities(HashMap<String, Object> filter, ApiListener1<UserAction> listener) {
        getActivities(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getActivities(Integer limit, Integer offset, HashMap<String, Object> filter,
                              ApiListener1<UserAction> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getActivities(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserAction.class, true, listener, "data", "user", "activity"));
    }

    /**
     * Get activities (by user id) as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<UserAction>)} will be called on success<br/>
     * Other method implementations use filter (as {@link HashMap} of criterias), limit and offset for pagination.
     * @param userId requested user id
     * @param listener listener for result.
     */
    public void getActivitiesByUserId(String userId, ApiListener1<UserAction> listener) {
        getActivitiesByUserId(userId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getActivitiesByUserId(String userId, HashMap<String, Object> filter, ApiListener1<UserAction> listener) {
        getActivitiesByUserId(userId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getActivitiesByUserId(String userId, Integer limit, Integer offset, HashMap<String, Object> filter,
                              ApiListener1<UserAction> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getActivitiesByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserAction.class, true, listener, "data", "user", "activity"));
    }

    /**
     * Get followers of current user as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<UserFollower>)} will be called on success<br/>
     * Other method implementation use limit and offset for pagination.
     * @param listener listener for result.
     */
    public void getFollowers(ApiListener1<UserFollower> listener) {
        getFollowers(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getFollowers(Integer limit, Integer offset, ApiListener1<UserFollower> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getFollowers(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserFollower.class, true, listener, "data", "user", "follower"));
    }

    /**
     * Get followers of user as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<UserFollower>)} will be called on success<br/>
     * Other method implementation use limit and offset for pagination.
     * @param userId id of the user whose followings will be returned
     * @param listener listener for result.
     */
    public void getFollowersByUserId(String userId, ApiListener1<UserFollower> listener) {
        getFollowersByUserId(userId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getFollowersByUserId(String userId, Integer limit, Integer offset, ApiListener1<UserFollower> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getFollowersByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserFollower.class, true, listener, "data", "user", "follower"));
    }

    /**
     * Follow user with provided id<br/>
     * Followings of current user will be returned as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<UserFollowing>)} will be called on success
     * @param userId requested user id
     * @param listener listener for result.
     */
    public void followUser(String userId, ApiListener1<UserFollowing> listener) {
        getApiService().followUser(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(UserFollowing.class, true, listener, "data", "user", "following"));
    }

    /**
     * Unfollow user with provided id<br/>
     * Followings of current user will be returned as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<UserFollowing>)} will be called on success
     * @param userId requested user id
     * @param listener listener for result.
     */
    public void unfollowUser(String userId, ApiListener1<UserFollowing> listener) {
        getApiService().unfollowUser(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(UserFollowing.class, true, listener, "data", "user", "following"));
    }

    /**
     * Get followings of current user as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<UserFollowing>)} will be called on success<br/>
     * Other method implementation use limit and offset for pagination.
     * @param listener listener for result.
     */
    public void getFollowings(ApiListener1<UserFollowing> listener) {
        getFollowings(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getFollowings(Integer limit, Integer offset, ApiListener1<UserFollowing> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getFollowings(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserFollowing.class, true, listener, "data", "user", "following"));
    }

    /**
     * Get followings of user as {@link ArrayList} <br/>
     * {@link ApiListener1#onSuccess(ArrayList<UserFollowing>)} will be called on success<br/>
     * Other method implementation use limit and offset for pagination.
     * @param userId requested user id
     * @param listener listener for result.
     */
    public void getFollowingsByUserId(String userId, ApiListener1<UserFollowing> listener) {
        getFollowingsByUserId(userId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getFollowingsByUserId(String userId, Integer limit, Integer offset, ApiListener1<UserFollowing> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getFollowingsByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserFollowing.class, true, listener, "data", "user", "following"));
    }

    public void getAttendingEvents(ApiListener1<Event> listener) {
        getAttendingEvents(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getAttendingEvents(HashMap<String, Object> filter, ApiListener1<Event> listener) {
        getAttendingEvents(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getAttendingEvents(Integer limit, Integer offset, HashMap<String, Object> filter,
                                   ApiListener1<Event> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getAttendingEvents(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Event.class, true, listener, "data", "user", "event"));
    }

    public void getAttendingEventsByUserId(String userId, ApiListener1<Event> listener) {
        getAttendingEventsByUserId(userId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getAttendingEventsByUserId(String userId, HashMap<String, Object> filter, ApiListener1<Event> listener) {
        getAttendingEventsByUserId(userId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getAttendingEventsByUserId(String userId, Integer limit, Integer offset, HashMap<String, Object> filter,
                                           ApiListener1<Event> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getAttendingEventsByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Event.class, true, listener, "data", "user", "event"));
    }

    public void getAttendedEvents(ApiListener1<Event> listener) {
        getAttendedEvents(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getAttendedEvents(HashMap<String, Object> filter, ApiListener1<Event> listener) {
        getAttendedEvents(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getAttendedEvents(Integer limit, Integer offset, HashMap<String, Object> filter,
                                  ApiListener1<Event> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getAttendedEvents(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Event.class, true, listener, "data", "user", "event"));
    }

    public void getAttendedEventsByUserId(String userId, ApiListener1<Event> listener) {
        getAttendedEventsByUserId(userId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getAttendedEventsByUserId(String userId, HashMap<String, Object> filter, ApiListener1<Event> listener) {
        getAttendingEventsByUserId(userId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getAttendedEventsByUserId(String userId, Integer limit, Integer offset, HashMap<String, Object> filter,
                                          ApiListener1<Event> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getAttendedEventsByUserId(sApiVersion, HarpoonSDK.getUserId(), userId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Event.class, true, listener, "data", "user", "event"));
    }

    public void getEventTickets(String eventId, ApiListener1<UserEventTicket> listener) {
        getEventTickets(eventId, null, null, listener);
    }
    public void getEventTickets(String eventId, Integer limit, Integer offset, ApiListener1<UserEventTicket> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getEventTickets(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserEventTicket.class, true, listener, "data", "user", "event", "ticket"));
    }

    public void getCouponTickets(String couponId, ApiListener1<UserCouponTicket> listener) {
        getCouponTickets(couponId, null, null, listener);
    }
    public void getCouponTickets(String couponId, Integer limit, Integer offset, ApiListener1<UserCouponTicket> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getCouponTickets(sApiVersion, HarpoonSDK.getUserId(), couponId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserCouponTicket.class, true, listener, "data", "user", "coupon", "ticket"));
    }

    public void getSimpleDealTickets(String dealId, ApiListener1<UserDealTicket> listener) {
        getSimpleDealTickets(dealId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getSimpleDealTickets(String dealId, Integer limit, Integer offset, ApiListener1<UserDealTicket> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getDealTickets(sApiVersion, HarpoonSDK.getUserId(), "simple", dealId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserDealTicket.class, true, listener, "data", "user", "deal", "simple", "ticket"));
    }

    public void getGroupDealTickets(String dealId, ApiListener1<UserDealTicket> listener) {
        getGroupDealTickets(dealId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getGroupDealTickets(String dealId, Integer limit, Integer offset, ApiListener1<UserDealTicket> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getDealTickets(sApiVersion, HarpoonSDK.getUserId(), "group", dealId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserDealTicket.class, true, listener, "data", "user", "deal", "group", "ticket"));
    }

    public void getCards(ApiListener1<UserCard> listener) {
        getCards(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getCards(Integer limit, Integer offset, ApiListener1<UserCard> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getCards(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserCard.class, true, listener, "data", "user", "card"));
    }

    public void getCardInfo(String cardId, ApiListener1<UserCard> listener) {
        getApiService().getCardInfo(sApiVersion, HarpoonSDK.getUserId(), cardId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(UserCard.class, listener, "data", "user", "card"));
    }

    public void updateCard(String cardId, HashMap<String, String> cardInfo, ApiListener1<UserCard> listener) {
        getApiService().updateCard(sApiVersion, HarpoonSDK.getUserId(), cardId, HarpoonSDK.getUserToken(),
                cardInfo, new RestCallback1<>(UserCard.class, true, listener, "data", "user", "card"));
    }

    public void addCard(HashMap<String, String> cardInfo, ApiListener1<UserCard> listener) {
        String paramString = sGson.toJson(cardInfo);
        getApiService().addCard(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(UserCard.class, listener, "data", "user", "card"));
    }

    public void removeCard(String cardId, ApiListener1<UserCard> listener) {
        getApiService().removeCard(sApiVersion, HarpoonSDK.getUserId(), cardId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(UserCard.class, true, listener, "data", "user", "card"));
    }

    public void getCurrentWallet(ApiListener1<Event> listener) {
        getCurrentWallet(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getCurrentWallet(Integer limit, Integer offset, ApiListener1<Event> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getCurrentWallet(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Event.class, true, listener, "data", "user", "wallet", "current"));
    }

    public void getWalletHistory(ApiListener1<Event> listener) {
        getWalletHistory(DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getWalletHistory(Integer limit, Integer offset, ApiListener1<Event> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getWalletHistory(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Event.class, true, listener, "data", "user", "wallet", "history"));
    }


    //------Event api methods--------------------------------------------

    public void getEvents(ApiListener1<Event> listener) {
        getEvents(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getEvents(HashMap<String, Object> filter, ApiListener1<Event> listener) {
        getEvents(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getEvents(Integer limit, Integer offset,
                          HashMap<String, Object> filter, ApiListener1<Event> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getEvents(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Event.class, true, listener, "data", "event"));
    }

    public void getEventInfo(String eventId, ApiListener1<Event> listener) {
        getApiService().getEventInfo(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(Event.class, listener, "data", "event"));
    }

    public void getEventAttendees(String eventId, ApiListener1<EventAttendee> listener) {
        getEventAttendees(eventId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getEventAttendees(String eventId, Integer limit, Integer offset,
                                  ApiListener1<EventAttendee> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getEventAttendees(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(EventAttendee.class, true, listener, "data", "event", "attendee"));
    }

    public void getEventVenues(String eventId, ApiListener1<Venue> listener) {
        getEventVenues(eventId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getEventVenues(String eventId, Integer limit, Integer offset,
                               ApiListener1<Venue> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getEventVenues(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Venue.class, true, listener, "data", "event", "venue"));
    }

    public void eventCheckout(String[] ids, Integer[] quantities, String eventId,
                              ApiListener1<Checkout> listener) {
        HashMap<String, Object> params = ParamsHelper.checkoutList(ids, quantities);
        getApiService().eventCheckout(sApiVersion, HarpoonSDK.getUserId(), eventId, HarpoonSDK.getUserToken(),
                params, new RestCallback1<>(Checkout.class, listener, "data", "event", "checkout"));
    }

    //------Brand api methods--------------------------------------------

    public void getBrands(ApiListener1<Brand> listener) {
        getBrands(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getBrands(HashMap<String, Object> filter, ApiListener1<Brand> listener) {
        getBrands(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getBrands(Integer limit, Integer offset, HashMap<String, Object> filter, ApiListener1<Brand> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getBrands(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Brand.class, true, listener, "data", "brand"));
    }

    public void getBrandInfo(Integer brandId, ApiListener1<Brand> listener) {
        getApiService().getBrandInfo(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(Brand.class, listener, "data", "brand"));
    }

    public void followBrand(Integer brandId, ApiListener1<BrandFollower> listener) {
        getApiService().followBrand(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(BrandFollower.class, true, listener, "data", "brand", "follower"));
    }

    public void unfollowBrand(Integer brandId, ApiListener1<BrandFollower> listener) {
        getApiService().unfollowBrand(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(BrandFollower.class, true, listener, "data", "brand", "follower"));
    }

    public void getBrandFollowers(Integer brandId, ApiListener1<BrandFollower> listener) {
        getBrandFollowers(brandId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getBrandFollowers(Integer brandId, Integer limit, Integer offset, ApiListener1<BrandFollower> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getBrandFollowers(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(BrandFollower.class, true, listener, "data", "brand", "follower"));
    }

    public void getBrandFeed(Integer brandId, ApiListener1<BrandFeed> listener) {
        getBrandFeed(brandId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getBrandFeed(Integer brandId, HashMap<String, Object> filter, ApiListener1<BrandFeed> listener) {
        getBrandFeed(brandId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getBrandFeed(Integer brandId, Integer limit, Integer offset, HashMap<String, Object> filter,
                             ApiListener1<BrandFeed> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getBrandFeed(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(BrandFeed.class, true, listener, "data", "brand", "feed"));
    }

    public void getBrandVenues(Integer brandId, ApiListener1<Venue> listener) {
        getBrandVenues(brandId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getBrandVenues(Integer brandId, Integer limit, Integer offset, ApiListener1<Venue> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getBrandVenues(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Venue.class, true, listener, "data", "brand", "venue"));
    }

    public void getBrandEvents(Integer brandId, ApiListener1<Event> listener) {
        getBrandEvents(brandId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getBrandEvents(Integer brandId, HashMap<String, Object> filter, ApiListener1<Event> listener) {
        getBrandEvents(brandId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getBrandEvents(Integer brandId, Integer limit, Integer offset, HashMap<String, Object> filter,
                               ApiListener1<Event> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getBrandEvents(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Event.class, true, listener, "data", "brand", "event"));
    }

    public void getBrandOffers(Integer brandId, ApiListener1<Coupon> listener) {
        getBrandOffers(brandId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getBrandOffers(Integer brandId, HashMap<String, Object> filter, ApiListener1<Coupon> listener) {
        getBrandOffers(brandId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getBrandOffers(Integer brandId, Integer limit, Integer offset, HashMap<String, Object> filter,
                               ApiListener1<Coupon> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getBrandOffers(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Coupon.class, true, listener, "data", "brand", "offer"));
    }

    public void getBrandCoupons(Integer brandId, ApiListener1<Coupon> listener) {
        getBrandCoupons(brandId, DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getBrandCoupons(Integer brandId, HashMap<String, Object> filter, ApiListener1<Coupon> listener) {
        getBrandCoupons(brandId, DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getBrandCoupons(Integer brandId, Integer limit, Integer offset, HashMap<String, Object> filter,
                                ApiListener1<Coupon> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getBrandCoupons(sApiVersion, HarpoonSDK.getUserId(), brandId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Coupon.class, true, listener, "data", "brand", "coupon"));
    }

    //------Offer api methods--------------------------------------------

    public void getOffers(ApiListener1<Coupon> listener) {
        getOffers(null, null, null, listener);
    }
    public void getOffers(HashMap<String, Object> filter, ApiListener1<Coupon> listener) {
        getOffers(filter, null, null, listener);
    }
    public void getOffers(HashMap<String, Object> filter, Integer limit, Integer offset, ApiListener1<Coupon> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getOffers(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Coupon.class, true, listener, "data", "offer"));
    }

    //------Coupon api methods--------------------------------------------

    public void getCoupons(ApiListener1<Coupon> listener) {
        getCoupons(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getCoupons(HashMap<String, Object> filter, ApiListener1<Coupon> listener) {
        getCoupons(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getCoupons(Integer limit, Integer offset, HashMap<String, Object> filter, ApiListener1<Coupon> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getCoupons(sApiVersion, HarpoonSDK.getUserId(), HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Coupon.class, true, listener, "data", "coupon"));
    }

    public void getCouponInfo(String couponId, ApiListener1<Coupon> listener) {
        getApiService().getCouponInfo(sApiVersion, HarpoonSDK.getUserId(), couponId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(Coupon.class, listener, "data", "coupon"));
    }

    public void getCouponVenues(String couponId, ApiListener1<Venue> listener) {
        getCouponVenues(couponId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getCouponVenues(String couponId, Integer limit, Integer offset, ApiListener1<Venue> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getCouponVenues(sApiVersion, HarpoonSDK.getUserId(), couponId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Venue.class, true, listener, "data", "coupon", "venue"));
    }

    public void couponCheckout(String couponId, /*Integer quantity, */ApiListener1<CouponCheckout> listener) {
        HashMap<String, String> params = new HashMap<>();
//        params.put("qty", quantity);
        //TODO only for debug purposes
        params.put("debug", "B\\”PcVsb,i7D2]L0Y&5>3{,87gPY322");
        getApiService().couponCheckout(sApiVersion, HarpoonSDK.getUserId(), couponId, HarpoonSDK.getUserToken(),
                params, new RestCallback1<>(CouponCheckout.class, listener, "data", "coupon", "checkout"));
    }

    //------Deal api methods--------------------------------------------

    public void getSimpleDeals(ApiListener1<SimpleDeal> listener) {
        getSimpleDeals(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getSimpleDeals(HashMap<String, Object> filter, ApiListener1<SimpleDeal> listener) {
        getSimpleDeals(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getSimpleDeals(Integer limit, Integer offset, HashMap<String, Object> filter, ApiListener1<SimpleDeal> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getDeals(sApiVersion, HarpoonSDK.getUserId(), "simple", HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(SimpleDeal.class, true, listener, "data", "deal", "simple"));
    }

    public void getSimpleDealInfo(String dealId, ApiListener1<SimpleDeal> listener) {
        getApiService().getDealInfo(sApiVersion, HarpoonSDK.getUserId(), "simple", dealId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(SimpleDeal.class, listener, "data", "deal", "deal_simple"));
    }

    public void getSimpleDealVenues(String dealId, ApiListener1<Venue> listener) {
        getSimpleDealVenues(dealId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getSimpleDealVenues(String dealId, Integer limit, Integer offset, ApiListener1<Venue> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getDealVenues(sApiVersion, HarpoonSDK.getUserId(), "simple", dealId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Venue.class, true, listener, "data", "deal", "simple", "venue"));
    }

    public void simpleDealCheckout(String dealId, Integer quantity, ApiListener1<Checkout> listener) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("qty", quantity);
        getApiService().dealCheckout(sApiVersion, HarpoonSDK.getUserId(), "simple", dealId, HarpoonSDK.getUserToken(),
                params, new RestCallback1<>(Checkout.class, listener, "data", "deal", "simple", "checkout"));
    }

    public void getGroupDeals(ApiListener1<GroupDeal> listener) {
        getGroupDeals(DEF_LIMIT, DEF_OFFSET, null, listener);
    }
    public void getGroupDeals(HashMap<String, Object> filter, ApiListener1<GroupDeal> listener) {
        getGroupDeals(DEF_LIMIT, DEF_OFFSET, filter, listener);
    }
    public void getGroupDeals(Integer limit, Integer offset, HashMap<String, Object> filter, ApiListener1<GroupDeal> listener) {
        HashMap<String, Object> params = ParamsHelper.listFilterParams(limit, offset, filter);
        String paramString = sGson.toJson(params);
        getApiService().getDeals(sApiVersion, HarpoonSDK.getUserId(), "group", HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(GroupDeal.class, true, listener, "data", "deal", "group"));
    }

    public void getGroupDealInfo(String dealId, ApiListener1<GroupDeal> listener) {
        getApiService().getDealInfo(sApiVersion, HarpoonSDK.getUserId(), "group", dealId, HarpoonSDK.getUserToken(),
                new RestCallback1<>(GroupDeal.class, listener, "data", "deal", "deal_group"));
    }

    public void getGroupDealVenues(String dealId, ApiListener1<Venue> listener) {
        getGroupDealVenues(dealId, DEF_LIMIT, DEF_OFFSET, listener);
    }
    public void getGroupDealVenues(String dealId, Integer limit, Integer offset, ApiListener1<Venue> listener) {
        HashMap<String, Object> params = ParamsHelper.listParams(limit, offset);
        String paramString = sGson.toJson(params);
        getApiService().getDealVenues(sApiVersion, HarpoonSDK.getUserId(), "group", dealId, HarpoonSDK.getUserToken(),
                paramString, new RestCallback1<>(Venue.class, true, listener, "data", "deal", "group", "venue"));
    }

    public void groupDealCheckout(String dealId, Integer quantity, ApiListener1<Checkout> listener) {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("qty", quantity);
        getApiService().dealCheckout(sApiVersion, HarpoonSDK.getUserId(), "group", dealId, HarpoonSDK.getUserToken(),
                params, new RestCallback1<>(Checkout.class, listener, "data", "deal", "group", "checkout"));
    }

}
