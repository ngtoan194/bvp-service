package com.isofh.service.service;

import com.isofh.service.model.DepartmentEntity;
import com.isofh.service.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Long> {
    @Query(value = "select n.* from tbl_department n "
            + " where true "
            + " and (n.col_link_alias like concat('%',:linkAlias,'%') or :linkAlias = '') "
            , countQuery = "select count(n.id) from tbl_department n "
            + " where true "
            + " and (n.col_link_alias like concat('%',:linkAlias,'%') or :linkAlias = '') "
            , nativeQuery = true)
    Page<DepartmentEntity> search(
            @Param("linkAlias") String linkAlias,
            Pageable defaultPage);

    @Query(value = "select n.* from tbl_department n "
            , nativeQuery = true)
    List<DepartmentEntity> getListGroupByDepartment();

    DepartmentEntity findFirstByUid(String departmentUid);
}
