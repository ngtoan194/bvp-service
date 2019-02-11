package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.ONLINE_EXCHANGE)
public class OnlineExchangeEntity {

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
     * chu de giao luu truc tuyen
     */
    @Column(name = "col_topic")
    private String topic;
    /**
     * trang thai giao luu truc tuyen
     */
    @Column(name = "col_active", nullable = false, columnDefinition = "int(11) default 1")
    private int active = 1;
    /**
     * dia chi dien ra
     */
    @Column(name = "col_location")
    private String location;
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
     * lien ket chu de giao luu truc tuyen
     */
    @Column(name = "col_link_alias")
    private String linkAlias;
    /**
     * Danh sach cac question cua giao luu truc tuyen
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "onlineExchange")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<OnlineExchangeQuestionEntity> onlineExchangeQuestions = new HashSet<>();
    /**
     * Danh sach bac si cua giao luu truc tuyen
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_doctor_online_exchange",
            joinColumns = {@JoinColumn(name = "col_doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_online_exchange_id")})
    @JsonIgnore
    private Set<UserEntity> doctors = new HashSet<>();

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public Set<OnlineExchangeQuestionEntity> getOnlineExchangeQuestions() {
        return onlineExchangeQuestions;
    }

    public void setOnlineExchangeQuestions(Set<OnlineExchangeQuestionEntity> onlineExchangeQuestions) {
        this.onlineExchangeQuestions = onlineExchangeQuestions;
    }

    public Set<UserEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<UserEntity> doctors) {
        this.doctors = doctors;
    }
}
