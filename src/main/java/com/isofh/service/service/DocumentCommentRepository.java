package com.isofh.service.service;

import com.isofh.service.model.DocumentCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DocumentCommentRepository extends CrudRepository<DocumentCommentEntity, Long> {

    @Query(value = "select n.* from tbl_document_comment n left join tbl_document u on n.col_document_id = u.id"
            + " where true "
            + " and (n.col_document_id =:documentId or :documentId = '-1') "
            , countQuery = "select count(n.id) from tbl_document_comment n left join tbl_document u on n.col_document_id = u.id"
            + " where true "
            + " and (n.col_document_id =:documentId or :documentId = '-1') "
            , nativeQuery = true)
    Page<DocumentCommentEntity> search(
            @Param("documentId") Long documentId,
            Pageable defaultPage);

}
