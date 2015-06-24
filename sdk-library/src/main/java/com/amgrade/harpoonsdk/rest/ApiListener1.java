package com.amgrade.harpoonsdk.rest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Generic listener for Harpoon server responses
 * Created by michael on 22.06.15.
 * @param <T> type of data to receive from server. Single object can be received through {@link #onSuccess(T)},<br/>
 *           and array of objects - through {@link #onSuccess(ArrayList<T>)}.
 */
public interface ApiListener1<T extends Serializable> {
//    void onSuccess();
    void onSuccess(T response);
    void onSuccess(ArrayList<T> response);
    void onError(String code, String message);
//    void onError(RetrofitError error);
}
