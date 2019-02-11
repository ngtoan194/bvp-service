package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.USER_ACCESS)
public class UserAccessEntity {

    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_created_date")
    public LocalDateTime createdDate;

    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_updated_date")
    public LocalDateTime updatedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;
    @Column(name = "col_deleted", nullable = false, columnDefinition = "int(11) default 0")
    private int deleted;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @Column(name = "name", columnDefinition = "LONGTEXT")
    private String name;

    @Column(name = "col_device_id")
    private String deviceId;

    /**
     * Lan cuoi cung deviceId nay request
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_last_request")
    private LocalDateTime lastRequest;

    /**
     * Lan cuoi cung device id nay active
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_last_actvie")
    private LocalDateTime lastActive;

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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(LocalDateTime lastRequest) {
        this.lastRequest = lastRequest;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }
}
