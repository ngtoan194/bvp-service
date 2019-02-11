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
 * Kieu su dung thuoc <br>
 * 1: An<br>
 * 2: Uong<br>
 * 4: Tiem<br>
 */
public enum InjectType {

    EAT(1),
    DRINK(2),
    INJECT(4);

    private final int value;

    InjectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
