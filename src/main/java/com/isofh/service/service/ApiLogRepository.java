package com.isofh.service.service;

import com.isofh.service.model.ApiLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApiLogRepository extends CrudRepository<ApiLogEntity, Long> {
}
