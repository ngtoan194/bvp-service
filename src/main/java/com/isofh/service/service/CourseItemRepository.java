package com.isofh.service.service;

import com.isofh.service.model.CourseItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseItemRepository extends CrudRepository<CourseItemEntity, Long> {

    @Query(value = "select n.* from tbl_course_item n "
            + " where true "
            + " and (n.col_course_id =:courseId or :courseId = '-1') "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            , countQuery = "select count(n.id) from tbl_course_item n "
            + " where true "
            + " and (n.col_course_id =:courseId or :courseId = '-1') "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            , nativeQuery = true)
    Page<CourseItemEntity> search(
            @Param("courseId") Integer courseId,
            @Param("name") String name,
            Pageable defaultPage);


    @Query(value = "select n.* from tbl_course_item n "
            + " where true "
            + " and (n.col_course_id =:courseId or :courseId = '-1') "
            , nativeQuery = true)
    List<CourseItemEntity> getByCourse(
            @Param("courseId") Long courseId);

    CourseItemEntity findFirstByUid(String courseItemUid);
}
