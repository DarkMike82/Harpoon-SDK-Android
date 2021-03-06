package com.amgrade.harpoonsdk.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Michael Dontsov on 27.05.15.
 */
class JsonTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {
            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementAdapter.read(in);
//                Log.d("READ_JSON",jsonElement.toString());
                return delegate.fromJsonTree(jsonElement);
            }

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }
        }.nullSafe();
    }
}
