package com.isofh.service.service;

import com.isofh.service.model.PositionEntity;
import com.isofh.service.model.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PositionRepository extends CrudRepository<PositionEntity, Long> {

    @Query(value = "select n.* from tbl_position n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            , countQuery = "select count(n.id) from tbl_position n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            , nativeQuery = true)
    Page<PositionEntity> search(
            @Param("name") String name,
            Pageable defaultPage);

    @Query(value = "select n.* from tbl_position n "
            + " where true"
            + " and (n.col_value =:value or :value='') "
            , nativeQuery = true)
    List<PositionEntity> checkExistedValue(
            @Param("value") String value);

}
