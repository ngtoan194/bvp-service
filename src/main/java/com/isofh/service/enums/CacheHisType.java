/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 */

package com.isofh.service.enums;

/**
 * 1 Chi tiet lich su vao vien<br>
 * 2 Ket qua lich su vao vien<br>
 * 4 Dat kham da day len his<br>
 */
public enum CacheHisType {

    DETAIL_PATIENT_HISTORY(1),
    RESULT_PATIENT_HISTORY(2),
    BOOKING_IN_HIS(4);

    private final Integer value;

    CacheHisType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
