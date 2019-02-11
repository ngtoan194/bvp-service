package com.isofh.service.service;

import com.isofh.service.model.WeblinkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface WebLinkRepository extends CrudRepository<WeblinkEntity, Long> {
    @Query(value = "select n.* from tbl_web_link n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_link like concat('%',:link,'%') or :link = '') "
            + " and (n.col_image like concat('%',:image,'%') or :image = '') "
            + " and (date(n.col_created_date) >= :startDate or :startDate= '1970-01-01') "
            + " and (date(n.col_created_date) <= :endDate or :endDate= '1970-01-01') "

            , countQuery = "select count(n.id) from tbl_web_link n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_link like concat('%',:link,'%') or :link = '') "
            + " and (n.col_image like concat('%',:image,'%') or :image = '') "
            + " and (date(n.col_created_date) >= :startDate or :startDate= '1970-01-01') "
            + " and (date(n.col_created_date) <= :endDate or :endDate= '1970-01-01') "
            , nativeQuery = true)
    Page<WeblinkEntity> search(
            @Param("name") String name,
            @Param("link") String link,
            @Param("image") String image,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable defaultPage);
}
