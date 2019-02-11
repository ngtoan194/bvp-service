package com.isofh.service.service;

import com.isofh.service.model.UserConferenceEntity;
import com.isofh.service.model.UserCourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCourseRepository extends CrudRepository<UserCourseEntity, Long> {
    @Query(value = "select n.* from tbl_user_course n left join tbl_user u on n.col_user_id = u.id"
            + " where true "
            + " and (u.col_name like concat('%',:name,'%') or :name = '') "
            + " and (u.col_username like concat('%',:username,'%') or :username = '') "
            + " and (n.col_certificate_code like concat('%',:certificateCode,'%') or :certificateCode = '') "
            + " and (n.col_course_lao_id = :courseLaoId or :courseLaoId= -1) "
            , countQuery = "select count(n.id) from tbl_user_course n left join tbl_user u on n.col_user_id = u.id"
            + " where true "
            + " and (u.col_name like concat('%',:name,'%') or :name = '') "
            + " and (u.col_username like concat('%',:username,'%') or :username = '') "
            + " and (n.col_certificate_code like concat('%',:certificateCode,'%') or :certificateCode = '') "
            + " and (n.col_course_lao_id = :courseLaoId or :courseLaoId= -1) "
            , nativeQuery = true)
    Page<UserCourseEntity> search(
            @Param("name") String name,
            @Param("username") String username,
            @Param("certificateCode") String certificateCode,
            @Param("courseLaoId") Integer courseLaoId,
            Pageable defaultPage);


    @Query(value = "select * from tbl_user_course n" +
            " where true"
            + " and (n.col_course_lao_id = :courseLaoId  or :courseLaoId = '-1')"
            , nativeQuery = true)
    Page<UserCourseEntity> findByCourseLao(
            @Param("courseLaoId") Long courseLaoId,
            Pageable defaultPage);

    @Query(value = "select count(n.id) from tbl_user_course n" +
            " where true"
            + " and (n.col_course_lao_id = :courseLaoId  )"
            + " and (n.col_user_id = :userId  )"
            , nativeQuery = true)
    Long countByUserAndCourseLao(
            @Param("userId") Long userId,
            @Param("courseLaoId") Long courseLaoId);

    @Query(value = "select * from tbl_user_course n" +
            " where true"
            + " and (n.col_course_lao_id = :courseLaoId  )"
            + " and (n.col_user_id = :userId  )"
            , nativeQuery = true)
    UserCourseEntity findFirstByUserAndCourseLao(@Param("userId") Long userId,
                                                 @Param("courseLaoId") Long courseLaoId);

    UserCourseEntity findFirstByUid(String userCourseUid);

    @Query(value = "select * from tbl_user_course n" +
            " where true"
            + " and (n.col_course_lao_id = :courseLaoId  )"
            , nativeQuery = true)
    List<UserCourseEntity> getByCourseLao(
            @Param("courseLaoId") Long courseLaoId);

    @Query(value = "select n.* from tbl_user_course n" +
            " where n.col_user_id = :userId order by id desc limit 1"
            , nativeQuery = true)
    UserCourseEntity findMaxId(
            @Param("userId") long userId);
}
