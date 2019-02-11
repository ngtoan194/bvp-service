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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class ResourceUtils {

    private static final Object lock = new Object();
    private static volatile ResourceUtils instance;

    private ResourceUtils() {

    }

    public static ResourceUtils getInstance() {
        ResourceUtils r = instance;
        if (r == null) {
            synchronized (lock) {    // While we were waiting for the lock, another
                r = instance;        // thread may have instantiated the object.
                if (r == null) {
                    r = new ResourceUtils();
                    instance = r;
                }
            }
        }
        return r;
    }

    /**
     * Get value from a file *.properties and a key
     *
     * @param propName
     * @param key
     * @return default is empty
     */
    public String getValue(String propName, String key) {
        try {
            return getProperties(propName).getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get a Properties Object by name
     *
     * @param propName
     * @return null if not found
     */
    public Properties getProperties(String propName) {
        try {
            Properties prop = new Properties();
            InputStream in = getClass().getClassLoader().getResourceAsStream(propName);
            prop.load(new InputStreamReader(in, Charset.forName("UTF-8")));
            in.close();
            return prop;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get value from properties by key
     *
     * @param properties
     * @param key
     * @return null if not found
     */
    public String getPropertyValue(Properties properties, String key) {
        return getPropertyValue(properties, key, null);
    }

    /**
     * Get value from properties by key
     *
     * @param properties
     * @param key
     * @param defaultValue
     * @return defaultValue if not found
     */
    public String getPropertyValue(Properties properties, String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
