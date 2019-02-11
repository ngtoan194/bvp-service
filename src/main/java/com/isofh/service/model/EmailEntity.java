package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.EMAIL)
public class EmailEntity {

    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    public LocalDateTime createdDate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    public LocalDateTime updatedDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;
    /**
     * Danh sach cac email duoc gui, cach nhau boi dau ","
     */
    private String emails;

    /**
     * Noi email dung de cc
     */
    private String ccEmails;

    @Column(columnDefinition = "TEXT")
    private String result;
    /**
     * Tieu de cua Email
     */
    private String title;
    /**
     * Noi dung cua email
     */
    @Column(columnDefinition = "TEXT")
    private String content;
    /**
     * Trang thai gui cua email
     */
    private int sent = 0;
    /**
     * thoi gian bat dau phong van
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    private LocalDateTime timeInterview;

    public EmailEntity() {
    }

    public EmailEntity(String emails, String title, String content) {
        this.emails = emails;
        this.title = title;
        this.content = content;
    }

    public EmailEntity(String emails, String title, String content, String ccEmails) {
        this.emails = emails;
        this.title = title;
        this.content = content;
        this.ccEmails = ccEmails;
    }

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    public LocalDateTime getTimeInterview() {
        return timeInterview;
    }

    public void setTimeInterview(LocalDateTime timeInterview) {
        this.timeInterview = timeInterview;
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

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSent() {
        return sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public String getCcEmails() {
        return ccEmails;
    }

    public void setCcEmails(String ccEmails) {
        this.ccEmails = ccEmails;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
