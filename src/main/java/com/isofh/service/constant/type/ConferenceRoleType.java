package com.isofh.service.constant.type;

/**
 * vai tro nguoi dung trong hoi nghi
 * 1 vip
 * 2 chu toa
 * 4 thu ky
 * 8 bao cao vien
 * 16 ban to chuc
 * 32 khach moi
 */
public enum ConferenceRoleType {
    VIP(1),
    PRECIDENT(2),
    SECRETARY(4),
    REPORTOR(8),
    ORGANIZER(16),
    GUEST(32);

    private final int value;

    ConferenceRoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
