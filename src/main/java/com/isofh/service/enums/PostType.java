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
 * Loai bai viet
 * 0 la loai cau hoi
 * 1 la loai bai viet bac si
 */
public enum PostType {

    QUESTION(0),
    ARTICAL(1);


    private final Integer value;

    PostType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
