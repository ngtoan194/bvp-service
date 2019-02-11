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
 * Phan quyen cua nguoi dung <br>
 * 1: user thong thuong<br>
 * 2: bac si <br>
 * 4: admin phoi <br>
 * 8: super admin <br>
 * 16: admin hoi lao <br>
 * 32: thanh vien co tra phi hoi lao <br>
 * 64: Ky thuat vien <br>
 * 128: admin chuong trinh phong chong lao<br>
 */
public enum UserType {

    USER(1),
    DOCTOR(2),
    ADMIN(4),
    SUPERADMIN(8),
    ADMIN_HOILAO(16),
    MEMBER_HOILAO(32),
    TECHNICIAN(64),
    ADMIN_CHONG_LAO(128);

    private final int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}