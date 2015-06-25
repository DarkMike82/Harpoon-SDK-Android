package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.rest.model.brand.Brand;
import com.amgrade.harpoonsdk.rest.model.event.Event;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Venue Model (for {@link Brand}, {@link Coupon} or {@link Event})<br/>
 * Created by Michael Dontsov on 24.06.15.
 */
public class Venue implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("city")
    private String mCity;

    @SerializedName("country")
    private String mCountry;

    @SerializedName("coordinates")
    private Coords mCoords;

    @SerializedName("brand")
    private Brand mBrand;

    public Venue() {
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getCity() {
        return mCity;
    }

    public String getCountry() {
        return mCountry;
    }

    public Float getLatitude() {
        return mCoords.getLatitude();
    }

    public Float getLongitude() {
        return mCoords.getLongitude();
    }

    /**
     * @return Brand owning this venue, or{@code null} if this object received as brand's venue.
     */
    public Brand getBrand() {
        return mBrand;
    }
}
