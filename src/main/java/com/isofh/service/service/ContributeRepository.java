package com.isofh.service.service;

import com.isofh.service.model.ContributeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ContributeRepository extends CrudRepository<ContributeEntity, Long> {

    @Query(value = "select n.* from tbl_contribute n "
            + " where true "
            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
            + " and (n.col_title like concat('%',:title,'%') or :title = '') "
            + " and (date(n.col_created_date) >= :startDate or :startDate= '1970-01-01') "
            + " and (date(n.col_created_date) <= :endDate or :endDate= '1970-01-01') "
            + " and (n.col_author like concat('%',:author,'%') or :author = '') "

            , countQuery = "select count(n.id) from tbl_contribute n "
            + " where true "
            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
            + " and (n.col_title like concat('%',:title,'%') or :title = '') "
            + " and (date(n.col_created_date) >= :startDate or :startDate= '1970-01-01') "
            + " and (date(n.col_created_date) <= :endDate or :endDate= '1970-01-01') "
            + " and (n.col_author like concat('%',:author,'%') or :author = '') "
            , nativeQuery = true)
    Page<ContributeEntity> search(
            @Param("content") String content,
            @Param("title") String title,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("author") String author,
            Pageable defaultPage);

    ContributeEntity findFirstByLinkAlias(String linkAlias);
}

