package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import com.isofh.service.enums.CourseLaoType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.LESSON_LAO)
public class LessonLaoEntity {

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
    /**
     * Khoa hoc ma bai hoc nay thuoc ve
     */
//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "tbl_course_lao_user_conference",
//            joinColumns = {@JoinColumn(name = "col_course_lao_id")},
//            inverseJoinColumns = {@JoinColumn(name = "col_user_conference_id")})
//    @JsonIgnore

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_course_lao_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private CourseLaoEntity courseLao;
    /**
     * file
     */
    @Column(name = "col_file")

    private String file;
    /**
     * Thoi gian bat dau bai hoc
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_start_time")
    private LocalDateTime startTime;
    /**
     * Thoi gian ket thuc bai hoc
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_end_time")
    private LocalDateTime endTime;
    /**
     * Gia khoa hoc
     */
    @Column(name = "col_link_youtube")
    private String linkYoutube;
    @Column(name = "col_images", columnDefinition = "TEXT")
    private String images;
    @Column(name = "col_date")
    private LocalDate date;
    /**
     * Loai bai hoc cap chung chi hay khong <br> {@link CourseLaoType}
     */
    @Column(name = "col_type", nullable = false, columnDefinition = "int(11) default 0")
    private int type;
    @Column(name = "col_name")
    private String name;
    private String uid;
    /**
     * Danh sach cac comments cua Bai hoc
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "lessonLao")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<LessonLaoCommentEntity> lessonLaoComments = new HashSet<>();

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<LessonLaoCommentEntity> getLessonLaoComments() {
        return lessonLaoComments;
    }

    public void setLessonLaoComments(Set<LessonLaoCommentEntity> lessonLaoComments) {
        this.lessonLaoComments = lessonLaoComments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public CourseLaoEntity getCourseLao() {
        return courseLao;
    }

    public void setCourseLao(CourseLaoEntity courseLao) {
        this.courseLao = courseLao;
    }
}
