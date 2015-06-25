package com.amgrade.harpoonsdk.rest.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * User Model<br/>
 * Created by Michael Dontsov on 23.06.15.
 */
public class User implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("authorization_code")
    private String mAuthCode;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("first_name")
    private String mFName;

    @SerializedName("last_name")
    private String mLName;

    @SerializedName("dob")
    private String mBirthDate;

    @SerializedName("gender")
    private String mGender;

    @SerializedName("profile_picture")
    private String mProfilePicture;

    @SerializedName("cover")
    private String mCover;

    @SerializedName("metadata")
    private HashMap<String, ? extends Serializable> mMetadata;

    @SerializedName("connection")
    private HashMap<String, HashMap<String, String>> mConnections;

    @SerializedName("following_count")
    private Integer mFollowingCount;

    @SerializedName("follower_count")
    private Integer mFollowerCount;

    @SerializedName("notification_count")
    private Integer mNotificationCount;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd");


    public User() {
    }

    public String getId() {
        return mId;
    }

    public String getAuthCode() {
        return mAuthCode;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getFName() {
        return mFName;
    }

    public String getLName() {
        return mLName;
    }

    public Date getBirthDate() {
        if (mBirthDate==null) {
            return null;
        } else {
            Date d = null;
            try {
                d = mDateFormat.parse(mBirthDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d;
        }
    }

    public String getGender() {
        return mGender;
    }

    public String getProfilePicture() {
        return mProfilePicture;
    }

    public String getCover() {
        return mCover;
    }

    public HashMap<String, ? extends Serializable> getMetadata() {
        return mMetadata;
    }

    public HashMap<String, HashMap<String, String>> getConnections() {
        return mConnections;
    }

    public Integer getFollowingCount() {
        return mFollowingCount;
    }

    public Integer getFollowerCount() {
        return mFollowerCount;
    }

    public Integer getNotificationCount() {
        return mNotificationCount;
    }
}
