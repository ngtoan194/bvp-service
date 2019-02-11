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

import java.time.format.DateTimeFormatter;

public class AppConst {
    public static final Integer SERVICE_VERSION = 1;
    public static final String MIN_PRICE = "MinPrice";
    public static final String TRANSPORT_FEE = "TransportFee";
    public static final String SUPPORT_PHONE = "SupportPhone";

    /**
     * 1 hour to milisecond
     */
    public static final Integer ONE_HOUR = 60 * 60 * 1000;

    /**
     * 1 day to milisecond
     */
    public static final Integer ONE_DAY = 24 * ONE_HOUR;

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";


    public static final String APP_BVP_NAME = "BV PHỔI TRUNG ƯƠNG";
    public static final String APP_HL_NAME = "HỘI LAO VÀ BỆNH PHỔI VIỆT NAM";
    public static final String APP_CL_NAME = "CHƯƠNG TRÌNH CHỐNG LAO QUỐC GIA";

    public static final String CONTACT_BVP_EMAIL = "bvptw@gmail.com";
    public static final String CONTACT_HL_EMAIL = "hlbpvn@gmail.com";
    public static final String CONTACT_CL_EMAIL = "clqg@gmail.com";

    public static final String CC_EMAIL = "trang.dtt@isofh.com";

    /**
     * Link ung dung treen google store
     */
    public static final String LINK_ANDROID = "https://play.google.com/store";

    /**
     * Link ung dugn tren apple store
     */
    public static final String LINK_IOS = "https://www.apple.com/lae/ios/app-store";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(AppConst.DATE_TIME_FORMAT);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT);

    /**
     * 1 hour to milisecond
     */
    public static final long ONE_MINUTE_MILS = (long) 60 * 1000;

    /**
     * 1 hour to milisecond
     */
    public static final long ONE_HOUR_MILS = (long) 60 * ONE_MINUTE_MILS;

    /**
     * 1 hour IN MINUTE
     */
    public static final long ONE_HOUR_IN_MINUTE = (long) 60;

    /**
     * 1 day to milisecond
     */
    public static final long ONE_DAY_MILS = 24 * ONE_HOUR_MILS;

    public static final String FILE_HDSD = "/file/downloadFile/huong_dan_su_dung.pdf";

    public static final String SERVICE = "http://123.24.206.9:8983";

    public static final String ISOFH_CARE_SERVICE = "http://27.72.105.49:8382";

    public static final String DATA_DIR = "/mnt/apps/bvp_upgrade";
    public static final String DATA_FILES = DATA_DIR + "/new_files";
    public static final String DATA_IMAGES = DATA_DIR + "/new_images";
    public static final String DEFAULT_AVATAR = "/images/default.png";


    public static final String WEB_ADDRESS_HOILAO = "http://123.24.206.9:8884";

    public static final String WEB_ADDRESS_CHONGLAO = "http://123.24.206.9:8384";

    public static final String WEB_ADDRESS = "http://123.24.206.9:8384";
}