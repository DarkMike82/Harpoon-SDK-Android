package com.amgrade.harpoonsdk.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michael on 27.05.15.
 */
public class ListParams {
    @SerializedName("limit")
    private Integer mLimit;

    @SerializedName("offset")
    private Integer mOffset;

    public ListParams(){
    }

    public ListParams(Integer limit, Integer offset) {
        mLimit = limit;
        mOffset = offset;
    }
}
