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
 * Loai Tai lieu<br>
 * 1: Tai lieu<br>
 * 2: Van ban<br>
 */
public enum DocumentType {

    DOCUMENT(1),
    TEXT(2);

    private final Integer value;

    DocumentType(Integer value) {
        this.value = value;
    }

    public static DocumentType fromValue(int value) {
        if (Integer.valueOf(1).equals(value)) {
            return DOCUMENT;
        } else if (Integer.valueOf(2).equals(value)) {
            return TEXT;
        }
        return DOCUMENT;
    }

    public Integer getValue() {
        return value;
    }
}

