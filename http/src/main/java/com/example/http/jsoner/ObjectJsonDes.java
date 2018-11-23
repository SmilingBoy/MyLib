package com.example.http.jsoner;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Smile
 * @date 2018/4/4
 */

public class ObjectJsonDes implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            return context.deserialize(json, itemType);
        }
        return null;
    }
}
