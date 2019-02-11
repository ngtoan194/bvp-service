package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.DOCUMENT)
public class DocumentEntity {

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
     * Ten tai lieu
     */
    @Column(name = "col_name")
    private String name;
    /**
     * So ky hieu tai lieu
     */
    @Column(name = "col_code")
    private String code;
    /**
     * Ngay ban hanh
     */
    @JsonFormat(pattern = AppConst.DATE_FORMAT)
    @Column(name = "col_published_date")
    private LocalDate publishedDate;
    /**
     * Ngay co hieu luc
     */
    @JsonFormat(pattern = AppConst.DATE_FORMAT)
    @Column(name = "col_effective_date")
    private LocalDate effectiveDate;
    /**
     * Don vi ban hanh
     */
    @Column(name = "col_published_org", columnDefinition = "TEXT")
    private String publishedOrg;
    /**
     * File xem truoc
     */
    @Column(name = "col_file_preview")
    private String filePreview;
    /**
     * File
     */
    @Column(name = "col_file")
    private String file;
    /**
     * Link alias
     */
    @Column(name = "col_link_alias")
    private String linkAlias;
    /**
     * Anh tai lieu
     */
    @Column(name = "col_image")
    private String image;
    /**
     * Tai lieu nam o web nao
     * 1: bvp
     * 2: hoi lao
     * 3: chong lao
     */
    @Column(name = "col_web_type", nullable = false, columnDefinition = "int(11) default 1")
    private int webType = 1;
    /**
     * Loai tai lieu
     * 1: tai lieu
     * 2: van ban
     * 3: tap chi an pham
     * 4: video
     * 5: hoi nghi - hoi thao
     */
    @Column(name = "col_type", nullable = false, columnDefinition = "int(11) default 1")
    private int type = 1;
    /**
     * So luong nguoi da download tai lieu nay
     */
    @Column(name = "col_download_count", nullable = false, columnDefinition = "int(11) default 0")
    private int downloadCount = 0;
    /**
     * So luong nguoi da view tai lieu nay
     */
    @Column(name = "col_view_count", nullable = false, columnDefinition = "int(11) default 0")
    private int viewCount = 0;
    /**
     * Tai lieu noi bat
     * 1: yes
     * 2: no
     */
    @Column(name = "col_is_high_light", nullable = false, columnDefinition = "int(11) default 0")
    private int isHighLight = 0;
    /**
     * Ten tac gia
     */
    @Column(name = "col_author_name")
    private String authorName;
    /**
     * Nam phat hanh
     */
    @Column(name = "col_published_year", nullable = false, columnDefinition = "int(11) default 0")
    private int publishedYear = 0;
    /**
     * So tai lieu
     */
    @Column(name = "col_document_number")
    private String documentNumber;
    /**
     * Gia tai lieu
     */
    @Column(name = "col_price", nullable = false, columnDefinition = "bigint(11) default 0")
    private long price = 0;
    /**
     * Neu fileType=2 la youtube thi can link nay
     */
    @Column(name = "col_link_youtube")
    private String linkYoutube;
    /**
     * Tai lieu nay free cho cac thanh vien hoi nghi download, nguoi ngoai phai mat phi
     */
    @Column(name = "col_fee_for_member", nullable = false, columnDefinition = "int(11) default 0")
    private int freeForMember = 0;
    private String uid;
    /**
     * menu of this document
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_menu_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private MenuEntity menu;
    /**
     * menu of this document
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_conference_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private ConferenceEntity conference;
    /**
     * Danh sach comment cua document nay
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "document")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<DocumentCommentEntity> documentComments = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getPublishedOrg() {
        return publishedOrg;
    }

    public void setPublishedOrg(String publishedOrg) {
        this.publishedOrg = publishedOrg;
    }

    public String getFilePreview() {
        return filePreview;
    }

    public void setFilePreview(String filePreview) {
        this.filePreview = filePreview;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getWebType() {
        return webType;
    }

    public void setWebType(int webType) {
        this.webType = webType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getIsHighLight() {
        return isHighLight;
    }

    public void setIsHighLight(int isHighLight) {
        this.isHighLight = isHighLight;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public int getFreeForMember() {
        return freeForMember;
    }

    public void setFreeForMember(int freeForMember) {
        this.freeForMember = freeForMember;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public Set<DocumentCommentEntity> getDocumentComments() {
        return documentComments;
    }

    public void setDocumentComments(Set<DocumentCommentEntity> documentComments) {
        this.documentComments = documentComments;
    }

    public ConferenceEntity getConference() {
        return conference;
    }

    public void setConference(ConferenceEntity conference) {
        this.conference = conference;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
