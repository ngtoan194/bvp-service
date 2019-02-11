package com.isofh.service.convertor;

import com.google.gson.*;
import com.isofh.service.constant.AppConst;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeJsonConverter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(AppConst.DATE_TIME_FORMAT);

    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDateTime.format(dateTimeFormatter));
    }

    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return LocalDateTime.parse(json.getAsString(), dateTimeFormatter);
    }
}