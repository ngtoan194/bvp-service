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


import com.isofh.service.constant.ServiceConst;
import com.isofh.service.enums.HttpMethodType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {

    public static String callService(String url, HttpMethodType methodType, Map<String, Object> pathParams,
                                     Map<String, Object> headers, Object body, Map<String, Object> queryParams) {
        try {
            if (MapUtils.isNullOrEmpty(headers)) {
                headers = new HashMap<>();
            }
            if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
                headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            headers.forEach((s, object) ->
                    httpHeaders.set(s, object.toString())
            );

            HttpEntity<Object> entity = new HttpEntity<>(GsonUtils.toString(body), httpHeaders);
            LogUtils.getInstance().info(GsonUtils.toString(body));
            RestTemplate restTemplate = new RestTemplate();
            String result = "{}";
            switch (methodType) {
                case GET:

                    UriComponentsBuilder builder = UriComponentsBuilder
                            .fromUriString(url);
                    if (!MapUtils.isNullOrEmpty(queryParams)) {
                        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                            builder.queryParam(entry.getKey(), entry.getValue());
                        }
                    }

                    if (pathParams != null) {
                        result = restTemplate.getForObject(builder.build(pathParams), String.class);
                    } else {
                        result = restTemplate.getForObject(builder.toUriString(), String.class);
                    }

                    break;
                case POST:
                    if (pathParams != null) {
                        result = restTemplate.postForObject(url, entity, String.class, pathParams);
                    } else {
                        result = restTemplate.postForObject(url, entity, String.class);
                    }
                    break;
                default:
                    break;
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String postHis(String path, Map<String, Object> pathParams, Map<String, Object> headers, Object body, Map<String, Object> queryParams) {
        return callService(ServiceConst.HIS_URL + "/" + path, HttpMethodType.POST, pathParams, headers, body, queryParams);
    }

    public static String getHis(String path, Map<String, Object> pathParams, Map<String, Object> headers, Map<String, Object> queryParams) {
        return callService(ServiceConst.HIS_URL + "/" + path, HttpMethodType.GET, pathParams, headers, null, queryParams);
    }

}
