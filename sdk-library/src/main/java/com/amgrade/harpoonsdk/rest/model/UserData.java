package com.amgrade.harpoonsdk.rest.model;

import java.util.HashMap;

/**
 * Created by michael on 04.06.15.
 */
class UserData extends HashMap<String, Object> {

    private HashMap<String, Object> mMetadata;
    private HashMap<String, String> mPicture;
    private HashMap<String, Object> mConnection;

    public UserData() {
        super();
    }

    public UserData(String... data) {
        super();
        this.put("email", data[0]);
        this.put("password", data[1]);
        this.put("first_name", data[2]);
//        this.put("middle_name", data[3]);
        this.put("last_name", data[3]);
        this.put("birthday", data[4]);
        this.put("gender", data[5]);
    }

    /**
     * Reset user's basic data
     * @param data
     */
    public void setData(String... data) {
        this.put("password", data[1]);
        this.put("first_name", data[2]);
//        this.put("middle_name", data[3]);
        this.put("last_name", data[3]);
        this.put("birthday", data[4]);
        this.put("gender", data[5]);
    }

    /*public void setAddress(String... data) {
        HashMap<String, String> address = new HashMap<>();
        address.put("country", data[0]);
        address.put("county", data[1]);
        address.put("city", data[2]);
        address.put("postcode", data[3]);

        this.put("address", address);
    }*/

    public void setPicture(String mime, String base64encodedimage) {
        if (mPicture==null) {
            mPicture = new HashMap<>();
        }
        mPicture.put("content_type", mime);
        mPicture.put("file", base64encodedimage);

        this.put("profile_picture", mPicture);
    }

    public void addMetadata(String[] keys, Object[] values) {
        if (mMetadata==null) {
            mMetadata = new HashMap<>();
        }
        for (int i=0;i<keys.length;i++) {
            mMetadata.put(keys[i], values[i]);
        }

        this.put("metadata", mMetadata);
    }

    public void setConnection(String connection_name, String userId, String token) {
        if (mConnection==null) {
            mConnection = new HashMap<>();
        }
        HashMap<String, String> user = new HashMap<>();
        user.put("user_id", userId);
        user.put("user_token", token);
        mConnection.put(connection_name, user);
        this.put("connection", mConnection);
    }

    /*public void setLocation(String... data) {
        HashMap<String, String> location = new HashMap<>();
        location.put("latitude", data[0]);
        location.put("longitude", data[1]);

        this.put("current_location", location);
    }*/

}
