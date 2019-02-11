package com.isofh.service.service;

import com.isofh.service.model.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
    @Query(value = "select n.* from tbl_question n  left join tbl_course_test u on n.col_course_test_id = u.id"
            + " where true "
            + " and (n.col_course_test_id =:courseTestId or :courseTestId= -1) "
            , countQuery = "select count(n.id) from tbl_question n left join tbl_course_test u on n.col_course_test_id = u.id"
            + " where true "
            + " and (n.col_course_test_id =:courseTestId or :courseTestId= -1) "
            , nativeQuery = true)
    Page<QuestionEntity> search(
            @Param("courseTestId") Long courseTestId,
            Pageable defaultPage);
}
