package com.amgrade.harpoonsdk.rest.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User Event Ticket Model<br/>
 * Created by michael on 24.06.15.
 */
public class UserEventTicket implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("code")
    private String mCode;

    @SerializedName("qrcode")
    private String mQRCode;

    @SerializedName("is_available")
    private Boolean mIsAvailable;

    @SerializedName("created_at")
    private String mCreatedAt;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");


    public UserEventTicket() {
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCode() {
        return mCode;
    }

    public String getQRCode() {
        return mQRCode;
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
