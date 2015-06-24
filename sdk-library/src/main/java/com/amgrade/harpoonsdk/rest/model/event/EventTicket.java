package com.amgrade.harpoonsdk.rest.model.event;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Ticket Model (for {@link Event})<br/>
 * Created by michael on 24.06.15.
 */
public class EventTicket implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("price")
    private Float mPrice;

    @SerializedName("qty_left")
    private Integer mQuantityLeft;

    @SerializedName("qty_total")
    private Integer mQuantityTotal;

    @SerializedName("qty_bought")
    private Integer mQuantityBought;


    public EventTicket() {
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Float getPrice() {
        return mPrice;
    }

    public Integer getQuantityLeft() {
        return mQuantityLeft;
    }

    public Integer getQuantityTotal() {
        return mQuantityTotal;
    }

    public Integer getQuantityBought() {
        return mQuantityBought;
    }
}
