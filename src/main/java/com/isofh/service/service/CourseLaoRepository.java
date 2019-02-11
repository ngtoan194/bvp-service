package com.isofh.service.service;

import com.isofh.service.model.CourseLaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseLaoRepository extends CrudRepository<CourseLaoEntity, Long> {

    @Query(value = " select * from tbl_course_lao n" +
            " where true" +
            " and (n.col_type =:type or :type = '-1') " +
            " and (n.col_name like concat('%',:name,'%') or :name = '')" +
            " and (n.col_conference_id =:conferenceId or :conferenceId = '-1') "
            , countQuery = " select count(n.id) from tbl_course_lao n" +
            " where true" +
            " and (n.col_type =:type or :type = '-1') " +
            " and (n.col_name like concat('%',:name,'%') or :name = '')" +
            " and (n.col_conference_id =:conferenceId or :conferenceId = '-1') "

            , nativeQuery = true)
    Page<CourseLaoEntity> search(
            @Param("type") Integer type,
            @Param("name") String name,
            @Param("conferenceId") Long conferenceId,
            Pageable defaultPage);

    CourseLaoEntity findFirstByUid(String courseLaoUid);
}
