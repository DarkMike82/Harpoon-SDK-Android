package com.amgrade.harpoonsdk.rest.model.brand;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Brand Feed Model<br/>
 * Created by michael on 23.06.15.
 */
public class BrandFeed implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("brand")
    private Brand mBrand;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("cover")
    private String mCover;

    @SerializedName("related")
    private HashMap<String, String> mRelatedIds;

    @SerializedName("link")
    private String mLink;

    @SerializedName("action_code")
    private String mActionCode;

    @SerializedName("privacy_code")
    private String mPrivacyCode;

    @SerializedName("posted_at")
    private String mPostedAt;


    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");


    public BrandFeed() {
    }

    public String getId() {
        return mId;
    }

    public Brand getBrand() {
        return mBrand;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getCover() {
        return mCover;
    }

    public HashMap<String, String> getRelatedIds() {
        return mRelatedIds;
    }

    public String getLink() {
        return mLink;
    }

    public String getActionCode() {
        return mActionCode;
    }

    public String getPrivacyCode() {
        return mPrivacyCode;
    }

    public Date getPostedAt() {
        if (mPostedAt==null) {
            return null;
        } else {
            Date d = null;
            try {
                d = mDateFormat.parse(mPostedAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d;
        }
    }

}
