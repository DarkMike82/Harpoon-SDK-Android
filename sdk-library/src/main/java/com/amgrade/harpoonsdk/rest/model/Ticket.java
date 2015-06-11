package com.amgrade.harpoonsdk.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by michael on 11.06.15.
 * Ticket or Merchandise for event
 */
class Ticket implements Serializable {
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
