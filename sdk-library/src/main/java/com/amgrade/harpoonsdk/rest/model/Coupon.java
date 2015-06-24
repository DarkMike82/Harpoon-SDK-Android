package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.rest.model.brand.Brand;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Coupon Model<br/>
 * Created by michael on 24.06.15.
 */
public class Coupon implements Serializable{
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("cover")
    private String mCover;

    @SerializedName("category")
    private String mCategory;

    @SerializedName("type")
    private String mType;

    @SerializedName("alias")
    private String mAlias;

    @SerializedName("from")
    private String mFromDate;

    @SerializedName("to")
    private String mToDate;

    @SerializedName("price")
    private Float mPrice;

    @SerializedName("qty_left")
    private Integer mQuantityLeft;

    @SerializedName("qty_total")
    private Integer mQuantityTotal;

    @SerializedName("qty_claimed")
    private Integer mQuantityClaimed;

    @SerializedName("nearest_venue")
    private Venue mNearestLocation;

    @SerializedName("has_claimed")
    private Boolean mHasClaimed;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("brand")
    private Brand mBrand;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");


    public Coupon() {
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

    public String getCategory() {
        return mCategory;
    }

    public String getType() {
        return mType;
    }

    public String getAlias() {
        return mAlias;
    }

    public Date getFromDate() {
        if (mFromDate==null) {
            return null;
        } else {
            Date d = null;
            try {
                d = mDateFormat.parse(mFromDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d;
        }
    }

    public Date getToDate() {
        if (mToDate==null) {
            return null;
        } else {
            Date d = null;
            try {
                d = mDateFormat.parse(mToDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d;
        }
    }

    public Float getPrice() {
        return mPrice;
    }

    public Integer getQuantityLeft() {
        return mQuantityLeft;
    }

    public Integer getQuantityTotal() {
        return mQuantityTotal;
    }

    public Integer getQuantityClaimed() {
        return mQuantityClaimed;
    }

    public Venue getNearestLocation() {
        return mNearestLocation;
    }

    public Boolean hasClaimed() {
        return mHasClaimed;
    }

    public String getStatus() {
        return mStatus;
    }

    public Brand getBrand() {
        return mBrand;
    }
}
