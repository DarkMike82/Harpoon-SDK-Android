package com.amgrade.harpoonsdk.rest.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * User Activity Model (user feed)<br/>
 * Created by Michael Dontsov on 23.06.15.
 */
public class UserAction implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("user")
    private User mUser;

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


    public UserAction() {
    }

    public String getId() {
        return mId;
    }

    public User getUser() {
        return mUser;
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
