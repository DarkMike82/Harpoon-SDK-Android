package com.amgrade.harpoonsdk.rest.model;

import com.amgrade.harpoonsdk.R;
import com.amgrade.harpoonsdk.HarpoonSDK;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by michael on 01.06.15.
 * POST-params for event creation request
 */
public class _EventParams implements Serializable {
    @SerializedName("campaign_id")
    private Integer mCampaignId;
    @SerializedName("external_id")
    private String mExternalId;
    @SerializedName("data")
    private EventData mData;

    /**
     * Empty constructor to provide backwards compatibility with Retrofit
     */
    public _EventParams() {
    }

    /**
     * @param cmpId Campaign Id to associate with the event
     * @param extId This can be used as other reference type (optional)
     * @param data An object containing all the event details
     */
    public _EventParams(Integer cmpId, String extId, EventData data) {
        if (cmpId<=0) {
            HarpoonSDK.showMessage(0, R.string.wrong_cmp_id);
            return;
        }
        if (extId!=null && extId.isEmpty()) {
            HarpoonSDK.showMessage(0, R.string.wrong_ext_id);
            return;
        }
        if (data == null) {
            HarpoonSDK.showMessage(0, R.string.wrong_event_data);
            return;
        }
        mCampaignId = cmpId;
        mExternalId = extId;
        mData = data;
    }
}
