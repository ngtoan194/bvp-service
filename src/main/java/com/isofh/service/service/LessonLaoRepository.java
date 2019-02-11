package com.isofh.service.service;

import com.isofh.service.model.LessonLaoEntity;
import com.isofh.service.model.UserCourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LessonLaoRepository extends CrudRepository<LessonLaoEntity, Long> {

    @Query(value = "select n.* from tbl_lesson_lao n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_type =:type or :type= -1) "
            + " and (n.col_course_lao_id =:courseLaoId or :courseLaoId= -1) "
            , countQuery = "select count(n.id) from tbl_lesson_lao n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_type =:type or :type= -1) "
            + " and (n.col_course_lao_id =:courseLaoId or :courseLaoId= -1) "
            , nativeQuery = true)
    Page<LessonLaoEntity> search(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("courseLaoId") Integer courseLaoId,
            Pageable defaultPage);
    @Query(value = "select * from tbl_lesson_lao n" +
            " where true"
            + " and (n.uid =:uid or :uid= -1) "
            , nativeQuery = true)
    LessonLaoEntity findFirstByUid(
            @Param("uid") String uid);
}
