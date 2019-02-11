/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package com.isofh.service.enums;

/**
 * Data cua website nao <br>
 * 1 Tin tuc <br>
 * 2 Trao doi lam sang <br>
 */
public enum NewsType {

    NEWS(1),
    CLINIC_DISCUSSION(2);

    private final Integer value;

    NewsType(Integer value) {
        this.value = value;
    }

    public static NewsType fromValue(int value) {
        if (Integer.valueOf(1).equals(value)) {
            return NEWS;
        } else if (Integer.valueOf(2).equals(value)) {
            return CLINIC_DISCUSSION;
        }
        return NEWS;
    }

    public Integer getValue() {
        return value;
    }
}
