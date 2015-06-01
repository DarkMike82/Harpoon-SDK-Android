package com.amgrade.harpoonsdk.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michael on 27.05.15.
 */
public class _ListParams {
    @SerializedName("limit")
    private Integer mLimit;

    @SerializedName("offset")
    private Integer mOffset;

    /**
     * Empty constructor to provide backwards compatibility with Retrofit
     */
    public _ListParams(){
    }

    public _ListParams(Integer limit, Integer offset) {
        if (limit==null || limit<1) {
            mLimit = 20;
        } else {
            mLimit = limit;
        }
        mOffset = offset;
    }
}
