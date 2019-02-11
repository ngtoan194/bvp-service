package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import com.isofh.service.enums.DoctorType;
import com.isofh.service.enums.SocialType;
import com.isofh.service.enums.UserSourceType;
import com.isofh.service.enums.UserType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = TableConst.USER)
public class UserEntity {

    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_created_date")
    public LocalDateTime createdDate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    @Column(name = "col_updated_date")
    public LocalDateTime updatedDate;
    /**
     * Id social khi dang nhap bang google
     */
    @Column(name = "col_google_id")
    public String googleId;
    /**
     * Id social khi dang nhap bang facebook
     */
    @Column(name = "col_facebook_id")
    public String facebookId;
    /**
     * Trang thai xoa cua user
     */
    @Column(name = "col_deleted", nullable = false, columnDefinition = "int(11) default 0")
    public int deleted = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;
    /**
     * Hoi vien da duoc duyet
     * 0 chua phe duyet<b></b>
     * 1 da phe duyet
     */
    @Column(name = "col_verified_member_lao", nullable = false, columnDefinition = "int(11) default 0")
    private int verifiedMemberLao;
    /**
     * email cua nguoi dung
     */
    @Column(name = "col_email")
    private String email;
    /**
     * password dang nhap
     */
    @Column(name = "col_password")
    private String password;
    /**
     * Tuong duong username
     */
    @Column(name = "col_username")
    private String username;
    /**
     * Ten hien thi
     */
    @Column(name = "col_name")
    private String name;
    /**
     * Ten cong viec
     */
    @Column(name = "col_job")
    private String job;
    /**
     * Ten cong ty
     */
    @Column(name = "col_company")
    private String company;
    /**
     * Ngay sinh (second)
     */
    @Column(name = "col_dob")
    @JsonFormat(pattern = AppConst.DATE_FORMAT)
    private LocalDate dob;
    /**
     * Dia chi
     */
    @Column(name = "col_address")
    private String address;
    /**
     * Gioi tinh - 0: Nu      1: Nam
     */
    @Column(name = "col_gender", nullable = false, columnDefinition = "int(11) default 0")
    private int gender;
    /**
     * Anh avatar dang thumbnail de load nhanh hon
     */
    @Column(name = "col_thumbnail")
    private String thumbnail;
    /**
     * Anh dai dien
     */
    @Column(name = "col_avatar")
    private String avatar;
    /**
     * So dien thoai
     */
    @Column(name = "col_phone")
    private String phone;
    /**
     * loaid dang nhap: 0: Default      1: Facebook     2: Google
     *
     * @see SocialType
     */
    @Column(name = "col_social_type", nullable = false, columnDefinition = "int(11) default 0")
    private int socialType;
    /**
     * Kieu nguoi dung <br>
     * 1: User     <br>
     * 2: Doctor   <br>
     * 4: Admin   <br>
     * 8: SuperAdmin <br>
     * 16: admin hoi lao <br>
     * 32 thanh vien hoi lao <br>
     *
     * @see UserType
     */
    @Column(name = "col_role", nullable = false, columnDefinition = "int(11) default 0")
    private int role = UserType.USER.getValue();
    /**
     * Trang thai da dc kiem duyet nguoi dung/bac si
     */
    @Column(name = "col_verify", nullable = false, columnDefinition = "int(11) default 0")
    private int verify = 1;
    /**
     * Chuc vu, chuc danh cau bac si
     */
    @Column(name = "col_job_title")
    private String jobTitle;
    /**
     * Trang thai Block, mac dinh khong block
     */
    @Column(name = "col_block", nullable = false, columnDefinition = "int(11) default 0")
    private int block = 0;
    /**
     * Thu tu sap xep cua cac bac si trong khoa
     *
     * @see DoctorType
     */
    @Column(name = "col_index", nullable = false, columnDefinition = "int(11) default 0")
    private int index = 0;
    /**
     * Token tao ra khi user dang nhap
     */
    @Column(name = "col_login_token")
    private String loginToken;
    /**
     * Quyen cua admin
     */
    @Column(name = "col_admin_role", nullable = false, columnDefinition = "int(11) default 0")
    private int adminRole;
    /**
     * Danh dau bac si noi bat, co nhieu dong gop
     */
    @Column(name = "col_is_highlight", nullable = false, columnDefinition = "int(11) default 0")
    private int isHighlight;
    /**
     * Thoi diem cuoi cung Reset Token Time,
     * cac Token co iat nho hon lastResetTime set khong hop le,
     * Gia tri nay duoc set thi can thay doi thong tin nguoi dung, can logout ra, login lai
     */
    @Column(name = "col_last_reset_time")
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    private LocalDateTime lastResetTime;
    /**
     * Thoi diem dang nhap cuoi cung cua nguoi do
     */
    @Column(name = "col_last_login")
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT)
    private LocalDateTime lastLogin;
    /**
     * nguon goc user <br>
     * 0 - Tao binh thuong, nguoi dung dang nhap <br>
     * 1 - Admin tao, <br>
     * 2 - His tao, <br>
     * 3 tao  <br>
     *
     * @see UserSourceType
     */
    @Column(name = "col_source", nullable = false, columnDefinition = "int(11) default 0")
    private int source;
    /**
     * Chuc vu
     */
    @Column(name = "col_title")
    private String title;
    /**
     * Hoc ham, hoc vi
     */
    @Column(name = "col_degree")
    private String degree;
    /**
     * Don vi cong tac
     */
    @Column(name = "col_org")
    private String org;
    /**
     * Ma so thanh vien
     */
    @Column(name = "col_member_code")
    private String memberCode;
    /**
     * So luong cau hoi
     */
    @Column(name = "col_answered_count", nullable = false, columnDefinition = "int(11) default 0")
    private int answeredCount;
    /**
     * so luong cau tra loi
     */
    @Column(name = "col_un_answered_count", nullable = false, columnDefinition = "int(11) default 0")
    private int unAnsweredCount;
    /**
     * Id cua tinh, thanh pho
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_province_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private ProvinceEntity province;
    /**
     * Thong tin gioi thieu cua bac si
     */
    @Column(name = "col_introduction", columnDefinition = "TEXT")
    private String introduction;
    /**
     * anh goc cua nguoi dung
     */
    @Column(name = "col_origin_image")
    private String originImage;
    /**
     * Ngay bat dau active cua hoi vien
     */
    @Column(name = "col_start_active")
    @JsonFormat(pattern = AppConst.DATE_FORMAT)
    private LocalDate startActive;
    /**
     * Ngay ket thuc active cua hoi vien
     */
    @Column(name = "col_end_active")
    @JsonFormat(pattern = AppConst.DATE_FORMAT)
    private LocalDate endActive;
    private String uid;
    /**
     * Department ma user thuoc ve
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_department_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private DepartmentEntity department;
    /**
     * danh sach cac tag ma user follow
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_user_tag",
            joinColumns = {@JoinColumn(name = "col_user_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_tag_id")})
    @JsonIgnore
    private Set<TagEntity> tags = new HashSet<>();
    /**
     * Danh sach giao luu truc tuyen ma bac si tham du
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_doctor_online_exchange",
            joinColumns = {@JoinColumn(name = "col_online_exchange_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_doctor_id")})
    @JsonIgnore
    private Set<OnlineExchangeEntity> onlineExchanges = new HashSet<>();
    /**
     * danh sach cac tag ma user follow
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_user_follow_post",
            joinColumns = {@JoinColumn(name = "col_user_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_post_id")})
    @JsonIgnore
    private Set<PostEntity> followedPosts = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<UserCourseEntity> userCourse = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<UserConferenceEntity> userConference = new HashSet<>();

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @Column(name = "col_charity")
    private Integer charity;

    public Set<UserCourseEntity> getUserCourse() {
        return userCourse;
    }

    public void setUserCourse(Set<UserCourseEntity> userCourse) {
        this.userCourse = userCourse;
    }

    public Set<UserConferenceEntity> getUserConference() {
        return userConference;
    }

    public void setUserConference(Set<UserConferenceEntity> userConference) {
        this.userConference = userConference;
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

    public int getVerifiedMemberLao() {
        return verifiedMemberLao;
    }

    public void setVerifiedMemberLao(int verifiedMemberLao) {
        this.verifiedMemberLao = verifiedMemberLao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSocialType() {
        return socialType;
    }

    public void setSocialType(int socialType) {
        this.socialType = socialType;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public int getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(int adminRole) {
        this.adminRole = adminRole;
    }

    public int getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(int isHighlight) {
        this.isHighlight = isHighlight;
    }

    public LocalDateTime getLastResetTime() {
        return lastResetTime;
    }

    public void setLastResetTime(LocalDateTime lastResetTime) {
        this.lastResetTime = lastResetTime;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public int getAnsweredCount() {
        return answeredCount;
    }

    public void setAnsweredCount(int answeredCount) {
        this.answeredCount = answeredCount;
    }

    public int getUnAnsweredCount() {
        return unAnsweredCount;
    }

    public void setUnAnsweredCount(int unAnsweredCount) {
        this.unAnsweredCount = unAnsweredCount;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOriginImage() {
        return originImage;
    }

    public void setOriginImage(String originImage) {
        this.originImage = originImage;
    }

    public LocalDate getStartActive() {
        return startActive;
    }

    public void setStartActive(LocalDate startActive) {
        this.startActive = startActive;
    }

    public LocalDate getEndActive() {
        return endActive;
    }

    public void setEndActive(LocalDate endActive) {
        this.endActive = endActive;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<OnlineExchangeEntity> getOnlineExchanges() {
        return onlineExchanges;
    }

    public void setOnlineExchanges(Set<OnlineExchangeEntity> onlineExchanges) {
        this.onlineExchanges = onlineExchanges;
    }


    public ProvinceEntity getProvince() {
        return province;
    }

    public void setProvince(ProvinceEntity province) {
        this.province = province;
    }

    public Set<PostEntity> getFollowedPosts() {
        return followedPosts;
    }

    public void setFollowedPosts(Set<PostEntity> followedPosts) {
        this.followedPosts = followedPosts;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getCharity() {
        return charity;
    }

    public void setCharity(Integer charity) {
        this.charity = charity;
    }
}
