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
 * 0: Cho xac nhan
 * 1: Dat lich thanh cong
 * 2: Da xac nhan
 * 3: Da thanh toan
 * 4: Da gui mau
 * 5: Cho ket qua/ tuong duong nop tien tai chinh ke toan
 * 6: Da co ket qua
 * 7: Da tra ket qua
 */
public enum BookingMedicalTestHomeStatusType {

    WAIT_CONFIRM(0),
    SUCCESS(1),
    CONFIM(2),
    PAID(3),
    PUSH_SAMPLE(4),
    WAIT_RESULT(5),
    HAVE_RESULT(6),
    RETURN_RESULT(7);

    private final Integer value;

    BookingMedicalTestHomeStatusType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
