package com.amgrade.harpoonsdk.rest.model;

import java.util.HashMap;

/**
 * Created by Michael Dontsov on 04.06.15.
 */
class UserParams extends HashMap<String, Object> {

    private HashMap<String, Object> mMetadata;
    private HashMap<String, String> mPicture;
    private HashMap<String, Object> mConnection;

    public UserParams() {
        super();
    }

    /*public UserParams(String... data) {
        super();
        this.put("email", data[0]);
        this.put("password", data[1]);
        this.put("first_name", data[2]);
        this.put("last_name", data[3]);
        this.put("birthday", data[4]);
        this.put("gender", data[5]);
    }*/

    /**
     * Reset user's basic data
//     * @param data
     */
    /*public void setData(String... data) {
        this.put("password", data[1]);
        this.put("first_name", data[2]);
        this.put("last_name", data[3]);
        this.put("birthday", data[4]);
        this.put("gender", data[5]);
    }*/

    public void setName(String first_name, String last_name) {
        this.put("first_name", first_name);
        this.put("last_name", last_name);
    }

    public void setBirthday(String date) {
        this.put("dob", date);
    }

    public void setEmail(String email) {
        this.put("email", email);
    }

    public void setGender(String gender) {
        this.put("gender", gender);
    }

    public void setPassword(String pwd) {
        this.put("password", pwd);
    }

    public void setPicture(String mime, String base64encodedimage) {
        if (mPicture==null) {
            mPicture = new HashMap<>();
        }
        mPicture.put("content_type", mime);
        mPicture.put("file", base64encodedimage);

        this.put("profile_picture", mPicture);
    }

    public void setCover(String mime, String base64encodedimage) {
        if (mPicture==null) {
            mPicture = new HashMap<>();
        }
        mPicture.put("content_type", mime);
        mPicture.put("file", base64encodedimage);

        this.put("cover", mPicture);
    }

    public void addMetadata(String[] keys, Object[] values) {
        if (mMetadata==null) {
            mMetadata = new HashMap<>();
        }
        for (int i=0;i<keys.length;i++) {
            mMetadata.put(keys[i], values[i]);
        }

        this.put("metadata", mMetadata);
    }

    public void setConnection(String connection_name, String userId) {
        if (mConnection==null) {
            mConnection = new HashMap<>();
        } else {
            mConnection.clear();
        }
        HashMap<String, String> user = new HashMap<>();
        user.put("user_id", userId);
        mConnection.put(connection_name, user);
        this.put("connection", mConnection);
    }

    public void requestAuthCodeForUser(boolean needAuthCode) {
        if (needAuthCode) {
            this.put("response_type", "code");
        } else {
            this.remove("response_type");
        }
    }

    public void setRandomString(String text) {
        if (text!=null) {
            this.put("state", text);
        } else {
            this.remove("state");
        }
    }

}
