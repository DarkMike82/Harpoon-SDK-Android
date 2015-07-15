package com.amgrade.harpoonsdk.rest;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by michael on 15.07.15.
 */
final class InterfaceAdapter<T> implements /*JsonSerializer<T>,*/ JsonDeserializer<T> {

//    private String mTypeName;
    private Type mType;
    /*private static final HashMap<String, String> sTypeMap;
    static {
        sTypeMap = new HashMap<String, String>();
        sTypeMap.put("user","com.amgrade.harpoonsdk.rest.model.user.User");
        sTypeMap.put("user.activity", "com.amgrade.harpoonsdk.rest.model.user.UserAction");
        sTypeMap.put("user.follower", "com.amgrade.harpoonsdk.rest.model.user.UserFollower");
        sTypeMap.put("user.following", "com.amgrade.harpoonsdk.rest.model.user.UserFollowing");
        sTypeMap.put("user.notification", "com.amgrade.harpoonsdk.rest.model.user.UserNotification");
        sTypeMap.put("user.ticket.event", "com.amgrade.harpoonsdk.rest.model.user.UserEventTicket");
        sTypeMap.put("user.ticket.coupon", "com.amgrade.harpoonsdk.rest.model.user.UserCouponTicket");
        sTypeMap.put("user.ticket.deal", "com.amgrade.harpoonsdk.rest.model.user.UserDealTicket");
        sTypeMap.put("user.card", "com.amgrade.harpoonsdk.rest.model.user.UserCard");
        sTypeMap.put("event", "com.amgrade.harpoonsdk.rest.model.event.Event");
        sTypeMap.put("event.ticket", "com.amgrade.harpoonsdk.rest.model.event.EventTicket");
        sTypeMap.put("event.attendee", "com.amgrade.harpoonsdk.rest.model.event.EventAttendee");
        sTypeMap.put("venue", "com.amgrade.harpoonsdk.rest.model.Venue"); //venue for Event/Coupon/
        sTypeMap.put("", "com.amgrade.harpoonsdk.rest.model.");
        sTypeMap.put("", "com.amgrade.harpoonsdk.rest.model.");
    }*/

    public InterfaceAdapter(String typeName) {
//        mTypeName = typeName;
        mType = typeForName(typeName);
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        final JsonObject wrapper = (JsonObject) json;
        return context.deserialize(json, mType);
    }

    /*@Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }*/

    private Type typeForName(String typeName) {
        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }
}
