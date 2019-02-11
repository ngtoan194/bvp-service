package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.SESSION)
public class SessionEntity {

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

    /**
     * ten phien
     */
    @Column(name = "col_name")
    private String name;

    /**
     * thoi gian bat dau
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_start_time")
    private LocalDateTime startTime;

    /**
     * thoi gian ket thuc
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_end_time")
    private LocalDateTime endTime;

    /**
     * kieu phien {@link com.isofh.service.enums.SessionType}
     * OS(1),
     * POSTER(2),
     * CLINICAL(4),
     * PL(8);
     */
    @Column(name = "col_type", nullable = false, columnDefinition = "int(11) default 0")
    private int type = 0;

    /**
     * khu vuc to chuc
     */
    @Column(name = "col_location")
    private String location;
    /**
     * chu de phien nay
     */
    @Column(name = "col_topic")
    private String topic;
    /**
     * chu toa phien nay
     */
    @Column(name = "col_owners")
    private String owners;
    /**
     * chu toa phien nay
     */
    @Column(name = "col_secretaries")
    private String secretaries;
    /**
     * chu toa phien nay
     */
    @Column(name = "col_reports")
    private String reports;

    private String uid;
    /**
     * OnlineExchange of cua question nay
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_conference_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private ConferenceEntity conference;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getSecretaries() {
        return secretaries;
    }

    public void setSecretaries(String secretaries) {
        this.secretaries = secretaries;
    }

    public String getReports() {
        return reports;
    }

    public void setReports(String reports) {
        this.reports = reports;
    }

    public ConferenceEntity getConference() {
        return conference;
    }

    public void setConference(ConferenceEntity conference) {
        this.conference = conference;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
