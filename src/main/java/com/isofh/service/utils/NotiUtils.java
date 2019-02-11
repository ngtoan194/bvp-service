/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 */

package com.isofh.service.utils;

import com.isofh.service.constant.NotiConst;
import com.isofh.service.constant.ServiceConst;
import com.isofh.service.enums.HttpMethodType;

import java.util.HashMap;
import java.util.Map;

public class NotiUtils {

    public static void sendTopic(int type, long id) {
        Thread thread = new Thread(() -> {

            try {
                Map<String, Object> mapData = new HashMap<>();
                mapData.put("type", String.valueOf(type));
                mapData.put("id", id);

                Map<String, Object> mapNoti = new HashMap<>();
                mapNoti.put("body", NotiConst.DEFAULT_BODY_NOTIFICATION);
                mapNoti.put("title", NotiConst.DEFAULT_TITLE_NOTIFICATION);
                mapNoti.put("sound", NotiConst.DEFAULT_SOUND);

                Map<String, Object> mapBodys = new HashMap<>();
                mapBodys.put("to", NotiConst.TOPIC_NAME);
                mapBodys.put("data", mapData);
                mapBodys.put("notification", mapNoti);

                Map<String, Object> mapHeaders = new HashMap<>();
                mapHeaders.put("Authorization", "key=" + NotiConst.FIREBASE_KEY);

                String result = NetworkUtils.callService(ServiceConst.FIREBASE_URL, HttpMethodType.POST, null, mapHeaders, mapBodys, null);
                LogUtils.getInstance().info("send noti result " + result);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        thread.start();
    }

}

