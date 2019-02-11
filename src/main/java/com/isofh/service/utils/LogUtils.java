/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package com.isofh.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    private static final Object lock = new Object();
    private static volatile LogUtils instance;
    private Logger log;

    private LogUtils() {
        log = LoggerFactory.getLogger(LogUtils.class);
    }

    public static LogUtils getInstance() {
        if (instance == null) {
            synchronized (lock) {    // While we were waiting for the lock, another
                if (instance == null) {
                    instance = new LogUtils();
                }
            }
        }
        return instance;
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void info(Object obj) {
        log.info(GsonUtils.toString(obj));
    }

    public void error(String msg) {
        log.error(msg);
    }

    public void error(Exception ex) {
        try {
            StringBuilder sbError = new StringBuilder();
            sbError.append(ex.toString());
            if (ex.getStackTrace() != null) {
                for (StackTraceElement element : ex.getStackTrace()
                ) {
                    sbError.append("\n" + element.toString());
                }
            }
            log.error(sbError.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
