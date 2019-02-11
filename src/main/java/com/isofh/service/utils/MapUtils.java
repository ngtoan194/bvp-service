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

import java.util.Map;

public class MapUtils {
    /**
     * Check whether map is null or empty
     *
     * @param map
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }
}
