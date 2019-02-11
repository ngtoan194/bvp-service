package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.COMMENT)
public class CommentEntity {

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
     * noi dung comment
     */
    @Column(name = "col_content", columnDefinition = "TEXT")
    private String content;
    /**
     * anh comment
     */
    @Column(name = "col_images")
    private String images;
    /**
     * so luong like comment
     */
    @Column(name = "col_like_count", nullable = false, columnDefinition = "int(11) default 0")
    private int likeCount = 0;
    /**
     * comment giai phap
     * 1 yes
     * 0 no
     */
    @Column(name = "col_is_solution", nullable = false, columnDefinition = "int(11) default 0")
    private int isSolution = 0;
    /**
     * comment cua user nao
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_author_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity author;
    /**
     * post of cua comment nay
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_post_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private PostEntity post;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getIsSolution() {
        return isSolution;
    }

    public void setIsSolution(int isSolution) {
        this.isSolution = isSolution;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
