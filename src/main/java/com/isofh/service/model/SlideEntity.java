package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.SLIDE)
public class SlideEntity {

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
     * Ten cua slide
     */
    @Column(name = "col_name")
    private String name;
    /**
     * Trang thai active cua slide
     */
    @Column(name = "col_active", nullable = false, columnDefinition = "int(11) default 0")
    private int active = 1;
    /**
     * Trang thai tu dong chuyen giua cac slide
     */
    @Column(name = "col_auto_play", nullable = false, columnDefinition = "int(11) default 1")
    private int autoPlay = 1;
    /**
     * Trang thai tu dong chuyen giua cac slide
     */
    @Column(name = "col_interval_time", nullable = false, columnDefinition = "int(11) default 3000")
    private int intervalTime = 3000;
    /**
     * slide nay cua web nao, phoi, hoilao, hay phong chong
     */
    @Column(name = "col_web_type", nullable = false, columnDefinition = "int(11) default 1")
    private int webType = 1;
    private String uid;
    /**
     * danh sach cac slide chua slide item
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_slide_item_slide",
            joinColumns = {@JoinColumn(name = "col_slide_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_slide_id")})
    @JsonIgnore
    private Set<SlideItemEntity> slideItems = new HashSet<>();

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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(int autoPlay) {
        this.autoPlay = autoPlay;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Set<SlideItemEntity> getSlideItems() {
        return slideItems;
    }

    public void setSlideItems(Set<SlideItemEntity> slideItems) {
        this.slideItems = slideItems;
    }

    public int getWebType() {
        return webType;
    }

    public void setWebType(int webType) {
        this.webType = webType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
