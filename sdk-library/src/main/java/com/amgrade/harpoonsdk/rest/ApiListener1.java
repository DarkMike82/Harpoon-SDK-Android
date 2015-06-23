package com.amgrade.harpoonsdk.rest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by michael on 22.06.15.
 */
public interface ApiListener1<T extends Serializable> {
    void onSuccess();
    void onSuccess(T response);
    void onSuccess(ArrayList<T> response);
    void onError(String code, String message);
//    void onError(RetrofitError error);
}
