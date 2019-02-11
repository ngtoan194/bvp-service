package com.isofh.service.service;

import com.isofh.service.model.CourseItemCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseItemCommentRepository extends CrudRepository<CourseItemCommentEntity, Long> {

    @Query(value = "select n.* from tbl_course_item_comment n "
            + " where true "
            + " and (n.col_course_item_id =:courseItemId or :courseItemId = '-1') "
            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
            , countQuery = "select count(n.id) from tbl_course_item_comment n "
            + " where true "
            + " and (n.col_course_item_id =:courseItemId or :courseItemId = '-1') "
            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
            , nativeQuery = true)
    Page<CourseItemCommentEntity> search(
            @Param("courseItemId") Integer courseItemId,
            @Param("content") String content,
            Pageable defaultPage);

    CourseItemCommentEntity findFirstByUid(String courseItemCommentUid);
}
