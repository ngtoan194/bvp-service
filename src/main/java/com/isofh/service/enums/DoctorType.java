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
 * 0 truogn khoa<br>
 * 1 pho khoa <br>
 * 2 bac si thuong<br>
 */

public enum DoctorType {
    PRESIDENT(0),
    VICE(1),
    NORMAL(2);


    private final Integer value;

    DoctorType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
