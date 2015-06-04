package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.rest.RestClient;

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
    //user-data methods
    //-------------------------------------------------------------------

    public static HashMap getUser() {
        return sUser;
    }

    /**
     * Set user's basic data
     * @param email
     * @param pwd
     * @param f_name
     * @param m_name
     * @param l_name
     * @param birthday
     * @param gender
     */
    public static void setUser(String email, String pwd,
                                   String f_name, String m_name, String l_name,
                                   String birthday, String gender) {
        if (sUser==null) {
            sUser = new UserData(email, pwd, f_name, m_name, l_name, birthday, gender);
        } else {
            sUser.setData(email, pwd, f_name, m_name, l_name, birthday, gender);
        }
    }

    public static void setUserAddress(String country, String county, String city, String postcode) {
        sUser.setAddress(country, county, city, postcode);
    }

    public static void addUserMetadata(String[] keys, Object[] values){
        sUser.addMetadata(keys, values);
    }

    public static void setUserLocation(String lat, String lon) {
        sUser.setLocation(lat, lon);
    }

    public static void clearUser() {
        sUser.clear();
    }
}
