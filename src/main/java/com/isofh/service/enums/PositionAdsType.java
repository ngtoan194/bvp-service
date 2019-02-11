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

public enum PositionAdsType {
    LEFT(0),
    RIGHT(1),
    TOP(2),
    HOT_NEW(3);

    private final Integer value;

    PositionAdsType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
