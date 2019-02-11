package com.isofh.service.service;

import com.isofh.service.model.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<PostEntity, Long> {

    @Query(value = "select n.* from tbl_post n left join tbl_user u on n.col_assignee_id = u.id"
            + " where true "
            + " and (n.col_is_answered =:isAnswered or :isAnswered = '-1') "
            + " and (n.col_is_assigned =:isAssigned or :isAssigned = '-1') "
            + " and (n.col_is_published =:isPublished or :isPublished = '-1') "
            + " and (n.col_author_id =:authorId or :authorId = '-1') "
            + " and (n.col_department_id =:departmentId or :departmentId = '-1') "
            + " and (n.col_assignee_id =:assigneeId or :assigneeId = '-1') "
            + " and (n.col_created_date >= :startTime or :startTime = '1970-01-01 00:00:00') "

            , countQuery = "select count(n.id) from tbl_post n left join tbl_user u on n.col_assignee_id = u.id"
            + " where true "
            + " and (n.col_is_answered =:isAnswered or :isAnswered = '-1') "
            + " and (n.col_is_assigned =:isAssigned or :isAssigned = '-1') "
            + " and (n.col_is_published =:isPublished or :isPublished = '-1') "
            + " and (n.col_author_id =:authorId or :authorId = '-1') "
            + " and (n.col_department_id =:departmentId or :departmentId = '-1') "
            + " and (n.col_assignee_id =:assigneeId or :assigneeId = '-1') "
            + " and (n.col_created_date >= :startTime or :startTime = '1970-01-01 00:00:00') "
            , nativeQuery = true)
    Page<PostEntity> search(
            @Param("isAnswered") Integer isAnswered,
            @Param("isAssigned") Integer isAssigned,
            @Param("isPublished") Integer isPublished,
            @Param("authorId") Long authorId,
            @Param("departmentId") Long departmentId,
            @Param("assigneeId") Long assigneeId, @Param("startTime") LocalDateTime startTime,
            Pageable defaultPage);


    PostEntity findFirstByLinkAlias(String alias);

    @Query(value = "select n.* from tbl_post n " +
            " join tbl_user_follow_post up on n.id = up.col_post_id and up.col_user_id = :userId"

            , countQuery = "select count(n.id) from tbl_post n "
            + " join tbl_user_follow_post up on n.id = up.col_post_id and up.col_user_id = :userId"
            , nativeQuery = true)
    Page<PostEntity> getFollowedByUser(@Param("userId") Long userId, Pageable defaultPage);


    @Query(value = "select n.* from tbl_post n " +
            " join tbl_post_tag pt on n.id = pt.col_post_id and pt.col_tag_id = :tagId"

            , countQuery = "select count(n.id) from tbl_post n "
            + " join tbl_post_tag pt on n.id = pt.col_post_id and pt.col_tag_id = :tagId"
            , nativeQuery = true)
    Page<PostEntity> getByTag(@Param("tagId") Long tagId, Pageable defaultPage);

    @Query(value = "select count(n.id) from tbl_post n" +
            " where true" +
            " and n.col_is_assigned = 0" +
            " and n.col_is_published = 0",
            nativeQuery = true)
    Long getClassifyCount();

    PostEntity findFirstByUid(String uid);

    @Query(value = "select n.* from tbl_post n\n" +
            "join tbl_comment c on n.col_first_doctor_comment_id = c.id\n" +
            "join tbl_user author on n.col_author_id = author.id \n" +
            "join tbl_user commentAuthor on c.col_author_id = commentAuthor.id order by c.col_created_date desc limit 2 "
            , nativeQuery = true)
    List<PostEntity> getHighlightPost();
}
