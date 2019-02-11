package com.isofh.service.service;

import com.isofh.service.model.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VideoRepository extends CrudRepository<VideoEntity, Long> {

    @Query(value = "select n.* from tbl_video n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_is_hot_video =:isHotVideo or :isHotVideo= -1) "
            + " and (n.col_web_type =:webType or :webType= -1) "
            , countQuery = "select count(n.id) from tbl_video n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_is_hot_video =:isHotVideo or :isHotVideo= -1) "
            + " and (n.col_web_type =:webType or :webType= -1) "
            , nativeQuery = true)
    Page<VideoEntity> search(
            @Param("name") String name,
            @Param("isHotVideo") Integer isHotVideo,
            @Param("webType") Integer webType,
            Pageable defaultPage);

    VideoEntity findFirstByLinkAlias(String linkAlias);

}
