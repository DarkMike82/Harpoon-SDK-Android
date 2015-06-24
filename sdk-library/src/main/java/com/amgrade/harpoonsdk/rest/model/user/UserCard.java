package com.amgrade.harpoonsdk.rest.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User Card Model<br/>
 * Created by michael on 24.06.15.
 */
public class UserCard implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("last_4_digits")
    private String mLast4Digits;

    @SerializedName("ex_month")
    private String mExMonth;

    @SerializedName("ex_year")
    private String mExYear;

    @SerializedName("cardholder_name")
    private String mCardholderName;


    public UserCard() {
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getLast4Digits() {
        return mLast4Digits;
    }

    public String getExpirationMonth() {
        return mExMonth;
    }

    public String getExpirationYear() {
        return mExYear;
    }

    public String getCardholderName() {
        return mCardholderName;
    }
}
