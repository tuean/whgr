package com.tuean.whgr.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GsonIsoDateTimeJsonDeserializer implements JsonDeserializer<Date> {


    @Override
    public Date deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        LocalDateTime localDateTime = LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_DATE_TIME);

        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
    }


}
