package com.isofh.service.service;

import com.isofh.service.model.OnlineExchangeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OnlineExchangeRepository extends CrudRepository<OnlineExchangeEntity, Long> {

    @Query(value = "select n.* from tbl_online_exchange n "
            + " where true "
            + " and (n.col_topic like concat('%',:topic,'%') or :topic = '') "
            + " and (n.col_active =:active or :active = '-1') "
            + " and (date(n.col_start_time) = :startTime or :startTime = '1970-01-01') "
            + " and (date(n.col_end_time) = :endTime or :endTime = '1970-01-01') "
            , countQuery = "select count(n.id) from tbl_online_exchange n "
            + " where true "
            + " and (n.col_topic like concat('%',:topic,'%') or :topic = '') "
            + " and (n.col_active =:active or :active = '-1') "
            + " and (date(n.col_start_time) = :startTime or :startTime = '1970-01-01') "
            + " and (date(n.col_end_time) = :endTime or :endTime = '1970-01-01') "
            , nativeQuery = true)
    Page<OnlineExchangeEntity> search(
            @Param("topic") String topic,
            @Param("active") Integer active,
            @Param("startTime") LocalDate startTime,
            @Param("endTime") LocalDate endTime,
            Pageable defaultPage);

    @Query(value = "select n.* from tbl_online_exchange n "
            + " where true"
            + " and (n.col_active =1) "
            , nativeQuery = true)
    List<OnlineExchangeEntity> checkActive();

    OnlineExchangeEntity findFirstByLinkAlias(String linkAlias);

}
