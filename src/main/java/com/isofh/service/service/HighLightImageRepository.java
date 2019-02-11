package com.isofh.service.service;

import com.isofh.service.model.HighLightImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface HighLightImageRepository extends CrudRepository<HighLightImageEntity, Long> {
    @Query(value = "select n.* from tbl_highlight_image n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_web_type =:webType or :webType= -1) "

            , countQuery = "select count(n.id) from tbl_highlight_image n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_web_type =:webType or :webType= -1) "
            , nativeQuery = true)
    Page<HighLightImageEntity> search(
            @Param("name") String name,
            @Param("webType") Integer webType,
            Pageable defaultPage);
}
