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
 * Data cua website nao <br>
 * 1 Benh Vien Phoi <br>
 * 2 Hoi Lao <br>
 * 4 chuong trinh phong chong lao <br>
 */
public enum WebType {

    BVP(1),
    HOILAO(2),
    CHONG_LAO(4);

    private final Integer value;

    WebType(Integer value) {
        this.value = value;
    }

    public static WebType fromValue(int value) {
        if (Integer.valueOf(1).equals(value)) {
            return BVP;
        } else if (Integer.valueOf(2).equals(value)) {
            return HOILAO;
        } else if (Integer.valueOf(4).equals(value)) {
            return CHONG_LAO;
        }
        return BVP;
    }

    public Integer getValue() {
        return value;
    }
}
