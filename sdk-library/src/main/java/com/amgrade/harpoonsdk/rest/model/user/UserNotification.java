package com.amgrade.harpoonsdk.rest.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * User Notification Model<br/>
 * Created by Michael Dontsov on 24.06.15.
 */
public class UserNotification implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("cover")
    private String mCover;

    @SerializedName("from")
    private HashMap<String, Object> mFromObjects;

    @SerializedName("related")
    private HashMap<String, String> mRelatedIds;

    @SerializedName("link")
    private String mLink;

    @SerializedName("action_code")
    private String mActionCode;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("sent_at")
    private String mSentAt;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");


    public UserNotification() {
    }

    public String getId() {
        return mId;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getCover() {
        return mCover;
    }

    public HashMap<String, Object> getFromObjects() {
        return mFromObjects;
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

    public String getStatus() {
        return mStatus;
    }

    public Date getSentAt() {
        if (mSentAt==null) {
            return null;
        } else {
            Date d = null;
            try {
                d = mDateFormat.parse(mSentAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d;
        }
    }
}
