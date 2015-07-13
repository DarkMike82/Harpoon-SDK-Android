package com.amgrade.harpoonsdk.rest.model.deal;

import com.amgrade.harpoonsdk.rest.model.Coupon;
import com.google.gson.annotations.SerializedName;

/**
 * Simple Deal Model<br/>
 * Created by Michael Dontsov on 24.06.15.
 */
public class SimpleDeal extends Coupon {
//    @SerializedName("id")
//    private String mId;

//    @SerializedName("product")
//    private DealProduct mProduct;

//    @SerializedName("name")
//    private String mName;

//    @SerializedName("description")
//    private String mDescription;

//    @SerializedName("cover")
//    private String mCover;

//    @SerializedName("category")
//    private String mCategory;

//    @SerializedName("type")
//    private String mType;

//    @SerializedName("alias")
//    private String mAlias;

//    @SerializedName("from")
//    private String mFromDate;

//    @SerializedName("to")
//    private String mToDate;

//    @SerializedName("price")
//    private Float mPrice;

//    @SerializedName("qty_left")
//    private Integer mQuantityLeft;

//    @SerializedName("qty_total")
//    private Integer mQuantityTotal;

    @SerializedName("qty_purchased")
    private Integer mQuantityPurchased;

//    @SerializedName("nearest_venue")
//    private Venue mNearestLocation;

    @SerializedName("has_purchased")
    private Boolean mHasPurchased;

//    @SerializedName("status")
//    private String mStatus;

//    @SerializedName("brand")
//    private Brand mBrand;

//    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");


    public SimpleDeal() {
        super();
    }

//    public String getId() {
//        return mId;
//    }

//    public DealProduct getProduct() {
//        return mProduct;
//    }

//    public String getName() {
//        return mName;
//    }

//    public String getDescription() {
//        return mDescription;
//    }

//    public String getCover() {
//        return mCover;
//    }

//    public String getCategory() {
//        return mCategory;
//    }

//    public String getType() {
//        return mType;
//    }

//    public String getAlias() {
//        return mAlias;
//    }

//    public Date getFromDate() {
//        if (mFromDate==null) {
//            return null;
//        } else {
//            Date d = null;
//            try {
//                d = mDateFormat.parse(mFromDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return d;
//        }
//    }

//    public Date getToDate() {
//        if (mToDate==null) {
//            return null;
//        } else {
//            Date d = null;
//            try {
//                d = mDateFormat.parse(mToDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return d;
//        }
//    }

//    public Float getPrice() {
//        return mPrice;
//    }

//    public Integer getQuantityLeft() {
//        return mQuantityLeft;
//    }

//    public Integer getQuantityTotal() {
//        return mQuantityTotal;
//    }

    public Integer getQuantityPurchased() {
        return mQuantityPurchased;
    }

//    public Venue getNearestLocation() {
//        return mNearestLocation;
//    }

    public Boolean hasPurchased() {
        return mHasPurchased;
    }

//    public String getStatus() {
//        return mStatus;
//    }

//    public Brand getBrand() {
//        return mBrand;
//    }
}
