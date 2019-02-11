package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.PAGE)
public class PageEntity {

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
    @Column(name = "col_deleted")
    private int deleted = 0;
    @Column(name = "col_name")
    private String name;
    @Expose
    private String adminIds;

    private String uid;
    /**
     * album nua nay cua cac khoa/phong nao
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "page")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<AlbumEntity> album = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private NewsEntity news;
    @Column(name = "col_type", nullable = false, columnDefinition = "int(11) default 0")
    private int type;
    /**
     * Tieu de page
     */
    @Column(name = "col_title")
    private String title;
    @Column(name = "col_color")
    private String color;
    @Column(name = "col_color2")

    private String color2;
    @Column(name = "col_color3")
    private String color3;
    /**
     * Noi dung page
     */
    @Column(name = "col_content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "col_nameE")
    private String nameE;
    /**
     * dung de hien thi tren browserUrl
     */
    @Column(name = "col_linkAlias")
    private String linkAlias;
    /**
     * Danh sach anh hien thi tren thong tin cua khoa phong
     */
    @Column(name = "col_images")
    private String images;
    /**
     * So dien thoai
     */
    @Column(name = "col_phone")
    private String phone;
    /**
     * So may le
     */
    @Column(name = "col_phoneExt")
    private String phoneExt;
    /**
     * Danh sach giam doc cu
     */
    @Column(name = "col_oldDirectors", columnDefinition = "TEXT")
    private String oldDirectors;
    /**
     * Danh sach pho giam doc cu
     */
    @Column(name = "col_oldDeputyDirectors", columnDefinition = "TEXT")
    private String oldDeputyDirectors;
    /**
     * Danh sach giam doc hien tai
     */
    @Column(name = "col_directors", columnDefinition = "TEXT")
    private String directors;
    /**
     * Danh sach pho giam doc hien tai
     */
    @Column(name = "col_deputyDirectors", columnDefinition = "TEXT")
    private String deputyDirectors;
    /**
     * Thong tin chung
     */
    @Column(name = "col_commonInfo", columnDefinition = "TEXT")
    private String commonInfo;
    /**
     * Chuc nang,
     */
    @Column(name = "col_function")
    private String function;
    /**
     * nhan su
     */
    @Column(name = "col_employees")
    private String employees;
    /**
     * khen thuong
     */
    @Column(name = "col_reward")
    private String reward;
    /**
     * dinh huong phat trien
     */
    @Column(name = "col_development")
    private String development;
    /**
     * Khi muon hien thi trang bang 1 file dinh kem
     */
    @Column(name = "col_file")
    private String file;
    /**
     * Anh hien thi o banner
     */
    @Column(name = "col_bannerImage")
    private String bannerImage;
    @Column(name = "col_webType", nullable = false, columnDefinition = "int(11) default 0")
    private int webType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameE() {
        return nameE;
    }

    public void setNameE(String nameE) {
        this.nameE = nameE;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public String getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(String adminIds) {
        this.adminIds = adminIds;
    }

    public NewsEntity getNews() {
        return news;
    }

    public void setNews(NewsEntity news) {
        this.news = news;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public String getOldDirectors() {
        return oldDirectors;
    }

    public void setOldDirectors(String oldDirectors) {
        this.oldDirectors = oldDirectors;
    }

    public String getOldDeputyDirectors() {
        return oldDeputyDirectors;
    }

    public void setOldDeputyDirectors(String oldDeputyDirectors) {
        this.oldDeputyDirectors = oldDeputyDirectors;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getDeputyDirectors() {
        return deputyDirectors;
    }

    public void setDeputyDirectors(String deputyDirectors) {
        this.deputyDirectors = deputyDirectors;
    }

    public String getCommonInfo() {
        return commonInfo;
    }

    public void setCommonInfo(String commonInfo) {
        this.commonInfo = commonInfo;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getDevelopment() {
        return development;
    }

    public void setDevelopment(String development) {
        this.development = development;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public int getWebType() {
        return webType;
    }

    public void setWebType(int webType) {
        this.webType = webType;
    }

    public Set<AlbumEntity> getAlbum() {
        return album;
    }

    public void setAlbum(Set<AlbumEntity> album) {
        this.album = album;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
