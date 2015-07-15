package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.rest.model.user.UserCouponTicket;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by michael on 14.07.15.
 */
public class CouponCheckout implements Serializable {
    @SerializedName("ticket")
    private UserCouponTicket mTicket;

    public CouponCheckout() {
    }

    public UserCouponTicket getTicket() {
        return mTicket;
    }
}
