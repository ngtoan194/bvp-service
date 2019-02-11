package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.API_LOG)
public class ApiLogEntity {

    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    public LocalDateTime createdDate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    public LocalDateTime updatedDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;
    @Column(name = "col_method", columnDefinition = "TEXT")
    private String method;
    @Column(name = "col_uri", columnDefinition = "TEXT")
    private String uri;
    @Column(name = "col_query_string", columnDefinition = "TEXT")
    private String queryString;
    @Column(name = "col_remote_addr", columnDefinition = "TEXT")
    private String remoteAddr;
    @Column(name = "col_user_agent", columnDefinition = "TEXT")
    private String userAgent;
    @Column(name = "col_cookie", columnDefinition = "TEXT")
    private String cookie;
    @Column(name = "col_body", columnDefinition = "LONGTEXT")
    private String body;
    @Column(name = "col_forward", columnDefinition = "TEXT")
    private String forward;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }
}
