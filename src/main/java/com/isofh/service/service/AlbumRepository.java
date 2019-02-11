package com.isofh.service.service;

import com.isofh.service.model.AlbumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends CrudRepository<AlbumEntity, Long> {
    @Query(value = "select n.* from tbl_album n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_belong_department =:belongDepartment or :belongDepartment= -1) "
            + " and (n.col_page_id =:pageId or :pageId= -1) "
            , countQuery = "select count(n.id) from tbl_album n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_belong_department =:belongDepartment or :belongDepartment= -1) "
            + " and (n.col_page_id =:pageId or :pageId= -1) "
            , nativeQuery = true)
    Page<AlbumEntity> search(
            @Param("name") String name,
            @Param("belongDepartment") Integer belongDepartment,
            @Param("pageId") Integer pageId,
            Pageable defaultPage);

    AlbumEntity findFirstByLinkAlias(String linkAlias);
}
