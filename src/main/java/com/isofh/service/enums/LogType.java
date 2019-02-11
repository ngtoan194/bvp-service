/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 */

package com.isofh.service.enums;

/**
 * 1 them trang<br>
 * 2 sua trang<br>
 * 4 xoa trang<br>
 */
public enum LogType {

    CREATE_PAGE(1),
    UPDATE_PAGE(1 << 1),
    DELETE_PAGE(1 << 2),
    PAGE_CREATE_ALBUM(1 << 3),
    PAGE_UPDATE_ALBUM(1 << 4),
    PAGE_DELETE_ALBUM(1 << 5),
    PAGE_CREATE_NEWS(1 << 6),
    PAGE_UPDATE_NEWS(1 << 7),
    PAGE_DELETE_NEWS(1 << 8);


    private final Integer value;

    LogType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
