package com.isofh.service.service;

import com.isofh.service.model.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DocumentRepository extends CrudRepository<DocumentEntity, Long> {

    @Query(value = "select n.* from bvp_upgrade.tbl_document n left join bvp_upgrade.tbl_menu u on n.col_menu_id = u.id " +
             " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_author_name like concat('%',:authorName,'%') or :authorName = '') "
            + " and (n.col_code like concat('%',:code,'%') or :code = '') "
            + " and (date(n.col_published_date) = :publishedDate or :publishedDate = '1970-01-01') "
            + " and (date(n.col_effective_date) = :effectiveDate or :effectiveDate = '1970-01-01') "
            + " and (n.col_published_org like concat('%',:publishedOrg,'%') or :publishedOrg = '') "
            + " and (n.col_is_high_light =:isHighLight or :isHighLight = '-1') "
            + " and (n.col_type =:type or :type = '-1') "
            + " and (n.col_web_type =:webType or :webType = '-1') "
            + " and (n.col_menu_id =:menuId or u.col_parent_id =:menuId or :menuId = '-1') "
            + " and (n.col_conference_id =:conferenceId or :conferenceId = '-1') "
            , countQuery = "select count(n.id) from tbl_document n left join tbl_menu u on n.col_menu_id = u.id"
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_author_name like concat('%',:authorName,'%') or :authorName = '') "
            + " and (n.col_code like concat('%',:code,'%') or :code = '') "
            + " and (date(n.col_published_date) = :publishedDate or :publishedDate = '1970-01-01') "
            + " and (date(n.col_effective_date) = :effectiveDate or :effectiveDate = '1970-01-01') "
            + " and (n.col_published_org like concat('%',:publishedOrg,'%') or :publishedOrg = '') "
            + " and (n.col_is_high_light =:isHighLight or :isHighLight = '-1') "
            + " and (n.col_type =:type or :type = '-1') "
            + " and (n.col_web_type =:webType or :webType = '-1') "
            + " and (n.col_menu_id =:menuId or u.col_parent_id =:menuId or :menuId = '-1') "
            + " and (n.col_conference_id =:conferenceId or :conferenceId = '-1') "
            , nativeQuery = true)
    Page<DocumentEntity> search(
            @Param("name") String name,
            @Param("authorName") String authorName,
            @Param("code") String code,
            @Param("publishedDate") LocalDate publishedDate,
            @Param("effectiveDate") LocalDate effectiveDate,
            @Param("publishedOrg") String publishedOrg,
            @Param("isHighLight") Integer isHighLight,
            @Param("type") Integer type,
            @Param("webType") Integer webType,
            @Param("menuId") Integer menuId,
            @Param("conferenceId") Long conferenceId,
            Pageable defaultPage);

    DocumentEntity findFirstByLinkAlias(String linkAlias);

    @Query(value = "select n.* from tbl_document n"
            + " where true "
            + " and (n.col_name like concat('%',:query,'%') or n.col_author_name like concat('%',:query,'%') or n.col_published_year like concat('%',:query,'%')or n.col_document_number like concat('%',:query,'%') or :query = '') "
            + " and (n.col_web_type =2)"
            , countQuery = "select count(n.id) from tbl_document n"
            + " where true "
            + " and (n.col_name like concat('%',:query,'%') or n.col_author_name like concat('%',:query,'%') or n.col_published_year like concat('%',:query,'%')or n.col_document_number like concat('%',:query,'%') or :query = '') "
            + " and (n.col_web_type =2)"
            , nativeQuery = true)
    Page<DocumentEntity> searchByQuery(
            @Param("query") String query,
            Pageable pageable);


    DocumentEntity findFirstByUid(String documentUid);
}
