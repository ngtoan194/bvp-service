package com.isofh.service.service;

import com.isofh.service.model.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceRepository extends CrudRepository<DeviceEntity, Long> {

    DeviceEntity findFirstByDeviceId(String deviceId);

    @Transactional
    void deleteByDeviceId(String deviceId);
}
