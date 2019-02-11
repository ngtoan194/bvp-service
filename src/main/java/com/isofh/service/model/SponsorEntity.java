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

@Entity(name = TableConst.SPONSOR)
public class SponsorEntity {

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
    @Column(name = "col_logo")
    private String logo;
    /**
     * Mo ta cua hang
     */
    @Column(name = "col_description", columnDefinition = "TEXT")
    private String description;
    private String uid;
    /**
     * so luong nguoi duoc tham du cua hang tai tro nay
     */

    @Column(name = "col_num_sponsor", nullable = false, columnDefinition = "int(11) default 0")
    private int numSponsor;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "sponsor")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private Set<UserConferenceEntity> userConference = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_conference_id")
    @JsonIgnore
    @NotFound(
            action = NotFoundAction.IGNORE)
    private ConferenceEntity conference;

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

    public ConferenceEntity getConference() {
        return conference;
    }

    public void setConference(ConferenceEntity conference) {
        this.conference = conference;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumSponsor() {
        return numSponsor;
    }

    public void setNumSponsor(int numSponsor) {
        this.numSponsor = numSponsor;
    }

    public Set<UserConferenceEntity> getUserConference() {
        return userConference;
    }

    public void setUserConference(Set<UserConferenceEntity> userConference) {
        this.userConference = userConference;
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
}
