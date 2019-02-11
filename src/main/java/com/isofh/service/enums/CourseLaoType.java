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
 * 1 Cap chung chi <br>
 * 2 Khong cap chung chi <br>
 */
public enum CourseLaoType {

    CERTIFICATE(1),
    NOT_CERTIFICATE(2);

    private final Integer value;

    CourseLaoType(Integer value) {
        this.value = value;
    }

    public static CourseLaoType fromValue(int value) {
        if (Integer.valueOf(1).equals(value)) {
            return CERTIFICATE;
        } else if (Integer.valueOf(2).equals(value)) {
            return NOT_CERTIFICATE;
        }
        return CERTIFICATE;
    }

    public Integer getValue() {
        return value;
    }
}
