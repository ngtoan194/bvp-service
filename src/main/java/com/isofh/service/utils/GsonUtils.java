/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package com.isofh.service.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.isofh.service.constant.AppConst;
import com.isofh.service.convertor.LocalDateJsonConverter;
import com.isofh.service.convertor.LocalDateTimeJsonConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GsonUtils {

    /**
     * Convert String to Object
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T toObject(String json, Class<T> cls) {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
                .registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter())
                .create().fromJson(json, cls);
    }

    /**
     * Convert String to Object
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T toObject(JsonElement json, Class<T> cls) {
        return new GsonBuilder().setDateFormat(AppConst.DATE_TIME_FORMAT).registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter()).create().fromJson(json, cls);
    }

//    /**
//     * Convert Object to String using Json
//     *
//     * @param obj
//     * @return
//     */
//    public static String toStringResult(Object obj) {
//        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(obj);
//    }

    /**
     * Convert Object to String using Json
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter()).setPrettyPrinting().create().toJson(obj);
    }

    public static String toStringCompact(Object obj) {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter()).create().toJson(obj);
    }

}
