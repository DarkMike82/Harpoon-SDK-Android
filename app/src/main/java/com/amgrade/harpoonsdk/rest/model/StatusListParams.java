package com.amgrade.harpoonsdk.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michael on 27.05.15.
 */
public class StatusListParams extends ListParams {
    @SerializedName("status")
    private String mStatus;

    public StatusListParams() {
    }

    public StatusListParams(String status, Integer limit, Integer offset) {
        super(limit, offset);
        mStatus = status;
    }
}
