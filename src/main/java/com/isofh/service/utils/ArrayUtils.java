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

public class ArrayUtils {

    /**
     * Kiem tra xem mang co null hay empty khong
     */
    public static boolean isNullOrEmpty(Object[] arrs) {
        return arrs == null || arrs.length == 0;
    }

    /**
     * Noi cac phan tu trong mang phan cach nhau boi dau ","
     */
    public static String concatStringWithComma(String[] strings) {
        StringBuilder sb = new StringBuilder();

        if (strings == null || strings.length == 0) {
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

    public static String concatIntegerWithComma(int[] arr) {
        StringBuilder sb = new StringBuilder();

        if (arr == null || arr.length == 0) {
            return sb.toString();
        }
        for (int str : arr) {
            sb.append(String.format("%s,", str));
        }

        // xoa ky tu "," cua ban ghi cuoi cung
        String result = sb.toString();
        if (result.length() > 0)
            result = result.substring(0, result.length() - 1);
        return result;
    }
}
