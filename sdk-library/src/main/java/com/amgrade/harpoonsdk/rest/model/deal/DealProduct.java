package com.amgrade.harpoonsdk.rest.model.deal;

import com.google.gson.annotations.SerializedName;

/**
 * Product attached to a deal
 * Created by michael on 24.06.15.
 */
public class DealProduct {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("cover")
    private String mCover;

    @SerializedName("price")
    private Float mPrice;

    @SerializedName("base_currency")
    private String mBaseCurrency;


    public DealProduct() {
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCover() {
        return mCover;
    }

    public Float getPrice() {
        return mPrice;
    }

    public String getBaseCurrency() {
        return mBaseCurrency;
    }
}
