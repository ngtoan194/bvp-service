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
 * -1 Da bi huy
 * 0 cho xac nhan
 * 1 xac nhan
 * 2 co so kham
 * 3 da thanh toan
 * 4 da co ket qua
 */
public enum BookingHospitalStatusType {

    CANCEL(-1),
    WAIT_CONFIRM(0),
    CONFIRM(1),
    HAVE_NUMBER(2),
    PAID(3),
    HAVE_RESULT(4);

    private final Integer value;

    BookingHospitalStatusType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
