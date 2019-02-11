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
 * Nguon tao nguoi dung <br>
 * 0 tu dang ky<br>
 * 1 admin tao<br>
 * 2 His tao<br>
 */
public enum UserSourceType {
    REGISTERED(0),
    ADMIN(1),
    HIS(2);

    private final Integer value;

    UserSourceType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
