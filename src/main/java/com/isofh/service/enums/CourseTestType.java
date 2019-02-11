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
 * 1 Test dau vao <br>
 * 2 Test dau ra  <br>
 */
public enum CourseTestType {

    PRE_TEST(1),
    POST_TEST(2);

    private final Integer value;

    CourseTestType(Integer value) {
        this.value = value;
    }

    public static CourseTestType fromValue(int value) {
        if (Integer.valueOf(1).equals(value)) {
            return PRE_TEST;
        } else if (Integer.valueOf(2).equals(value)) {
            return POST_TEST;
        }
        return PRE_TEST;
    }

    public Integer getValue() {
        return value;
    }
}
