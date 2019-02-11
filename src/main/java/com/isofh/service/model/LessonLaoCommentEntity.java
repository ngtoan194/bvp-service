package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.LESSON_LAO_COMMENT)
public class LessonLaoCommentEntity {

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
     * Nguoi dung tao comment nay
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_author_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity author;
    /**
     * Nguoi dung tao comment nay
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_lesson_lao_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private LessonLaoEntity lessonLao;
    @Column(name = "col_content", columnDefinition = "TEXT")
    private String content;

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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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

    public LessonLaoEntity getLessonLao() {
        return lessonLao;
    }

    public void setLessonLao(LessonLaoEntity lessonLao) {
        this.lessonLao = lessonLao;
    }
}
