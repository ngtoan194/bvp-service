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

public enum ValidateTokenResult {
    SUCCESS(0),
    INVALID(1),
    EXPIRATED(2);

    private final Integer value;

    ValidateTokenResult(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
