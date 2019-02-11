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
 * Phan quyen cua admin <br>
 * 1: Quan ly tai khoan<br>
 * 2: Quan ly goc tri an<br>
 * 4: quan ly tu van online<br>
 * 8: quan ly menu, slide, page<br>
 * 16: quan ly tin nhan quang ba<br>
 * 32: quan dat kham<br>
 * 64: quan ly Tai lieu, khoa hoc<br>
 * 128: quan ly Thu vien Anh, Video<br>
 * 256: quan ly trao doi truc tuyen<br>
 * 512: quan ly trang chu<br>
 */
public enum AdminRoleType {

    ACCOUNT(1),
    CONTRIBUTION(2),
    CONSULTANT(4),
    MENU_SLIDE_PAGE(8),
    ADVERTISE(16),
    BOOKING(32),
    DOCUMENT_COURSE(64),
    PICTURE_VIDEO(128),
    ONLINE_EXCHANGE(256),
    MAIN_PAGE(512);
    private final int value;

    AdminRoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
