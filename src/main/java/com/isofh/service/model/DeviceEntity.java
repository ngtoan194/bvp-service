package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import com.isofh.service.enums.DeviceType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.DEVICE)
public class DeviceEntity {

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
    /// <summary>
    /// 0: IOS      1: Android  2: WEB
    /// </summary>
    @Expose
    @Column(name = "col_os")
    private int os = DeviceType.WEB.getValue();
    /**
     * Id duy nhat cua device
     */
    @Expose
    @Column(name = "col_device_id")
    private String deviceId;
    /**
     * Token sinh ra de gui notification
     */
    @Expose
    @Column(name = "col_token")
    private String token;
    /**
     * Danh dau device nay duoc login bang tai khoan cua ktv
     */
    @Column(name = "col_logged_by_technician")
    private int loggedByTechnician;
    /**
     * User dang nhap vao device nay
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_user_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity user;

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


    public int getOs() {
        return os;
    }

    public void setOs(int os) {
        this.os = os;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLoggedByTechnician() {
        return loggedByTechnician;
    }

    public void setLoggedByTechnician(int loggedByTechnician) {
        this.loggedByTechnician = loggedByTechnician;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
