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
 * +) 1 = Bệnh viện <br>
 * +) 2 = Phòng khám <br>
 * +) 4 = Trung tâm y tế <br>
 */
public enum FacilityType {

    HOSPITAL(1),
    CLINIC(2),
    MEDICAL_CENTER(4);

    private final int value;

    FacilityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
