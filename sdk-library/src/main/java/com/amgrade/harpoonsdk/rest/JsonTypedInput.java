package com.amgrade.harpoonsdk.rest;

import com.google.gson.JsonElement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import retrofit.mime.TypedInput;

/**
 * Created by Michael Dontsov on 22.06.15.
 */
class JsonTypedInput implements TypedInput {
    private byte[] mBytes;
//    private boolean isArray;

    public JsonTypedInput(/*boolean isArray,*/ JsonElement object) {
//        this.isArray = isArray;
        try {
            mBytes = RestClient.getGson().toJson(object).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            mBytes = null;
        }
    }

    @Override
    public String mimeType() {
        return "application/json";
    }

    @Override
    public long length() {
        if (mBytes==null) {
            return -1;
        } else {
            return mBytes.length;
        }
    }

    @Override
    public InputStream in() throws IOException {
        if (mBytes==null) {
            return null;
        } else {
            return new ByteArrayInputStream(mBytes);
        }
    }

//    public boolean isArray() {
//        return isArray;
//    }
}
