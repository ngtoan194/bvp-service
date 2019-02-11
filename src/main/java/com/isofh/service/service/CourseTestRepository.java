package com.isofh.service.service;

import com.isofh.service.model.CourseTestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseTestRepository extends CrudRepository<CourseTestEntity, Long> {
    @Query(value = "select n.* from tbl_course_test n left join tbl_course_lao u on n.col_course_lao_id = u.id"
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name= '') "
            + " and (n.col_type =:type or :type= -1) "
            + " and (n.col_course_lao_id =:courseLaoId or :courseLaoId= -1) "
            , countQuery = "select count(n.id) from tbl_course_test n left join tbl_course_lao u on n.col_course_lao_id = u.id"
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name= '') "
            + " and (n.col_type =:type or :type= -1) "
            + " and (n.col_course_lao_id =:courseLaoId or :courseLaoId= -1) "
            , nativeQuery = true)
    Page<CourseTestEntity> search(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("courseLaoId") Long courseLaoId,
            Pageable defaultPage);

    @Query(value = "select u.* from tbl_course_test u where u.col_type = :type "
            + " and (u.col_course_lao_id =:courseId) limit 1 ", nativeQuery = true)
    CourseTestEntity findByType(@Param("type") Integer type,
                                @Param("courseId") Long courseId);

    CourseTestEntity findFirstByUid(String uid);
}
