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
 * 1 Thong bao binh thuong<br>
 * 2 Tin nhan khuyen mai<br>
 * 4 Tinh nang moi <br>
 */
public enum AdvertiseType {
    NOTIFICATION(1),
    PROMOTION(2),
    NEW_FEATURES(4);


    private Integer value;

    AdvertiseType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
