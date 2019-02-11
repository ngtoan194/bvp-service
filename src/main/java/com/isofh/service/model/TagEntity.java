package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.TAG)
public class TagEntity {

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
    @Column(name = "col_name")
    private String name;
    @Column(name = "col_link_alias")
    private String linkAlias;
    private String uid;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,

                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_user_tag",
            joinColumns = {@JoinColumn(name = "col_tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_user_id")})
    @JsonIgnore
    private Set<UserEntity> user = new HashSet<>();
    /**
     * danh sach cac tag ma post dung
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_post_tag",
            joinColumns = {@JoinColumn(name = "col_tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_post_id")})
    @JsonIgnore
    private Set<PostEntity> posts = new HashSet<>();

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

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public Set<UserEntity> getUser() {
        return user;
    }

    public void setUser(Set<UserEntity> user) {
        this.user = user;
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

    public Set<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostEntity> posts) {
        this.posts = posts;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
