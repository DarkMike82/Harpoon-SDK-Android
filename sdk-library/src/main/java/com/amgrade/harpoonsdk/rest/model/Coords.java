package com.amgrade.harpoonsdk.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Location coordinates<br/>
 * Created by michael on 24.06.15.
 */
public class Coords implements Serializable {
    @SerializedName("latitude")
    private Float mLat;

    @SerializedName("longitude")
    private Float mLng;


    public Coords() {
    }

    public Float getLatitude() {
        return mLat;
    }

    public Float getLongitude() {
        return mLng;
    }
}
