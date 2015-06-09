package com.amgrade.harpoonsdk.rest;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.amgrade.harpoonsdk.rest.model.Data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public void getApplicationSettings(ApiListener listener) {
        getApiService().getApplicationSettings(sApiVersion, new RestCallback(listener, "data","application","environment"));
    }

    //------eCommerce api methods----------------------------------------

    public void createCart(ApiListener listener) {
        getApiService().createCart(sApiVersion, HarpoonSDK.getUserId(), new RestCallback(listener, "data","ecommerce","cart"));
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

//    private void createUser()

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

    public void getProducts(ApiListener listener) {
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
    }

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
        getApiService().getEvents(sApiVersion, HarpoonSDK.getUserId(),
                params, new RestCallback(listener, "data", "event"));
    }

    public void getEventById(String eventId, ApiListener listener) {
        getEventById(eventId, "ecommerce", listener);
    }
    public void getEventById(String eventId, String id_type, ApiListener listener) {
        getApiService().getEventById(sApiVersion, HarpoonSDK.getUserId(), eventId, id_type,
                new RestCallback(listener, "data", "event"));
    }

    public void getEventCustomers(String eventId, ApiListener listener) {
        getEventCustomers(eventId, null, null, null, listener);
    }
    public void getEventCustomers(String eventId, Integer limit, Integer offset, ApiListener listener) {
        getEventCustomers(eventId, null, limit, offset, listener);
    }
    public void getEventCustomers(String eventId, String id_type,
                                  Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getEventCustomers(sApiVersion, HarpoonSDK.getUserId(), eventId, id_type,
                params, new RestCallback(listener, "data", "event")); //TODO maybe need to add "customer"
    }

    public void getEventVenues(String eventId, ApiListener listener) {
        getEventVenues(eventId, null, null, null, listener);
    }
    public void getEventVenues(String eventId, Integer limit, Integer offset, ApiListener listener) {
        getEventVenues(eventId, null, limit, offset, listener);
    }
    public void getEventVenues(String eventId, String id_type,
                                  Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getEventVenues(sApiVersion, HarpoonSDK.getUserId(), eventId, id_type,
                params, new RestCallback(listener, "data", "event", "venue"));
    }

    public void getEventTickets(String eventId, ApiListener listener) {
        getEventTickets(eventId, null, null, null, listener);
    }
    public void getEventTickets(String eventId, Integer limit, Integer offset, ApiListener listener) {
        getEventTickets(eventId, null, limit, offset, listener);
    }
    public void getEventTickets(String eventId, String id_type,
                                  Integer limit, Integer offset, ApiListener listener) {
        HashMap<String, Object> params = Data.listParams(limit, offset);
        getApiService().getEventTickets(sApiVersion, HarpoonSDK.getUserId(), eventId, id_type,
                params, new RestCallback(listener, "data", "event", "ticket"));
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
        getApiService().getOffers(sApiVersion, HarpoonSDK.getUserId(), params,
                new RestCallback(listener, "data", "offer"));
    }

    public void getOfferById(String offerId, ApiListener listener) {
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
    }

}
