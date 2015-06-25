package com.amgrade.harpoonsdk.rest.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * User Deal Ticket Model<br/>
 * Created by Michael Dontsov on 24.06.15.
 */
public class UserDealTicket implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("code")
    private HashMap<String, String> mCode;

    @SerializedName("is_available")
    private Boolean mIsAvailable;

    @SerializedName("created_at")
    private String mCreatedAt;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");


    public UserDealTicket() {
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public HashMap<String, String> getCode() {
        return mCode;
    }

    public Boolean isAvailable() {
        return mIsAvailable;
    }

    public Date getCreatedAt() {
        if (mCreatedAt==null) {
            return null;
        } else {
            Date d = null;
            try {
                d = mDateFormat.parse(mCreatedAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d;
        }
    }
}
