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
 * Loai trang hien thi <br>
 * 1 loai hien thi tin tuc <br>
 * 2 loai hien thi thong tin khoa, phong <br>
 * 3 Tao file tu tin tuc <br>
 */
public enum PageType {

    NEWS(1),
    DEPARTMENT(2),
    FROM_FILE(3);

    private final int value;

    PageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
