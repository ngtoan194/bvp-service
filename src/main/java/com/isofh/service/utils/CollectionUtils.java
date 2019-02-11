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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CollectionUtils {


    public static <T> T firstOrDefault(Collection<T> departments) {
        if (departments == null || departments.isEmpty()) return null;
        return departments.iterator().next();
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Sub list by page, size
     *
     * @param objects Danh sach source
     * @param page    Phan trang ket qua tim kiem
     * @param size    so luong ban ghi tren mot trang
     * @param <T>     common returned type
     * @return
     */
    public static <T> List<T> subList(List<T> objects, Integer page, Integer size) {
        if (objects == null || objects.size() <= (page - 1) * size) return new ArrayList<>();
        int lastIndex = page * size;
        if (objects.size() < page * size)
            lastIndex = objects.size();
        return objects.subList((page - 1) * size, lastIndex);
    }

    public static <T> List<T> subList(Set<T> objects, Integer page, Integer size) {
        if (objects == null || objects.size() <= (page - 1) * size) return new ArrayList<>();
        int lastIndex = page * size;
        if (objects.size() < page * size)
            lastIndex = objects.size();

        return new ArrayList<>(objects).subList((page - 1) * size, lastIndex);
    }


    public static <T> Integer getSize(Set<T> objects) {
        if (objects == null) return 0;
        return objects.size();
    }


    public static String concatStringWithComma(Collection<String> strings) {
        StringBuilder sb = new StringBuilder();

        if (strings == null || strings.isEmpty()) {
            return sb.toString();
        }
        for (String str : strings) {
            sb.append(String.format("'%s',", str));
        }

        // xoa ky tu "," cua ban ghi cuoi cung
        String result = sb.toString();
        if (result.length() > 0)
            result = result.substring(0, result.length() - 1);
        return result;
    }

    public static String concatLongWithComma(List<Long> ids) {
        StringBuilder sb = new StringBuilder();

        if (ids == null || ids.isEmpty()) {
            return sb.toString();
        }
        for (Long str : ids) {
            sb.append(String.format("%s,", str));
        }

        // xoa ky tu "," cua ban ghi cuoi cung
        String result = sb.toString();
        if (result.length() > 0)
            result = result.substring(0, result.length() - 1);
        return result;
    }
}
