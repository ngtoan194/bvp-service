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

@Entity(name = TableConst.CONFERENCE)
public class ConferenceEntity {

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
    @Column(name = "col_logo")
    private String logo;
    /**
     * danh sach banner de hien thi slide
     */
    @Column(name = "col_banners", columnDefinition = "TEXT")
    private String banners;
    /**
     * Thoi gian bat dau hoi nghi
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_start_date")
    private LocalDateTime startDate;
    /**
     * Thoi gian ket thuc hoi nghi
     */
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_end_date")
    private LocalDateTime endDate;
    /**
     * Chi phi tham du hoi nghi <br>
     * tu day dang json luu cac thong tin vao, luc lay json ra hien thi
     */
    @Column(name = "col_fee")
    private String fee;
    /**
     * Anh quang cao trong hoi nghi cho phep chay kieu slide
     */
    @Column(name = "col_ads_images", columnDefinition = "TEXT")
    private String adsImages;
    /**
     * Chu de trong hoi nghi
     */
    @Column(name = "col_topics", columnDefinition = "TEXT")
    private String topics;
    /**
     * Hoi truong to chuc
     */
    @Column(name = "col_halls", columnDefinition = "TEXT")
    private String halls;
    /**
     * Link hien thi tren web
     */
    @Column(name = "col_link_alias", columnDefinition = "TEXT")
    private String linkAlias;
    /**
     * Trang gioi thieu
     */
    @Column(name = "col_page_introduction", columnDefinition = "TEXT")
    private String pageIntroduction;
    /**
     * Trang thong tin chung
     */
    @Column(name = "col_page_common_info", columnDefinition = "TEXT")
    private String pageCommonInfo;
    /**
     * Trang hoi dong khoa hoc
     */
    @Column(name = "col_page_science_council", columnDefinition = "TEXT")
    private String pageScienceCouncil;
    /**
     * Trang thong diep chao mung
     */
    @Column(name = "col_page_welcome", columnDefinition = "TEXT")
    private String pageWelcome;
    /**
     * Trang dia diem hoi nghi
     */
    @Column(name = "col_page_conference_location", columnDefinition = "TEXT")
    private String pageConferenceLocation;
    /**
     * Trang ve khach san
     */
    @Column(name = "col_page_hotel", columnDefinition = "TEXT")
    private String pageHotel;
    /**
     * Trang Du lich
     */
    @Column(name = "col_page_tourism", columnDefinition = "TEXT")
    private String pageTourism;
    /**
     * Trang Trien lam poster
     */
    @Column(name = "col_page_exhibition_poster", columnDefinition = "TEXT")
    private String pageExhibitionPoster;
    /**
     * Trang tai tro hoi nghi
     */
    @Column(name = "col_page_conference_sponsor", columnDefinition = "TEXT")
    private String pageConferenceSponsor;
    /**
     * Trang ve hoi nghi ve tinh
     */
    @Column(name = "col_page_related_conference", columnDefinition = "TEXT")
    private String pageRelatedConference;
    /**
     * Huong dan nop bao cao
     */
    @Column(name = "col_guide_report", columnDefinition = "TEXT")
    private String guideReport;
    /**
     * Huong dan dang ky
     */
    @Column(name = "col_guide_register", columnDefinition = "TEXT")
    private String guideRegister;
    @Column(name = "col_sponsor", columnDefinition = "TEXT")
    private String sponsor;
    @Column(name = "col_register_fee", columnDefinition = "TEXT")
    private String registerFee;
    /**
     * Map de hien thi ten de in
     */
    @Column(name = "col_print_name", columnDefinition = "TEXT")
    private String printName;
    /**
     * Danh sach cac admin cua khoa phong nay
     */
    @Column(name = "col_admin_ids", columnDefinition = "TEXT")
    private String adminIds;
    /**
     * Ten hoi nghi
     */
    @Column(name = "col_name")
    private String name;
    private String uid;
    /**
     * Danh sach cac khoa hoc thuoc hoi nghi nay
     */

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,

                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_conference_sponsor",
            joinColumns = {@JoinColumn(name = "col_conference_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_course_id")})
    @JsonIgnore
    private Set<CourseEntity> courses = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "conference")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<CourseLaoEntity> courseLaos = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "conference")

    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<UserConferenceEntity> userConference = new HashSet<>();
    /**
     * Ma hien tai cua thanh vien tham du hoi nghi, moi lan them moi thanh vien, tang gia tri nay
     */
    @Column(name = "col_user_index", columnDefinition = "bigint(20) default 0")
    private Long userIndex;
    /**
     * Danh sach cac session cua hoi nghi
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "conference")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<SessionEntity> sessions = new HashSet<>();
    /**
     * Noi dung hien thi o cuoi cua hoi nghi
     */
    @Column(name = "col_footer")
    private String footer;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    public Set<UserConferenceEntity> getUserConference() {
        return userConference;
    }

    public void setUserConference(Set<UserConferenceEntity> userConference) {
        this.userConference = userConference;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanners() {
        return banners;
    }

    public void setBanners(String banners) {
        this.banners = banners;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAdsImages() {
        return adsImages;
    }

    public void setAdsImages(String adsImages) {
        this.adsImages = adsImages;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getHalls() {
        return halls;
    }

    public void setHalls(String halls) {
        this.halls = halls;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public String getPageIntroduction() {
        return pageIntroduction;
    }

    public void setPageIntroduction(String pageIntroduction) {
        this.pageIntroduction = pageIntroduction;
    }

    public String getPageCommonInfo() {
        return pageCommonInfo;
    }

    public void setPageCommonInfo(String pageCommonInfo) {
        this.pageCommonInfo = pageCommonInfo;
    }

    public String getPageScienceCouncil() {
        return pageScienceCouncil;
    }

    public void setPageScienceCouncil(String pageScienceCouncil) {
        this.pageScienceCouncil = pageScienceCouncil;
    }

    public String getPageWelcome() {
        return pageWelcome;
    }

    public void setPageWelcome(String pageWelcome) {
        this.pageWelcome = pageWelcome;
    }

    public String getPageConferenceLocation() {
        return pageConferenceLocation;
    }

    public void setPageConferenceLocation(String pageConferenceLocation) {
        this.pageConferenceLocation = pageConferenceLocation;
    }

    public String getPageHotel() {
        return pageHotel;
    }

    public void setPageHotel(String pageHotel) {
        this.pageHotel = pageHotel;
    }

    public String getPageTourism() {
        return pageTourism;
    }

    public void setPageTourism(String pageTourism) {
        this.pageTourism = pageTourism;
    }

    public String getPageExhibitionPoster() {
        return pageExhibitionPoster;
    }

    public void setPageExhibitionPoster(String pageExhibitionPoster) {
        this.pageExhibitionPoster = pageExhibitionPoster;
    }

    public String getPageConferenceSponsor() {
        return pageConferenceSponsor;
    }

    public void setPageConferenceSponsor(String pageConferenceSponsor) {
        this.pageConferenceSponsor = pageConferenceSponsor;
    }

    public String getPageRelatedConference() {
        return pageRelatedConference;
    }

    public void setPageRelatedConference(String pageRelatedConference) {
        this.pageRelatedConference = pageRelatedConference;
    }

    public String getGuideReport() {
        return guideReport;
    }

    public void setGuideReport(String guideReport) {
        this.guideReport = guideReport;
    }

    public String getGuideRegister() {
        return guideRegister;
    }

    public void setGuideRegister(String guideRegister) {
        this.guideRegister = guideRegister;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Long getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(Long userIndex) {
        this.userIndex = userIndex;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getRegisterFee() {
        return registerFee;
    }

    public void setRegisterFee(String registerFee) {
        this.registerFee = registerFee;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(String adminIds) {
        this.adminIds = adminIds;
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
    }

    public Set<CourseLaoEntity> getCourseLaos() {
        return courseLaos;
    }

    public void setCourseLaos(Set<CourseLaoEntity> courseLaos) {
        this.courseLaos = courseLaos;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Set<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(Set<SessionEntity> sessions) {
        this.sessions = sessions;
    }
}
