/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package com.isofh.service.constant;

public class ServiceConst {

    /**
     * CODE = 0 thao tac vs his thanh cong
     */
    public static final int CODE_OK = 0;

    public static final String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";

    /**
     * base url of his
     */
    public static final String HIS_URL = "https://dhy.test.isofh.vn/isofh/services";

    /**
     * sync doctors
     */
    public static final String SYNC_DOCTOR = "list/getDoctors";

    /**
     * sync specialist
     */
    public static final String SYNC_SPECIALIST = "list/getSpecialists";

    /**
     * sync room
     */
    public static final String SYNC_ROOM = "list/getRooms";

    /**
     * sync service
     */
    public static final String SYNC_SERVICE = "list/getServices";

    /**
     * sync service
     */
    public static final String SYNC_DEPARTMENT = "list/getDepartments";


    /**
     * Linh de hien thi anh his
     */
    public static final String HIS_IMAGES = "https://dhy.test.isofh.vn/images?id=";

    /**
     * Dong bo country
     */
    public static final String SYNC_COUNTRY = "list/getCountrys";

    /**
     * Dong bo province
     */
    public static final String SYNC_PROVINCE = "list/getProvinces";

    /**
     * Dong bo district
     */
    public static final String SYNC_DISTRICT = "list/getDistricts";

    /**
     * Dong bo zone
     */
    public static final String SYNC_ZONE = "list/getZones";

    /**
     * Kiem tra trang thai thanh toan cua 1 tai khoan
     */
    public static final String GET_STATUS_PAID = "patient/getStatusPaid/{value}";

    /**
     * Lay danh sach lich su vao vien theo value(HisPatientId cu)
     */
    public static final String GET_BY_VALUE = "patient/getByValue/{value}";

    /**
     * Lay chi tiet theo lich su vao vien
     */
    public static final String GET_DETAIL_PATIENT_HISTORY = "patient/getDetailPatientHistory/{patientHistoryId}";

    /**
     * lay ket qua theo lich su vao vien
     */
    public static final String GET_RESULT_PATIENT_HISTORY = "patient/getResultPatientHistory/{patientHistoryId}";

    // Checkin thong tin dat kham day len vien
    public static final String CHECK_IN = "patient/checkIn";
}

