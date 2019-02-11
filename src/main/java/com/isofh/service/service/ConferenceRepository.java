package com.isofh.service.service;

import com.isofh.service.model.ConferenceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ConferenceRepository extends CrudRepository<ConferenceEntity, Long> {
    @Query(value = "select n.* from tbl_conference n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_start_date >= :startDate or :startDate = '1970-01-01 00:00:00') "
            + " and (n.col_end_date >= :endDate or :endDate = '1970-01-01 00:00:00') "
            , countQuery = "select count(n.id) from tbl_conference n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_start_date >= :startDate or :startDate = '1970-01-01 00:00:00') "
            + " and (n.col_end_date >= :endDate or :endDate = '1970-01-01 00:00:00') "
            , nativeQuery = true)
    Page<ConferenceEntity> search(
            @Param("name") String name,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable defaultPage);

    ConferenceEntity findFirstByUid(String conferenceUid);

    ConferenceEntity findFirstByLinkAlias(String linkAlias);
}
