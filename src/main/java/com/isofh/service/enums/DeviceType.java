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
 * Loai device de dang nhap
 * 1 IOS<br>
 * 2 Android <br>
 * 4. Web <br>
 */
public enum DeviceType {
    IOS(1),
    ANDROID(2),
    WEB(4);

    private Integer value;

    DeviceType(Integer type) {
        this.value = type;
    }

    public Integer getValue() {
        return value;
    }
}
