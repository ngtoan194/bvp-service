package com.isofh.service.service;

import com.isofh.service.model.SubCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SubCommentRepository extends CrudRepository<SubCommentEntity, Long> {

    @Query(value = "select n.* from tbl_sub_comment n left join tbl_comment u on n.col_comment_id = u.id"
            + " where true "
            + " and (n.col_comment_id =:commentId or :commentId = '-1') "
            , countQuery = "select count(n.id) from tbl_comment n left join tbl_sub_comment u on n.col_comment_id = u.id"
            + " where true "
            + " and (n.col_comment_id =:commentId or :commentId = '-1') "
            , nativeQuery = true)
    Page<SubCommentEntity> search(
            @Param("commentId") Integer commentId,
            Pageable defaultPage);

}
