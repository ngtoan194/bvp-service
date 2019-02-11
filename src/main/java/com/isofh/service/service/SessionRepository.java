package com.isofh.service.service;

import com.isofh.service.model.SessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SessionRepository extends CrudRepository<SessionEntity, Long> {

    @Query(value = "select n.* from tbl_session n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_type = :type or :type= -1) "
            + " and (n.col_start_time >= :startTime or :startTime = '1970-01-01 00:00:00') "
            + " and (n.col_end_time <= :endTime or :endTime = '1970-01-01 00:00:00') "
            + " and (n.col_location like concat('%',:location,'%') or :location = '') "
            + " and (n.col_topic like concat('%',:topic,'%') or :topic = '') "
            + " and (n.col_conference_id =:conferenceId or :conferenceId = '-1') "
            , countQuery = "select count(n.id) from tbl_session n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_type = :type or :type= -1) "
            + " and (n.col_start_time >= :startTime or :startTime = '1970-01-01 00:00:00') "
            + " and (n.col_end_time <= :endTime or :endTime = '1970-01-01 00:00:00') "
            + " and (n.col_location like concat('%',:location,'%') or :location = '') "
            + " and (n.col_topic like concat('%',:topic,'%') or :topic = '') "
            + " and (n.col_conference_id =:conferenceId or :conferenceId = '-1') "
            , nativeQuery = true)
    Page<SessionEntity> search(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("location") String location,
            @Param("topic") String topic,
            @Param("conferenceId") Long conferenceId,
            Pageable defaultPage);

    SessionEntity findFirstByUid(String sessionUid);
}
