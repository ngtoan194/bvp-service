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
 * Loai gioi tinh<br>
 * 1 nam<br>
 * 0 nu
 */
public enum GenderType {
    MALE(1),
    FEMALE(0);

    private Integer type;

    GenderType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
