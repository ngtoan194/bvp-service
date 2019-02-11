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

public enum HttpMethodType {

    POST(0),
    GET(1);

    private final Integer type;

    HttpMethodType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
