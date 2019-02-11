package com.isofh.service.service;

import com.isofh.service.model.EmailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmailRepository extends CrudRepository<EmailEntity, Long> {

    @Query(value = "select n.* from tbl_email n "
            + " where true "
            + " and n.sent = :sent "
            + " order by n.id asc limit 10",
            nativeQuery = true)
    List<EmailEntity> findEmail(@Param("sent") int sent);
}
