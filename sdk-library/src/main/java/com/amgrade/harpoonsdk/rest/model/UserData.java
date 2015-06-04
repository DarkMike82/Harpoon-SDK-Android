package com.amgrade.harpoonsdk.rest.model;

import java.util.HashMap;

/**
 * Created by michael on 04.06.15.
 */
class UserData extends HashMap<String, Object> {

    private HashMap<String, Object> mMetadata;

    public UserData() {
        super();
    }

    public UserData(String... data) {
        super();
        this.put("email", data[0]);
        this.put("password", data[1]);
        this.put("first_name", data[2]);
        this.put("middle_name", data[3]);
        this.put("last_name", data[4]);
        this.put("birthday", data[5]);
        this.put("gender", data[6]);
    }

    /**
     * Reset user's basic data
     * @param data
     */
    public void setData(String... data) {
        this.put("password", data[1]);
        this.put("first_name", data[2]);
        this.put("middle_name", data[3]);
        this.put("last_name", data[4]);
        this.put("birthday", data[5]);
        this.put("gender", data[6]);
    }

    public void setAddress(String... data) {
        HashMap<String, String> address = new HashMap<>();
        address.put("country", data[0]);
        address.put("county", data[1]);
        address.put("city", data[2]);
        address.put("postcode", data[3]);

        this.put("address", address);
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

    public void setLocation(String... data) {
        HashMap<String, String> location = new HashMap<>();
        location.put("latitude", data[0]);
        location.put("longitude", data[1]);

        this.put("current_location", location);
    }

}
