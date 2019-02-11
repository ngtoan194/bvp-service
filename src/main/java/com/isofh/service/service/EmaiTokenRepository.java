package com.isofh.service.service;

import com.isofh.service.model.EmailTokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmaiTokenRepository extends CrudRepository<EmailTokenEntity, Long> {
    @Query(value = "select n.* from tbl_email_token n" +
            " where n.token = :token limit 1"
            , nativeQuery = true)
    EmailTokenEntity findByToken(@Param("token") String token);
}
