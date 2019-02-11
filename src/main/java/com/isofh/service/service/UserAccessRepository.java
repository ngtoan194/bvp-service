package com.isofh.service.service;

import com.isofh.service.model.UserAccessEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface UserAccessRepository extends CrudRepository<UserAccessEntity, Long> {

    UserAccessEntity findFirstByDeviceId(String deviceId);

    long countAllByLastRequestAfter(LocalDateTime localDateTime);
}
