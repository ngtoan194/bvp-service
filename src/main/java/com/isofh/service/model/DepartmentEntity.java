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

@Entity(name = TableConst.DEPARTMENT)
public class DepartmentEntity {

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
     * Ten cua Khoa
     */
    @Column(name = "col_name")
    private String name;
    @Column(name = "col_link_alias")
    private String linkAlias;
    /**
     * Khoa nay hien thi public hay khong
     */
    @Column(name = "col_is_public", nullable = false, columnDefinition = "int(11) default 0")
    private int isPublic;
    /**
     * thu tu sap xep cua khoa
     */
    @Column(name = "col_index", nullable = false, columnDefinition = "int(11) default 0")
    private int index;
    /**
     * Danh sach cac department co trong post
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "department")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<PostEntity> posts = new HashSet<>();
    /**
     * Danh sach cac comment cua bai hoc
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "department")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<UserEntity> users = new HashSet<>();
    /**
     * uid tu he thong cu, dung de join du lieu sau khi merge
     */
    private String uid;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
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

    public Set<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostEntity> posts) {
        this.posts = posts;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
