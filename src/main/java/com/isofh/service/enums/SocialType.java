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

public enum SocialType {
    DEFAULT(0),
    FACEBOOK(1),
    GOOGLE(2);


    private final Integer value;

    SocialType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
