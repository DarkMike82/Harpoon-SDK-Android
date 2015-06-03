package com.amgrade.harpoonsdk.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by michael on 29.05.15.
 */
public class FilterObject implements Serializable{
    @SerializedName("filter")
    private ArrayList<KeyValuePair> mFilter;
    @SerializedName("complex_filter")
    private ArrayList<FilterItem> mExtFilter;

    /**
     * Empty constructor to provide backwards compatibility with Retrofit
     */
    public FilterObject() {
    }

    public FilterObject(ArrayList<KeyValuePair> filter) {
        mFilter = filter;
        mExtFilter = null;
    }

    public FilterObject(ArrayList<KeyValuePair> filter, ArrayList<FilterItem> extFilter) {
        mFilter = filter;
        mExtFilter = extFilter;
    }

    /**
     * Item for {@link #mFilter}
     */
    public static class KeyValuePair {
        @SerializedName("key")
        private String mKey;

        @SerializedName("value")
        private Object mValue;

        public KeyValuePair() {
        }

        public KeyValuePair(String key, Object value) {
            mKey = key;
            mValue = value;
        }

    }

    /**
     * Item for {@link #mExtFilter}
     */
    public static class FilterItem implements Serializable{
        @SerializedName("key")
        private String mKey;
        @SerializedName("value")
        private ArrayList<KeyValuePair> mValue;

        public FilterItem() {
        }

        public FilterItem(String key, ArrayList<KeyValuePair> value) {
            mKey = key;
            mValue = value;
        }
    }

}
