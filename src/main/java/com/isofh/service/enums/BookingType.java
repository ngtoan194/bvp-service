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

public enum BookingType {

    HOSPITAL(0),
    HOME(1),
    MEDICAL_TEST_HOME(2);

    private final Integer value;

    BookingType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
