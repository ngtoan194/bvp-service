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

public enum NotificationDisplayType {

    DEFAULT(0),
    CUSTOM(1);

    private final Integer value;

    NotificationDisplayType(Integer value) {
        this.value = value;
    }

    public Integer getType() {
        return value;
    }
}
