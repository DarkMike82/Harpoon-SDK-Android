package com.amgrade.harpoonsdk.rest.model.event;

import com.amgrade.harpoonsdk.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Event Attendee Model<br/>
 * Created by Michael Dontsov on 23.06.15.
 */
public class EventAttendee implements Serializable, Constants {
    @SerializedName("id")
    private String mId;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("first_name")
    private String mFName;

    @SerializedName("last_name")
    private String mLName;

    @SerializedName("gender")
    private String mGender;

    @SerializedName("profile_picture")
    private String mProfilePicture;

    @SerializedName("joined_at")
    private String mJoinedAt;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat();


    public EventAttendee() {
    }

    public String getId() {
        return mId;
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

    public String getGender() {
        return mGender;
    }

    public String getProfilePicture() {
        return mProfilePicture;
    }

    public String getJoinedAt() {
        if (mJoinedAt==null) {
            return null;
        } else {
            Date d = null;
            String res = null;
            try {
                mDateFormat.applyPattern(IN_PATTERN);
                d = mDateFormat.parse(mJoinedAt);
                mDateFormat.applyPattern(OUT_PATTERN);
                res = mDateFormat.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return res;
        }
    }

}
