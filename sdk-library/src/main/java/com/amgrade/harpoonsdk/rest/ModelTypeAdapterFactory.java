package com.amgrade.harpoonsdk.rest;

import android.util.Log;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Created by michael on 21.07.15.
 */
public class ModelTypeAdapterFactory implements TypeAdapterFactory {
    private ReflectiveTypeAdapterFactory mModelFactory;

    public ModelTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
        mModelFactory = new ReflectiveTypeAdapterFactory(constructorConstructor, fieldNamingPolicy, excluder);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (type.toString().contains("Map")) {
            Log.d("TYPEADAPTER", "delegate: "+type.toString());
            return gson.getDelegateAdapter(this, type);
        }
        Log.d("TYPEADAPTER", "model: "+type.toString());
        return mModelFactory.create(gson, type);
    }
}
