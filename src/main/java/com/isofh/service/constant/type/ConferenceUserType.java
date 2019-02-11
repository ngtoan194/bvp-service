package com.isofh.service.constant.type;

/**
 * 1 khach moi<br>
 * 2 nhan vien sale
 */
public enum ConferenceUserType {
    GUEST(1),
    SALE(2);

    private final int value;

    ConferenceUserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
