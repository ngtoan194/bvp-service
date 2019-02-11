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
 * Tuyến BV: lấy từ His<br>
 * 1 Tuyen 1<br>
 * 2 Tuyen 2<br>
 * 4 Tuyen 3<br>
 * 8 Tuyen 4<br>
 */
public enum FacilityLevelType {

    LEVEL1(1),
    LEVEL2(2),
    LEVEL3(4),
    LEVEL4(8);

    private final int value;

    FacilityLevelType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
