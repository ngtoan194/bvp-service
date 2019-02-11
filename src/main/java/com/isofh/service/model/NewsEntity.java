package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.NEWS)
public class NewsEntity {


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
     * alias de hien thi tren address cua trinh duyet
     */
    @Column(name = "col_link_alias")
    private String linkAlias;
    /**
     * anh hien thi preview cua bai viet
     */
    @Column(name = "col_image_preview")
    private String imagePreview;
    /**
     * Tu khoa tag vao bai viet
     */
    @Column(name = "col_keyword")
    private String keyword;
    /**
     * Noi dung hien thi o ngoai danh sach
     */
    @Column(columnDefinition = "TEXT", name = "col_content_preview")
    private String contentPreview;
    /**
     * Tieu de cua tin tuc
     */
    @Column(name = "col_title")
    private String title;
    /**
     * Noi dung cua bai viet
     */
    @Column(columnDefinition = "LONGTEXT", name = "col_content")
    private String content;

    @Column(name = "col_is_hot_news")
    private Integer isHotNews;
    /**
     * Tin tuc nay cua web nao, phoi, hoilao, hay phong chong
     */
    @Column(name = "col_web_type")
    private Integer webType;
    /**
     * Hien bai viet nay thuoc la tu thien
     */
    @Column(name = "col_charity")
    private Integer charity;
    /**
     * 1 cho duyet sang benh vien plhoi
     * 2. da duyet
     */
    @Column(name = "col_from_hoi_lao")
    private Integer fromHoiLao;
    /**
     * Danh dau lam thong bao noi bat
     * 1 co, 0 la khong
     */
    @Column(name = "col_highlight_noti")
    private int highlightNoti;
    /**
     * Tin tuc nay la cua cac khoa, phong
     */
    @Column(name = "col_belong_department")
    private int belongDepartment;
    /**
     * Loai tin tuc hay trao doi lam sang <br>
     */
    @Column(name = "col_type")
    private int type;
    @Column(name = "col_name")
    private String name;
    /**
     * Department of Schedule
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_page_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private PageEntity page;
    /**
     * Department of Schedule
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_menu_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private MenuEntity menu;
    /**
     * uid tu he thong cu, dung de join du lieu sau khi merge
     */
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public String getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(String imagePreview) {
        this.imagePreview = imagePreview;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContentPreview() {
        return contentPreview;
    }

    public void setContentPreview(String contentPreview) {
        this.contentPreview = contentPreview;
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

    public Integer getIsHotNews() {
        return isHotNews;
    }

    public void setIsHotNews(Integer isHotNews) {
        this.isHotNews = isHotNews;
    }

    public Integer getWebType() {
        return webType;
    }

    public void setWebType(Integer webType) {
        this.webType = webType;
    }

    public Integer getCharity() {
        return charity;
    }

    public void setCharity(Integer charity) {
        this.charity = charity;
    }

    public Integer getFromHoiLao() {
        return fromHoiLao;
    }

    public void setFromHoiLao(Integer fromHoiLao) {
        this.fromHoiLao = fromHoiLao;
    }

    public int getHighlightNoti() {
        return highlightNoti;
    }

    public void setHighlightNoti(int highlightNoti) {
        this.highlightNoti = highlightNoti;
    }

    public int getBelongDepartment() {
        return belongDepartment;
    }

    public void setBelongDepartment(int belongDepartment) {
        this.belongDepartment = belongDepartment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageEntity getPage() {
        return page;
    }

    public void setPage(PageEntity page) {
        this.page = page;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
