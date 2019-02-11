package com.isofh.service.service;

import com.isofh.service.model.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {

    @Query(value = "select n.* from tbl_course n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            , countQuery = "select count(n.id) from tbl_course n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            , nativeQuery = true)
    Page<CourseEntity> search(
            @Param("name") String name,
            Pageable defaultPage);

    CourseEntity findFirstByUid(String courseUid);
}
