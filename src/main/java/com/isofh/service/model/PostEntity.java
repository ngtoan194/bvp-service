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

@Entity(name = TableConst.POST)
public class PostEntity {

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
     * tieu de bai viet
     */
    @Column(name = "col_title")
    private String title;

    /**
     * noi dung bai viet
     */
    @Column(name = "col_content", columnDefinition = "TEXT")
    private String content;

    /**
     * anh cua bai viet
     */
    @Column(name = "col_images", columnDefinition = "TEXT")
    private String images;

    /**
     * so luong like bai viet
     */
    @Column(name = "col_like_count", nullable = false, columnDefinition = "int(11) default 0")
    private int likeCount = 0;

    /**
     * so luong comment bai viet
     */
    @Column(name = "col_comment_count", nullable = false, columnDefinition = "int(11) default 0")
    private int commentCount = 0;

    /**
     * lien ket cua bai viet
     */
    @Column(name = "col_link_alias")
    private String linkAlias;

    /**
     * Loai vai viet
     * 0 Cau hoi
     * 1 Bai viet bac si
     */
    @Column(name = "col_post_type", nullable = false, columnDefinition = "int(11) default 0")
    private int postType = 0;

    /**
     * Bai post nay da dc assign cho bac si chua
     * 0 no
     * 1 yes
     */
    @Column(name = "col_is_assigned", nullable = false, columnDefinition = "int(11) default 0")
    private int isAssigned = 0;

    /**
     * bai viet an danh
     * 0 no
     * 1 yes
     */
    @Column(name = "col_is_private", nullable = false, columnDefinition = "int(11) default 0")
    private int isPrivate = 0;

    /**
     * Bac si da tra loi bai viet duoc gan chua
     * 0 no
     * 1 yes
     */
    @Column(name = "col_is_answered", nullable = false, columnDefinition = "int(11) default 0")
    private int isAnswered = 0;

    /**
     * Bai viet cua bac si da duoc duyet chua
     * 0 no
     * 1 yes
     */
    @Column(name = "col_is_published", nullable = false, columnDefinition = "int(11) default 0")
    private int isPublished = 0;
    /**
     * danh sach cac tag trong post
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,

                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_post_tag",
            joinColumns = {@JoinColumn(name = "col_post_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_tag_id")})
    @JsonIgnore
    private Set<TagEntity> tags = new HashSet<>();
    /**
     * Danh sach department cua post
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_department_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private DepartmentEntity department;
    /**
     * Bac si dc asignee de tra loi cau hoi
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_assignee_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity assignee;
    /**
     * bai viet or cau hoi cua user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_author_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity author;
    /**
     * Danh sach cac comment cua post
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "post")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<CommentEntity> comments = new HashSet<>();
    /**
     * danh sach cac tag ma user follow
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_user_follow_post",
            joinColumns = {@JoinColumn(name = "col_post_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_user_id")})
    @JsonIgnore
    private Set<UserEntity> followedUsers = new HashSet<>();
    @Column(name = "col_liked_user_ids", columnDefinition = "TEXT")
    private String likedUserIds;
    @Column(name = "col_followed_user_ids", columnDefinition = "TEXT")
    private String followedUserIds;
    private String uid;

    @OneToOne()
    @JoinColumn(name = "col_first_doctor_comment_id")
    @NotFound(
            action = NotFoundAction.IGNORE)
    @JsonIgnore
    private CommentEntity firstDoctorComment;

    @PrePersist
    public void prePersist() {
        if (createdDate == null)
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public int getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(int isAssigned) {
        this.isAssigned = isAssigned;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public int getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(int isAnswered) {
        this.isAnswered = isAnswered;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public int getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(int isPublished) {
        this.isPublished = isPublished;
    }


    public UserEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(UserEntity assignee) {
        this.assignee = assignee;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    public String getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(String likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public String getFollowedUserIds() {
        return followedUserIds;
    }

    public void setFollowedUserIds(String followedUserIds) {
        this.followedUserIds = followedUserIds;
    }

    public Set<UserEntity> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(Set<UserEntity> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public CommentEntity getFirstDoctorComment() {
        return firstDoctorComment;
    }

    public void setFirstDoctorComment(CommentEntity firstDoctorComment) {
        this.firstDoctorComment = firstDoctorComment;
    }
}
