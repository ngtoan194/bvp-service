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
 * 0 comment vao bai post<br>
 * 1 chap nhan comment lam giai phap<br>
 * 2 tao subComment<br>
 * 3 thich bai post<br>
 * 4 thich comment<br>
 * 5 thich subComment<br>
 * 6 gan bai post cho bac si tra loi<br>
 * 7 thay doi thong tin lich kham<br>
 * 8 Tao bai post<br>
 * 9 ket qua kham benh<br>
 * 10 lich dat da co so kham<br>
 * 11 Bac si tao 1 bai viet<br>
 * 12 Cancel Booking <br>
 * 13 Update Booking <br>
 * 14 Create Booking <br>
 */
public enum NotificationType {

    COMMENT(0),
    ACCEPT_AS_SOLUTION(1),
    SUB_COMMENT(2),
    LIKE_POST(3),
    LIKE_COMMENT(4),
    LIKE_SUBCOMMENT(5),
    ASSIGN_POST(6),
    CHANGE_BOOKING_INFO(7),
    CREATE_POST(8),
    BOOKING_RESULT(9),
    BOOKING_HAVE_NUMBER(10),
    CREATE_ARTICLE(11),
    CANCEL_BOOKING(12),
    UPDATE_BOOKING(13),
    CREATE_BOOKING(14);

    private final Integer value;

    NotificationType(Integer value) {
        this.value = value;
    }

    public static NotificationType fromValue(int value) {
        if (Integer.valueOf(0).equals(value)) {
            return COMMENT;
        } else if (Integer.valueOf(1).equals(value)) {
            return ACCEPT_AS_SOLUTION;
        } else if (Integer.valueOf(2).equals(value)) {
            return SUB_COMMENT;
        } else if (Integer.valueOf(3).equals(value)) {
            return LIKE_POST;
        } else if (Integer.valueOf(4).equals(value)) {
            return LIKE_COMMENT;
        } else if (Integer.valueOf(5).equals(value)) {
            return LIKE_SUBCOMMENT;
        } else if (Integer.valueOf(6).equals(value)) {
            return ASSIGN_POST;
        } else if (Integer.valueOf(7).equals(value)) {
            return CHANGE_BOOKING_INFO;
        } else if (Integer.valueOf(8).equals(value)) {
            return CREATE_POST;
        } else if (Integer.valueOf(9).equals(value)) {
            return BOOKING_RESULT;
        } else if (Integer.valueOf(10).equals(value)) {
            return BOOKING_HAVE_NUMBER;
        } else if (Integer.valueOf(11).equals(value)) {
            return CREATE_ARTICLE;
        } else if (Integer.valueOf(12).equals(value)) {
            return CANCEL_BOOKING;
        } else if (Integer.valueOf(13).equals(value)) {
            return UPDATE_BOOKING;
        } else if (Integer.valueOf(14).equals(value)) {
            return CREATE_BOOKING;
        }

        return COMMENT;
    }

    public Integer getValue() {
        return value;
    }
}
