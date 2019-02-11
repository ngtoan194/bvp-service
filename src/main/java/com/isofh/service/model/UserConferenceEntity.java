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

@Entity(name = TableConst.USER_CONFERENCE)
public class UserConferenceEntity {

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
     * Ma so hoi vien
     */
    @Column(name = "col_member_code")
    private String memberCode;
    /**
     * Anh avatar cua hoi vien
     */
    @Column(name = "col_avatar")
    private String avatar;
    /**
     * Ngay sinh cua hoi vien
     */
    @Column(name = "col_dob")
    @JsonFormat(pattern = AppConst.DATE_FORMAT)
    private LocalDate dob;
    /**
     * Gioi tinh
     */
    @Column(name = "col_gender", nullable = false, columnDefinition = "int(11) default 0")
    private int gender;
    /**
     * hoc ham, hoc vi
     */
    @Column(name = "col_degree")
    private String degree;
    /**
     * chuc vu
     */
    @Column(name = "col_titles")
    private String titles;
    /**
     * Khoa phong
     */
    @Column(name = "col_department")
    private String department;
    /**
     * Co quan
     */
    @Column(name = "col_company")
    private String company;
    /**
     * Dia chi
     */
    @Column(name = "col_address", columnDefinition = "TEXT")
    private String address;
    /**
     * Email
     */
    @Column(name = "col_email")
    private String email;
    /**
     * so dien thoai
     */
    @Column(name = "col_phone")
    private String phone;
    /**
     * tieu su
     */
    @Column(name = "col_profile")
    private String profile;
    /**
     * Vai tro
     */
    @Column(name = "col_role")
    private long role;
    /**
     * la thanh vien cua VATLD
     */
    @Column(name = "col_member_vatld", nullable = false, columnDefinition = "int(11) default 0")
    private int memberVatld;
    /**
     * Loai tham du, mine phi hoac duoc tai tro
     */
    @Column(name = "col_type", nullable = false, columnDefinition = "int(11) default 0")
    private int type;
    /**
     * Phi tham du
     */
    @Column(name = "col_fee", nullable = false, columnDefinition = "int(11) default 0")
    private long fee;
    /**
     *
     */
    @Column(name = "col_approved", nullable = false, columnDefinition = "int(11) default 0")
    private int approved;
    /**
     * Nguoi dung tham du hoi thao
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_user_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity user;
    /**
     * Thong tin hoi thao
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_conference_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private ConferenceEntity conference;
    /**
     * Thong tin hoi thao
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_sponsor_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private SponsorEntity sponsor;
    private String uid;
    /**
     * User comment vao bai hoc
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tbl_user_conference_course_lao",
            joinColumns = {@JoinColumn(name = "col_user_conference_id")},
            inverseJoinColumns = {@JoinColumn(name = "col_course_lao_id")})
    @JsonIgnore
    private Set<CourseLaoEntity> courseLaos = new HashSet<>();
    /**
     * Ma thanh vien tham du hoi nghi
     */
    @Column(name = "col_code")
    private String code;
    /**
     * danh dau ng dung la admin
     */
    @Column(name = "col_active", nullable = false, columnDefinition = "int(11) default 0")
    private int isAdmin = 0;

    @Column(name = "col_name")
    private String name;



    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public long getRole() {
        return role;
    }

    public void setRole(long role) {
        this.role = role;
    }

    public int getMemberVatld() {
        return memberVatld;
    }

    public void setMemberVatld(int memberVatld) {
        this.memberVatld = memberVatld;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ConferenceEntity getConference() {
        return conference;
    }

    public void setConference(ConferenceEntity conference) {
        this.conference = conference;
    }

    public SponsorEntity getSponsor() {
        return sponsor;
    }

    public void setSponsor(SponsorEntity sponsor) {
        this.sponsor = sponsor;
    }

    public Set<CourseLaoEntity> getCourseLaos() {
        return courseLaos;
    }

    public void setCourseLaos(Set<CourseLaoEntity> courseLaos) {
        this.courseLaos = courseLaos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
