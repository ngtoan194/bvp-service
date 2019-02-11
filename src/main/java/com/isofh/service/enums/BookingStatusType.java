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
 * -1 Da bi huy <br>
 * 0 cho xac nhan <br>
 * 2 co so kham<br>
 * 3 da thanh toan<br>
 * 4 da co ket qua<br>
 */
public enum BookingStatusType {

    CANCEL(-1),
    WAIT_CONFIRM(0),
    HAVE_NUMBER(2),
    PAID(3),
    HAVE_RESULT(4);

    private final Integer value;

    BookingStatusType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
