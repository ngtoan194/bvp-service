package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import com.isofh.service.enums.CourseLaoType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.COURSELAO)
public class CourseLaoEntity {

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
     * Loai khoa hoc <br> {@link CourseLaoType}
     */
    @Column(name = "col_type", nullable = false, columnDefinition = "int(11) default 0")
    private int type;
    /**
     * anh dai dien
     */
    @Column(name = "col_image")
    private String image;
    /**
     * Thoi gian bat dau khoa hoc
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_start_time")
    private LocalDateTime startTime;
    /**
     * Thoi gian ket thuc khoa hoc
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_end_time")
    private LocalDateTime endTime;
    /**
     * Mo ta khoa hoc
     */
    @Column(name = "col_description", columnDefinition = "TEXT")
    private String description;
    /**
     * Gia khoa hoc
     */
    @Column(name = "col_price", nullable = false, columnDefinition = "bigint(20) default 0")
    private long price;
    @Column(name = "col_name")
    private String name;
    /**
     * Danh sach cac menu con ben trong
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "courseLao")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<CourseTestEntity> courseTests = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_course_lao_user_conference",
            joinColumns = {@JoinColumn(name = "col_course_lao_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_user_conference_id")})
    @JsonIgnore
    private Set<UserConferenceEntity> userConference = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "courseLao")
    @JsonIgnore

    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<UserCourseEntity> userCourseEntities = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_conference_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private ConferenceEntity conference;
    private String uid;

    /**
     * Danh sach cac user tham du khoa hoc nay
     */
    @Column(name = "col_user_ids")
    private String userIds;

    /**
     * Danh sach cac comment cua bai hoc
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "courseLao")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<LessonLaoEntity> lessonLaos = new HashSet<>();

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    public Set<UserCourseEntity> getUserCourseEntities() {
        return userCourseEntities;
    }

    public void setUserCourseEntities(Set<UserCourseEntity> userCourseEntities) {
        this.userCourseEntities = userCourseEntities;
    }

    public Set<UserConferenceEntity> getUserConference() {
        return userConference;
    }

    public void setUserConference(Set<UserConferenceEntity> userConference) {
        this.userConference = userConference;
    }

    public ConferenceEntity getConference() {
        return conference;
    }

    public void setConference(ConferenceEntity conference) {
        this.conference = conference;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CourseTestEntity> getCourseTests() {
        return courseTests;
    }

    public void setCourseTests(Set<CourseTestEntity> courseTests) {
        this.courseTests = courseTests;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Set<LessonLaoEntity> getLessonLaos() {
        return lessonLaos;
    }

    public void setLessonLaos(Set<LessonLaoEntity> lessonLaos) {
        this.lessonLaos = lessonLaos;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
}
