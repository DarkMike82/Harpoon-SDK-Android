package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.rest.model.event.EventTicket;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Used internally when you book tickets for event.<br/>
 * Contains id of {@link EventTicket} and quantity of booked tickets.<br/>
 * Created by Michael Dontsov on 11.06.15.
 */
public class Ticket implements Serializable {
    @SerializedName("id")
    private String mId;
    @SerializedName("qty")
    private Integer mQuantity;

    public Ticket() {
    }

    public Ticket(String id, Integer quantity) {
        mId = id;
        mQuantity = quantity;
    }

    public String getId() {
        return mId;
    }

    public Integer getQuantity() {
        return mQuantity;
    }
}
