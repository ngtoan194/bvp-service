package com.isofh.service.service;

import com.isofh.service.model.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

    @Query(value = "select n.* from tbl_comment n left join tbl_user u on n.col_author_id = u.id"
            + " where true "
            + " and (n.col_post_id =:postId or :postId = '-1') "
            , countQuery = "select count(n.id) from tbl_comment n left join tbl_user u on n.col_author_id = u.id"
            + " where true "
            + " and (n.col_post_id =:postId or :postId = '-1') "
            , nativeQuery = true)
    Page<CommentEntity> search(
            @Param("postId") Integer postId,
            Pageable defaultPage);


    CommentEntity findFirstByUid(String uid);

}
