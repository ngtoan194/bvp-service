package com.isofh.service.service;

import com.isofh.service.model.TokenPasswordEntity;
import org.springframework.data.repository.CrudRepository;

public interface TokenPasswordRepository extends CrudRepository<TokenPasswordEntity, Long> {

    TokenPasswordEntity findFirstByToken(String token);
}
