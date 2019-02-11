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
 * int (OS (Oral Symposium) <br>
 * Poster <br>
 * Ca bệnh lâm sàng <br>
 * PL (phiên toàn thể))<br>
 */

public enum SessionType {
    OS(1),
    POSTER(2),
    CLINICAL(4),
    PL(8);


    private final Integer value;

    SessionType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
