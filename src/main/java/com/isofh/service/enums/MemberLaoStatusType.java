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
 * Trang thai cua thanh vien hoi lao
 */
public enum MemberLaoStatusType {
    WAIT_COMFIRM(1),
    INACTIVE(4),
    ACTIVE(8);


    private int value;

    MemberLaoStatusType(int value) {
        this.value = value;
    }

    public static MemberLaoStatusType fromValue(int value) {
        if (Integer.valueOf(1).equals(value)) {
            return WAIT_COMFIRM;
        } else if (Integer.valueOf(4).equals(value)) {
            return INACTIVE;
        } else if (Integer.valueOf(8).equals(value)) {
            return ACTIVE;
        }
        return WAIT_COMFIRM;
    }

    public int getValue() {
        return value;
    }
}
