package com.isofh.service.service;

import com.isofh.service.model.KeyValueEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface KeyValueRepository extends CrudRepository<KeyValueEntity, Long> {
    @Query(value = "select n.* from tbl_key_value n "
            + " where true "
            + " and (n.col_key like concat(:key) or :key = '') limit 1 "
            , nativeQuery = true)
    KeyValueEntity getValue(
            @Param("key") String key);

    KeyValueEntity findFirstByKey(String key);

}
