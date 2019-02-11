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
 * Hạng BV: lấy từ His <br>
 * 1 Hang dac biet<br>
 * 2 Hang 1<br>
 * 4 Hang 2<br>
 * 8 Hang 3<br>
 * 16 Hang 4<br>
 */
public enum FacilityRankType {

    SPECIAL(1),
    RANK1(2),
    RANK2(4),
    RANK3(8),
    RANK4(16);

    private final int value;

    FacilityRankType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
