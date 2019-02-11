package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.COURSE_ITEM_COMMENT)
public class CourseItemCommentEntity {

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
     * Nguoi comment
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_author_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity author;
    /**
     * noi dung comment
     */
    @Column(name = "col_content", columnDefinition = "TEXT")
    private String content;
    private String uid;
    /**
     * comemnt nay thuoc khoa bai hoc nao
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_course_item_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private CourseItemEntity courseItem;

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

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CourseItemEntity getCourseItem() {
        return courseItem;
    }

    public void setCourseItem(CourseItemEntity courseItem) {
        this.courseItem = courseItem;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
