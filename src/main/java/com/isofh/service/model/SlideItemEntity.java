package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.SLIDE_ITEM)
public class SlideItemEntity {

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
     * ten cua slide item
     */
    @Column(name = "col_name")
    private String name;
    /**
     * Link khi click vao slide
     */
    @Column(name = "col_href")
    private String href;
    /**
     * anh hien thi cua item nay
     */
    @Column(name = "col_image")
    private String image;
    /**
     * slide item nay cua web nao, phoi, hoilao, hay phong chong
     */
    @Column(name = "col_web_type", nullable = false, columnDefinition = "int(11) default 1")
    private int webType = 1;
    private String uid;
    /**
     * danh sach cac slide chua slideitem
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_slide_item_slide",
            joinColumns = {@JoinColumn(name = "col_slide_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_slide_item_id")})
    @JsonIgnore
    private Set<SlideEntity> slides = new HashSet<>();

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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<SlideEntity> getSlides() {
        return slides;
    }

    public void setSlides(Set<SlideEntity> slides) {
        this.slides = slides;
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
