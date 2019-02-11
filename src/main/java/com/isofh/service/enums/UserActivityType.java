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
 * 0 - Tao bai viet
 * 1 - Thich bai viet
 * 2 - Comment bai viet
 * 3 - Comment vao 1 comment
 * 4 - Thich Comment
 * 5 - Thich Comment LV2
 * 6 - Theo doi bai viet
 * 7 - Sua bai viet
 * 8 - Sua comment
 * 9 - Sua comment lv2
 * 10 - Chon comment lam giai phap
 * 11 - Bac si tao bai viet
 */
public enum UserActivityType {

    CREATE_POST(0),
    LIKE_POST(1),
    COMMENT(2),
    SUB_COMMENT(3),
    LIKE_COMMENT(4),
    LIKE_SUB_COMMENT(5),
    FOLLOW_POST(6),
    UPDATE_POST(7),
    UPDATE_COMMENT(8),
    UPDATE_SUB_COMMENT(9),
    ACCEPT_SOLUTION(10),
    CREATE_ARTICLE(11);

    private final int value;

    UserActivityType(int value) {
        this.value = value;
    }

    public static UserActivityType fromValue(int value) {
        if (Integer.valueOf(0).equals(value)) {
            return CREATE_POST;
        } else if (Integer.valueOf(value).equals(1)) {
            return LIKE_POST;
        } else if (Integer.valueOf(2).equals(value)) {
            return COMMENT;
        } else if (Integer.valueOf(value).equals(value)) {
            return SUB_COMMENT;
        } else if (Integer.valueOf(4).equals(value)) {
            return LIKE_COMMENT;
        } else if (Integer.valueOf(5).equals(value)) {
            return LIKE_SUB_COMMENT;
        } else if (Integer.valueOf(6).equals(value)) {
            return FOLLOW_POST;
        } else if (Integer.valueOf(7).equals(value)) {
            return UPDATE_POST;
        } else if (Integer.valueOf(8).equals(value)) {
            return UPDATE_COMMENT;
        } else if (Integer.valueOf(value).equals(9)) {
            return UPDATE_SUB_COMMENT;
        } else if (Integer.valueOf(10).equals(value)) {
            return ACCEPT_SOLUTION;
        } else if (Integer.valueOf(11).equals(value)) {
            return CREATE_ARTICLE;
        }
        return CREATE_POST;
    }

    public int getValue() {
        return value;
    }
}
