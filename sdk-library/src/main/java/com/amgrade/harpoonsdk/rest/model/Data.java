package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.rest.RestClient;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * API params interface for application
 * Created by michael on 04.06.15.
 */
public class Data {
    private static UserData sUser;

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

    //-------------------------------------------------------------------
    //filter-data methods
    //-------------------------------------------------------------------

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

    public static HashMap<String, String> userParams(boolean social, String... data) {
        HashMap<String, String> params = new HashMap<>();
        if (social) {
            params.put("user_id", data[0]);
            params.put("user_token", data[1]);
        } else {
            params.put("email", data[0]);
            params.put("password", data[1]);
        }
        return params;
    }

    public static HashMap<String, String> pwdParams(String... data) {
        HashMap<String, String> params = new HashMap<>();
        params.put("current_password", data[0]);
        params.put("new_password", data[1]);
        return params;
    }

    public static HashMap getUser() {
        return sUser;
    }

    /**
     * Set user's basic data
     * @param email
     * @param pwd
     * @param f_name
     * @param l_name
     * @param birthday
     * @param gender
     */
    public static void setUser(String email, String pwd,
                                   String f_name, String l_name,
                                   String birthday, String gender) {
        if (sUser==null) {
            sUser = new UserData(email, pwd, f_name, l_name, birthday, gender);
        } else {
            sUser.setData(email, pwd, f_name, l_name, birthday, gender);
        }
    }

    /*public static void setUserAddress(String country, String county, String city, String postcode) {
        sUser.setAddress(country, county, city, postcode);
    }*/

    public static void setProfilePicture(String mime, String base64encodedpicture) {
        sUser.setPicture(mime, base64encodedpicture);
    }

    public static void addUserMetadata(String[] keys, Object[] values){
        sUser.addMetadata(keys, values);
    }

    public static void setConnection(String connection_name, String userId, String token) {
        sUser.setConnection(connection_name, userId, token);
    }

    /*public static void setUserLocation(String lat, String lon) {
        sUser.setLocation(lat, lon);
    }*/

    public static void clearUser() {
        sUser.clear();
    }
}
