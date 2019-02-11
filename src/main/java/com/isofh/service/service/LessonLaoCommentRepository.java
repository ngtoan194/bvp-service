package com.isofh.service.service;

import com.isofh.service.model.CourseLaoEntity;
import com.isofh.service.model.LessonLaoCommentEntity;
import com.isofh.service.model.LessonLaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LessonLaoCommentRepository extends CrudRepository<LessonLaoCommentEntity, Long> {
    @Query(value = " select * from tbl_lesson_lao_comment n" +
            " where true" +
            " and (n.col_content like concat('%',:content,'%') or :content = '')"
            , countQuery = " select count(n.id) from tbl_lesson_lao_comment n" +
            " where true" +
            " and (n.col_content like concat('%',:content,'%') or :content = '')"
            , nativeQuery = true)
    Page<LessonLaoCommentEntity> search(
            @Param("content") String content,
            Pageable defaultPage);

    @Query(nativeQuery = true,
            value = "select * from tbl_lesson_lao_comment n " +
                    " where n.col_lesson_lao_id=:lessonLaoId",
            countQuery = "select count(n.id) from tbl_lesson_lao_comment n " +
                    " where n.col_lesson_lao_id=:lessonLaoId"
    )
    Page<LessonLaoCommentEntity> getByLessonLao(@Param("lessonLaoId") Long lessonLaoId, Pageable defaultPage);
}
