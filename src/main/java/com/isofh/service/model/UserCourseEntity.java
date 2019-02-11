package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.USER_COURSE)
public class UserCourseEntity {

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
     * Trang thai duyet<br>
     * 0 chua duyet<br>
     * 1 da duyet<br>
     */
    @Column(name = "col_is_verified", nullable = false, columnDefinition = "int(11) default 0")
    private int isVerified;
    /**
     * Trang thai hoan thanh bai PreTest<br>
     * 0 chua hoan thanh<br>
     * 1 da hoan thanh<br>
     */
    @Column(name = "col_is_completed_pre_test", nullable = false, columnDefinition = "int(11) default 0")
    private int isCompletedPreTest;
    /**
     * Trang thai hoan thanh bai PostTest<br>
     * 0 chua hoan thanh<br>
     * 1 da hoan thanh<br>
     */
    @Column(name = "col_is_completed_post_test", nullable = false, columnDefinition = "int(11) default 0")
    private int isCompletedPostTest;
    /**
     * Trang thai dong phi<br>
     * 0 chua dong<br>
     * 1 da dong<br>
     */
    @Column(name = "col_is_paid_fee", nullable = false, columnDefinition = "int(11) default 0")
    private int isPaidFee;
    /**
     * Trang thai dong phi<br>
     * 0 chua dong<br>
     * 1 da dong<br>
     */
    @Column(name = "col_is_issued_certificate", nullable = false, columnDefinition = "int(11) default 0")
    private int isIssuedCertificate;
    /**
     * Ma so chung chi
     */
    @Column(name = "col_certificate_code")
    private String certificateCode;
    /**
     * danh sach cau tra loi preTest
     */
    @Column(name = "col_pre_test_results")
    private String preTestResults;
    /**
     * danh sach cau tra loi postTest
     */
    @Column(name = "col_post_test_results")
    private String postTestResults;
    /**
     * So cau tra loi dung PreTest
     */
    @Column(name = "col_pre_test_score", nullable = false, columnDefinition = "int(11) default 0")
    private int preTestScore;
    /**
     * So cau tra loi dung PostTest
     */
    @Column(name = "col_post_test_score", nullable = false, columnDefinition = "int(11) default 0")
    private int postTestScore;
    /**
     * Nguoi dung dang ky cap chung chi khi tham du khoa hoc
     */
    @Column(name = "col_request_certificate", nullable = false, columnDefinition = "int(11) default 0")
    private int requestCertificate;
    /**
     * Trang thai hoan thanh khoa hoc cua nguoi dung
     */
    @Column(name = "col_finish", nullable = false, columnDefinition = "int(11) default 0")
    private int finish;
    @Column(name = "col_certificate")
    private String certificate;
    /**
     * Nguoi dung tham du khoa hoc
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_user_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_course_lao_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private CourseLaoEntity courseLao;

    private String uid;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public int getIsCompletedPreTest() {
        return isCompletedPreTest;
    }

    public void setIsCompletedPreTest(int isCompletedPreTest) {
        this.isCompletedPreTest = isCompletedPreTest;
    }

    public int getIsCompletedPostTest() {
        return isCompletedPostTest;
    }

    public void setIsCompletedPostTest(int isCompletedPostTest) {
        this.isCompletedPostTest = isCompletedPostTest;
    }

    public int getIsPaidFee() {
        return isPaidFee;
    }

    public void setIsPaidFee(int isPaidFee) {
        this.isPaidFee = isPaidFee;
    }

    public int getIsIssuedCertificate() {
        return isIssuedCertificate;
    }

    public void setIsIssuedCertificate(int isIssuedCertificate) {
        this.isIssuedCertificate = isIssuedCertificate;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getPreTestResults() {
        return preTestResults;
    }

    public void setPreTestResults(String preTestResults) {
        this.preTestResults = preTestResults;
    }

    public String getPostTestResults() {
        return postTestResults;
    }

    public void setPostTestResults(String postTestResults) {
        this.postTestResults = postTestResults;
    }

    public int getPreTestScore() {
        return preTestScore;
    }

    public void setPreTestScore(int preTestScore) {
        this.preTestScore = preTestScore;
    }

    public int getPostTestScore() {
        return postTestScore;
    }

    public void setPostTestScore(int postTestScore) {
        this.postTestScore = postTestScore;
    }

    public int getRequestCertificate() {
        return requestCertificate;
    }

    public void setRequestCertificate(int requestCertificate) {
        this.requestCertificate = requestCertificate;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CourseLaoEntity getCourseLao() {
        return courseLao;
    }

    public void setCourseLao(CourseLaoEntity courseLao) {
        this.courseLao = courseLao;
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
}
