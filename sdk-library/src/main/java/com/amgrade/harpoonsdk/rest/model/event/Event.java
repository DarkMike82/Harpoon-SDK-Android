package com.amgrade.harpoonsdk.rest.model.event;

import com.amgrade.harpoonsdk.rest.model.Venue;
import com.amgrade.harpoonsdk.rest.model.brand.Brand;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Event Model<br/>
 * Created by michael on 24.06.15.
 */
public class Event implements Serializable {
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

    @SerializedName("topic")
    private String mTopic;

    @SerializedName("alias")
    private String mAlias;

    @SerializedName("from")
    private String mFromDate;

    @SerializedName("to")
    private String mToDate;

    @SerializedName("base_price")
    private Float mBasePrice;

    @SerializedName("base_currency")
    private String mBaseCurrency;

    @SerializedName("attendee")
    private ArrayList<EventAttendee> mAttendees;

    @SerializedName("attendee_count")
    private Integer mAttendeeCount;

    @SerializedName("nearest_venue")
    private Venue mNearestLocation;

    @SerializedName("is_going")
    private Boolean mIsGoing;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("ticket")
    private ArrayList<EventTicket> mTickets;

    @SerializedName("brand")
    private Brand mOrganiserBrand;


    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");


    public Event() {
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

    public String getTopic() {
        return mTopic;
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

    public Float getBasePrice() {
        return mBasePrice;
    }

    public String getBaseCurrency() {
        return mBaseCurrency;
    }

    public ArrayList<EventAttendee> getAttendees() {
        return mAttendees;
    }

    public Integer getAttendeeCount() {
        return mAttendeeCount;
    }

    public Venue getNearestLocation() {
        return mNearestLocation;
    }

    public Boolean isGoing() {
        return mIsGoing;
    }

    public String getStatus() {
        return mStatus;
    }

    public ArrayList<EventTicket> getTickets() {
        return mTickets;
    }

    public Brand getOrganiserBrand() {
        return mOrganiserBrand;
    }
}
