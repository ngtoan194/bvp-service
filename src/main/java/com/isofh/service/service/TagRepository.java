package com.isofh.service.service;

import com.isofh.service.model.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends CrudRepository<TagEntity, Long> {

    @Query(value = "select n.* from tbl_tag n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_link_alias like concat('%',:linkAlias,'%') or :linkAlias = '') "
            , countQuery = "select count(n.id) from tbl_tag n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_link_alias like concat('%',:linkAlias,'%') or :linkAlias = '') "
            , nativeQuery = true)
    Page<TagEntity> search(
            @Param("name") String name,
            @Param("linkAlias") String linkAlias,
            Pageable defaultPage);

    @Query(value = "select n.* from tbl_tag n "
            + " where true"
            + " and (n.col_name =:name or :name='') "
            , nativeQuery = true)
    List<TagEntity> checkExistedTagNames(
            @Param("name") String name);

    @Query(value = "select n.* from tbl_tag n"
            , nativeQuery = true)
    List<TagEntity> getAll();

    TagEntity findFirstByUid(String tagUid);
}
