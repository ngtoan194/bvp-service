package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import com.isofh.service.enums.UserType;
import com.isofh.service.enums.WebType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.MENU)
public class MenuEntity {

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
     * trang thai deleted cua doi tuong
     */
    @Column(name = "col_deleted")
    private int deleted = 0;
    /**
     * Href khi click vao menu nay
     */
    @Column(name = "col_href")
    private String href;
    /**
     * Thu tu sap xep menu
     */
    @Column(name = "col_index", nullable = false, columnDefinition = "int(11) default 0")
    private int index;
    /**
     * Level cua menu, level1=0 , level 2=1, level 3=2
     */
    @Column(name = "col_level", nullable = false, columnDefinition = "int(11) default 0")
    private int level;
    /**
     * Menu parent cua menu day
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_parent_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private MenuEntity parent;
    /**
     * Danh sach cac menu con ben trong
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "parent")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<MenuEntity> menus = new HashSet<>();
    /**
     * Trang thai isActive cua menu
     */
    @Column(name = "col_is_active", nullable = false, columnDefinition = "int(11) default 0")
    private int isActive;
    /**
     * Xac dinh web nao quan ly menu nay
     *
     * @see WebType
     */
    @Column(name = "col_web_type", nullable = false, columnDefinition = "int(11) default 0")
    private int webType;
    /**
     * su dung dich bit <br>
     * Nguoi dung: 1 <br>
     * Bac si: 2 <br>
     * Admin:4 <br>
     * Supper admin:8<br>
     * Admin hoi lao: 16 <br>
     * thanh vien hoi lao: 32 <br>
     * Neu co 2 vai tro vi du: bac si + admin truyen (2+4)=6<br>
     * 3 vai tro (1+2+4)=7
     *
     * @see UserType
     */
    @Column(name = "col_role", nullable = false, columnDefinition = "int(11) default 0")
    private int role;
    /**
     * @see UserEntity#adminRole
     */
    @Column(name = "col_admin_role", nullable = false, columnDefinition = "int(11) default 0")
    private int adminRole;
    /**
     * Group cac menu, hardcode menu tin tuc = 1
     */
    @Column(name = "col_group", nullable = false, columnDefinition = "int(11) default 0")
    private int group;
    /**
     * Ten cua menu
     */
    @Column(name = "col_name")
    private String name;
    private String uid;
    /**
     * Danh sach cac tai lieu nam trong menu nay
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "menu")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<DocumentEntity> documents = new HashSet<>();

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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public MenuEntity getParent() {
        return parent;
    }

    public void setParent(MenuEntity parent) {
        this.parent = parent;
    }

    public Set<MenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(Set<MenuEntity> menus) {
        this.menus = menus;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getWebType() {
        return webType;
    }

    public void setWebType(int webType) {
        this.webType = webType;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(int adminRole) {
        this.adminRole = adminRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public Set<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<DocumentEntity> documents) {
        this.documents = documents;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
