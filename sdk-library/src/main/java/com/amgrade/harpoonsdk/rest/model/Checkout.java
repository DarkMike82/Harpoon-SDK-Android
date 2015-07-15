package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.rest.model.event.Event;
import com.amgrade.harpoonsdk.rest.model.deal.SimpleDeal;
import com.amgrade.harpoonsdk.rest.model.deal.GroupDeal;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Checkout Model (for {@link Event}, {@link SimpleDeal} or {@link GroupDeal})<br/>
 * Created by Michael Dontsov on 24.06.15.
 */
public class Checkout implements Serializable {
    @SerializedName("cart_id")
    private String mCartId;

    @SerializedName("url")
    private String mUrl;

    public Checkout() {
    }

    /**
     * @return ID of the related Cart
     */
    public String getCartId() {
        return mCartId;
    }

    /**
     * @return Url to proceed to the checkout
     */
    public String getUrl() {
        return mUrl;
    }
}
