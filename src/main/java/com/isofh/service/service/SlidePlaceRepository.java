package com.isofh.service.service;

import com.isofh.service.model.SlidePlaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SlidePlaceRepository extends CrudRepository<SlidePlaceEntity, Long> {

    @Query(value = "select  n.* from tbl_slide_place n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_web_type =:webType or :webType = '-1') "
            , countQuery = "select count(n.id) from tbl_slide_place n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_web_type =:webType or :webType = '-1') "
            , nativeQuery = true)
    Page<SlidePlaceEntity> search(
            @Param("name") String name,
            @Param("webType") Integer webType,
            Pageable pageable);

    @Query(value = "select  n.* from tbl_slide_place n "
            + " where true "
            + " and (n.col_name =:name or :name='') "
            , nativeQuery = true)
    List<SlidePlaceEntity> getName(
            @Param("name") String name);

    SlidePlaceEntity findFirstByUid(String slidePlaceUid);
}
