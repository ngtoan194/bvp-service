package com.isofh.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.TableConst;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = TableConst.ONLINE_EXCHANGE_QUESTION)
public class OnlineExchangeQuestionEntity {

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
     * noi dung cau hoi
     */
    @Column(columnDefinition = "TEXT", name = "col_content")
    private String content;
    /**
     * cau tra loi
     */
    @Column(columnDefinition = "TEXT", name = "col_answer")
    private String answer;
    /**
     * trang thai duyet cau hoi
     * 1: duyet
     * 0: chua duyet
     */
    @Column(name = "col_approval", nullable = false, columnDefinition = "int(11) default 0")
    private int approval = 0;
    /**
     * trang thai an danh, k hien thong tin nguoi hoi
     * 1: an danh
     * 0: khong an danh
     */
    @Column(name = "col_hide_info", nullable = false, columnDefinition = "int(11) default 0")
    private int hideInfo = 0;
    /**
     * OnlineExchange of cua question nay
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_online_exchange_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private OnlineExchangeEntity onlineExchange;
    /**
     * Danh sach doctor cua question
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_doctor_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity doctor;
    /**
     * Nguoi dat cau hoi nay
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_created_user_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private UserEntity createdUser;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getApproval() {
        return approval;
    }

    public void setApproval(int approval) {
        this.approval = approval;
    }

    public int getHideInfo() {
        return hideInfo;
    }

    public void setHideInfo(int hideInfo) {
        this.hideInfo = hideInfo;
    }

    public OnlineExchangeEntity getOnlineExchange() {
        return onlineExchange;
    }

    public void setOnlineExchange(OnlineExchangeEntity onlineExchange) {
        this.onlineExchange = onlineExchange;
    }

    public UserEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(UserEntity doctor) {
        this.doctor = doctor;
    }

    public UserEntity getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(UserEntity createdUser) {
        this.createdUser = createdUser;
    }
}
