/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 */

package com.isofh.service.model;


import com.isofh.service.constant.ServiceConst;
import org.json.JSONArray;

public class HisArray {

    private Integer code;

    private JSONArray data;

    private String comment;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return Integer.valueOf(ServiceConst.CODE_OK).equals(code);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
