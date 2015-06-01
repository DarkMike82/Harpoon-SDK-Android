package com.amgrade.harpoonsdk.rest.model;

import android.text.TextUtils;

import com.amgrade.harpoonsdk.HarpoonSDK;
import com.amgrade.harpoonsdk.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by michael on 01.06.15.
 */
public class EventData implements Serializable {
//    @SerializedName()

    /**
     * Empty constructor to provide backwards compatibility with Retrofit
     */
    public EventData() {
    }

//    public EventData() {
//    }

    //------------------------------------------------------------------------
    //Nested classes
    //------------------------------------------------------------------------

    /**
     * Event ticket data
     */
    public static class TicketDataObject {
        @SerializedName("event_description")
        private String mDescription;
        @SerializedName("event_subtitle")
        private String mSubtitle;
        @SerializedName("event_title")
        private String mTitle;
        @SerializedName("venue_location")
        private String mVenueLocation;
        @SerializedName("venue_name")
        private String mVenueName;
        @SerializedName("venue_phone")
        private String mVenuePhone;

        public TicketDataObject() {
        }

        /**
         * @param params 3 {@link String} parameters: title, subtitle, description (use in this order).
         */
        public TicketDataObject(String... params) {
            mTitle = params[0];
            mSubtitle = params[1];
            mDescription = params[2];
        }

        /**
         * Set venue data for ticket
         * @param venue_params
         */
        public void setVenue(String... venue_params) {
            mVenueLocation = venue_params[0];
            mVenueName = venue_params[1];
            mVenuePhone = venue_params[2];
        }

    }

    /**
     * Event media
     */
    public static class MediaObject implements Serializable {
        @SerializedName("media_name")
        private String mName;
        @SerializedName("label")
        private String mLabel;
        @SerializedName("crop")
        private CropObject mCropObject;
        @SerializedName("source")
        private String mPicSource;
        @SerializedName("url")
        private String mUrl;

        public MediaObject() {
        }

        /**
         * @param name use null to create new media, or a {@link java.lang.String} to update existing media
         * @param data can be valid URL or base64 encoded content of the picture
         * @param isUrl true is you're using Url as {@code data}, false otherwise
         */
        public MediaObject(String name, String data, boolean isUrl) {
            if (TextUtils.isEmpty(name)) {
                mName = "";
            } else {
                mName = name;
            }
            if (isUrl) {
                mUrl = data;
            } else {
                mPicSource = data;
            }
        }

        public void setLabel(String label) {
            mLabel = label;
        }

        public void setCropObject(CropObject obj) {
            mCropObject = obj;
        }

    }

    /**
     * Rectangle for picture cropping
     */
    public static class CropObject implements Serializable {
        @SerializedName("x")
        private Integer mCropX;
        @SerializedName("y")
        private Integer mCropY;
        @SerializedName("width")
        private Integer mCropWidth;
        @SerializedName("height")
        private Integer mCropHeight;

        public CropObject() {
        }

        /**
         * @param params 4 {@link Integer} parameters: x, y, w, h (use in this order).<br/>
         *               x & y - horizontal & vertical starting points of the rectangle;<br/>
         *               w & h - width & height of the rectangle.
         */
        public CropObject(Integer... params) {
            if (params.length!=4) {
                HarpoonSDK.showMessage(0, R.string.wrong_crop_data);
                return;
            }
            mCropX = params[0];
            mCropY = params[1];
            mCropWidth = params[2];
            mCropHeight = params[3];
        }
    }

}
