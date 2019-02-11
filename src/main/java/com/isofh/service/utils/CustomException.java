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

public class CustomException extends Exception {

    private Integer code;
    private Object data;

    public CustomException(Exception ex) {
        super(ex);
    }

    public CustomException(int code, Exception ex) {
        super(ex);
        this.code = code;
    }

    public CustomException(int code, String message) {
        super(new Exception(message));
        this.code = code;
    }
    public CustomException(String message) {
        super(new Exception(message));
        this.code = 500;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
