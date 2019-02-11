package com.isofh.service.service;

import com.isofh.service.model.SlideEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SlideRepository extends CrudRepository<SlideEntity, Long> {

    @Query(value = "select n.* from tbl_slide n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_active =:active or :active = '-1') "
            + " and (n.col_interval_time =:intervalTime or :intervalTime = '-1') "
            + " and (n.col_auto_play =:autoPlay or :autoPlay = '-1') "
            + " and (n.col_web_type =:webType or :webType = '-1') "
            , countQuery = "select count(n.id) from tbl_slide n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_active =:active or :active = '-1') "
            + " and (n.col_interval_time =:intervalTime or :intervalTime = '-1') "
            + " and (n.col_auto_play =:autoPlay or :autoPlay = '-1') "
            + " and (n.col_web_type =:webType or :webType = '-1') "
            , nativeQuery = true)
    Page<SlideEntity> search(
            @Param("name") String name,
            @Param("active") Integer active,
            @Param("intervalTime") Long intervalTime,
            @Param("autoPlay") Integer autoPlay,
            @Param("webType") Integer webType,
            Pageable defaultPage);


    SlideEntity findFirstByUid(String slideUid);
}