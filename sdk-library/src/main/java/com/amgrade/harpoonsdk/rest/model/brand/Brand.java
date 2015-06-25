package com.amgrade.harpoonsdk.rest.model.brand;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Brand Model<br/>
 * Created by Michael Dontsov on 24.06.15.
 */
public class Brand implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("logo")
    private String mLogo;

    @SerializedName("cover")
    private String mCover;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("follower_count")
    private Integer mFollowerCount;


    public Brand() {
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getLogo() {
        return mLogo;
    }

    public String getCover() {
        return mCover;
    }

    public String getDescription() {
        return mDescription;
    }

    public Integer getFollowerCount() {
        return mFollowerCount;
    }

}
