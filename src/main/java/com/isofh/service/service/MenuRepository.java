package com.isofh.service.service;

import com.isofh.service.model.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends CrudRepository<MenuEntity, Long> {
    @Query(value = "select n.* from tbl_menu n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_level =:level or :level= -1) "
            + " and (n.col_web_type =:webType or :webType= -1) "
            + " and (n.col_is_active =:isActive or :isActive= -1) "

            , countQuery = "select count(n.id) from tbl_menu n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_level =:level or :level= -1) "
            + " and (n.col_web_type =:webType or :webType= -1) "
            + " and (n.col_is_active =:isActive or :isActive= -1) "

            , nativeQuery = true)
    Page<MenuEntity> search(@Param("name") String name,
                            @Param("level") Integer level,
                            @Param("webType") Integer webType,
                            @Param("isActive") Integer isActive,
                            Pageable paging);

    @Query(value = "select n.* from tbl_menu n "
            + " where true "
            + " and n.col_level = 0 "
            + " and (n.col_is_active =:isActive or :isActive= -1) "
            + " and (n.col_role & :role !=0 or :role= -1) "
            + " and (n.col_web_type =:webType or :webType= -1) "
            + " and (true or :isActive = -1 or :role = -1 or :webType = -1) "
            , nativeQuery = true)
    List<MenuEntity> getAll(
            @Param("isActive") Integer isActive,
            @Param("role") Integer role,
            @Param("webType") Integer webType);

    @Query(value = "select n.* from tbl_menu n "
            + " where true "
            + " and (n.col_group =:group or :group= -1) "
            , nativeQuery = true)
    List<MenuEntity> getByGroup(
            @Param("group") Integer group
    );

    MenuEntity findFirstByUid(String menuUid);
}
