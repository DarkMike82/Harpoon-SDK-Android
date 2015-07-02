package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.amgrade.harpoonsdk.rest.RestClient;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helper to create params for API requests<br/>
 * Can store information about current user. If you want to register a new user, put its data here using:<br/>
 * {@link #setName(String, String)},<br/>
 * {@link #setBirthday(String)},<br/>
 * {@link #setEmail(String)},<br/>
 * {@link #setGender(String)},<br/>
 * {@link #setPassword(String)},<br/>
 * {@link #setProfilePicture(String, String)},<br/>
 * {@link #setCover(String, String)},<br/>
 * {@link #addUserMetadata(String[], Object[])},<br/>
 * {@link #setConnection(String, String)},<br/>
 * {@link #requestAuthCodeForUser(boolean)},<br/>
 * {@link #setRandomString(String)}.<br/>
 * Also contains methods to create params for list, filter and checkout.<br/>
 * <br/>
 * Created by Michael Dontsov on 04.06.15.
 */
public class ParamsHelper {
    private static UserParams sUser = new UserParams();

    //-------------------------------------------------------------------
    //list & filter-data methods
    //-------------------------------------------------------------------

    public static HashMap<String, Object> listParams(Integer limit, Integer offset) {
        HashMap<String, Object> params = new HashMap<>();
        if (limit==null) {
            limit = RestClient.DEF_LIMIT;
        }
        params.put("limit", limit);
        if (offset==null) {
            offset = RestClient.DEF_OFFSET;
        }
        params.put("offset", offset);
        return params;
    }

    public static HashMap<String, Object> listFilterParams(Integer limit, Integer offset, HashMap<String, Object> filter) {
        HashMap<String, Object> params = listParams(limit, offset);
        if (filter!=null) {
            params.put("filter", filter);
        }
        return params;
    }

    /*private static HashMap createFilter(String[] keys, Object[] values) {
        HashMap<String, Object> filter = new HashMap<>();
        for (int i=0;i<keys.length;i++) {
            filter.put(keys[i], values[i]);
        }
        return filter;
    }*/

    //-------------------------------------------------------------------
    //checkout-data methods
    //-------------------------------------------------------------------

    public static HashMap<String, Object> checkoutList(String[] ids, Integer[] quantities) {
        HashMap<String, Object> res = new HashMap<>();
        ArrayList<Ticket> list = new ArrayList<>();
        for (int i=0;i<ids.length;i++) {
            Ticket item = new Ticket(ids[i], quantities[i]);
            list.add(item);
        }
        res.put("ticket", list);
        return res;
    }

    //-------------------------------------------------------------------
    //user-data methods
    //-------------------------------------------------------------------

    public static HashMap<String, String> userParams(/*boolean social,*/ String... data) {
        HashMap<String, String> params = new HashMap<>();
//        if (social) {
//            params.put("user_id", data[0]);
//            params.put("user_token", data[1]);
//        } else {
            params.put("email", data[0]);
            params.put("password", data[1]);
            params.put("client_id", HarpoonSDK.getAppId());
            params.put("response_type", "code");
            params.put("state", System.currentTimeMillis()+"");
//        }
        return params;
    }

    public static HashMap<String, String> pwdParams(String... data) {
        HashMap<String, String> params = new HashMap<>();
        params.put("current_password", data[0]);
        params.put("new_password", data[1]);
        return params;
    }

    public static HashMap getUser() {
        if (!sUser.containsKey("client_id")) {
            sUser.put("client_id", HarpoonSDK.getAppId());
        }
        sUser.put("response_type", "code");
        sUser.setRandomString(System.currentTimeMillis()+"");
        return sUser;
    }

    public static void setName(String first_name, String last_name) {
        sUser.setName(first_name, last_name);
    }

    public static void setBirthday(String date) {
        sUser.setBirthday(date);
    }

    public static void setEmail(String email) {
        sUser.setEmail(email);
    }

    public static void setGender(String gender) {
        sUser.setGender(gender);
    }

    public static void setPassword(String pwd) {
        sUser.setPassword(pwd);
    }

    public static void setProfilePicture(String mime, String base64encodedpicture) {
        sUser.setPicture(mime, base64encodedpicture);
    }

    public static void setCover(String mime, String base64encodedpicture) {
        sUser.setCover(mime, base64encodedpicture);
    }

    public static void addUserMetadata(String[] keys, Object[] values){
        sUser.addMetadata(keys, values);
    }

    public static void setConnection(String connection_name, String userId) {
        sUser.setConnection(connection_name, userId);
    }

    public void requestAuthCodeForUser(boolean needAuthCode) {
        sUser.requestAuthCodeForUser(needAuthCode);
    }

    public void setRandomString(String text) {
        sUser.setRandomString(text);
    }

    public static void clearUser() {
        sUser.clear();
    }
}
