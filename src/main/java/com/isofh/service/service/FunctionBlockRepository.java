package com.isofh.service.service;

import com.isofh.service.model.FunctionBlockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FunctionBlockRepository extends CrudRepository<FunctionBlockEntity, Long> {
    @Query(value = "select n.* from tbl_function_block n "
            + " where true "
            + " and (n.col_title like concat('%',:title,'%') or :title = '') "
            , countQuery = "select count(n.id) from tbl_function_block n "
            + " where true "
            + " and (n.col_title like concat('%',:title,'%') or :title = '') "
            , nativeQuery = true)
    Page<FunctionBlockEntity> search(
            @Param("title") String title,
            Pageable defaultPage);
}
