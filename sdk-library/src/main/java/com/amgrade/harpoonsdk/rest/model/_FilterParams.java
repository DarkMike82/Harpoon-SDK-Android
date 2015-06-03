package com.amgrade.harpoonsdk.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by michael on 01.06.15.
 */
public class _FilterParams implements Serializable {
    @SerializedName("filter")
    private FilterObject mFilterParams;

    /**
     * Empty constructor to provide backwards compatibility with Retrofit
     */
    public _FilterParams() {
    }

    public _FilterParams(FilterObject params) {
        mFilterParams = params;
    }

}
