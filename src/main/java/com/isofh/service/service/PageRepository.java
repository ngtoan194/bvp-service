package com.isofh.service.service;

import com.isofh.service.model.PageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PageRepository extends CrudRepository<PageEntity, Long> {
    @Query(value = "select n.* from tbl_page n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_type =:type or :type= -1) "
            + " and (n.col_web_type =:webType or :webType= -1) "
            , countQuery = "select count(n.id) from tbl_page n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_type =:type or :type= -1) "
            + " and (n.col_web_type =:webType or :webType= -1) "
            , nativeQuery = true)
    Page<PageEntity> search(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("webType") Integer webType,
            Pageable defaultPage);

    PageEntity findFirstByLinkAlias(String linkAlias);

    PageEntity findFirstByUid(String uid);
}
