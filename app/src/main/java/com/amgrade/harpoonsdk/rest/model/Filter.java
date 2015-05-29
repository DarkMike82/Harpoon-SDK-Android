package com.amgrade.harpoonsdk.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michael on 29.05.15.
 */
public class Filter {
    @SerializedName("filter")


    @SerializedName("complex_filter")

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

    public static class ExtendedFilter {

    }
}
