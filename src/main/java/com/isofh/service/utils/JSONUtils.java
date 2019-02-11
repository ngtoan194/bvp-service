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

import org.json.JSONObject;

public class JSONUtils {

    /**
     * Get String by path
     *
     * @param path path separate by .
     * @return
     */
    public static String getStringByPath(JSONObject jsonObject, String path) {
        if (!path.contains(".")) {
            return getStringByField(jsonObject, path);
        }
        String[] fields = path.split("[.]");
        JSONObject tempJson = jsonObject.getJSONObject(fields[0]);
        for (int i = 1; i < fields.length - 1; i++) {
            tempJson = tempJson.getJSONObject(fields[i]);
        }
        return getStringByField(tempJson, fields[fields.length - 1]);
    }

    /**
     * Lay string boi truong (field)
     *
     * @param jsonObject
     * @param field
     * @return
     */
    private static String getStringByField(JSONObject jsonObject, String field) {
        return jsonObject.getString(field);
    }

    /**
     * Get Integer from path
     *
     * @param jsonObject
     * @param path
     * @return
     */
    public static Integer getIntegerByPath(JSONObject jsonObject, String path) {
        if (!path.contains(".")) {
            return getIntegerByField(jsonObject, path);
        }
        String[] fields = path.split("[.]");
        JSONObject tempJson = jsonObject.getJSONObject(fields[0]);
        for (int i = 1; i < fields.length - 1; i++) {
            tempJson = tempJson.getJSONObject(fields[i]);
        }
        return getIntegerByField(tempJson, fields[fields.length - 1]);
    }

    /**
     * Lay so nguyen tu truong trong json object
     *
     * @param jsonObject
     * @param field
     * @return
     */
    private static Integer getIntegerByField(JSONObject jsonObject, String field) {
        return jsonObject.getInt(field);
    }

    /**
     * Get Integer from path
     *
     * @param jsonObject
     * @param path
     * @return
     */
    public static Long getLongByPath(JSONObject jsonObject, String path) {
        if (!path.contains(".")) {
            return getLongByField(jsonObject, path);
        }
        String[] fields = path.split("[.]");
        JSONObject tempJson = jsonObject.getJSONObject(fields[0]);
        for (int i = 1; i < fields.length - 1; i++) {
            tempJson = tempJson.getJSONObject(fields[i]);
        }
        return getLongByField(tempJson, fields[fields.length - 1]);
    }

    /**
     * Lay so nguyen tu truong trong json object
     *
     * @param jsonObject
     * @param field
     * @return
     */
    private static Long getLongByField(JSONObject jsonObject, String field) {
        return jsonObject.getLong(field);
    }

    /**
     * Lay JsonObject boi 1 path
     *
     * @param jsonObject
     * @param path
     * @return
     */
    public static JSONObject getJSONObjectByPath(JSONObject jsonObject, String path) {
        if (!path.contains(".")) {
            return getJSONObjectByField(jsonObject, path);
        }
        String[] fields = path.split("[.]");
        JSONObject tempJson = jsonObject.getJSONObject(fields[0]);
        for (int i = 1; i < fields.length - 1; i++) {
            tempJson = tempJson.getJSONObject(fields[i]);
        }
        return getJSONObjectByField(tempJson, fields[fields.length - 1]);
    }

    private static JSONObject getJSONObjectByField(JSONObject jsonObject, String field) {
        return jsonObject.getJSONObject(field);
    }


}
