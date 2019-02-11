package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.SLIDE_PLACE)
public class SlidePlaceEntity {

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
     * Ten cua slide place
     */
    @Column(name = "col_name")
    private String name;
    /**
     * slide place nay cua web nao, phoi, hoilao, hay phong chong
     */
    @Column(name = "col_web_type", nullable = false, columnDefinition = "int(11) default 1")
    private int webType = 1;
    /**
     * Slide Entity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_slide_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private SlideEntity slide;
    private String uid;

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

    public SlideEntity getSlide() {
        return slide;
    }

    public void setSlide(SlideEntity slide) {
        this.slide = slide;
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
