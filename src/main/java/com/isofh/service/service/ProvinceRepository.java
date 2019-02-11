package com.isofh.service.service;

import com.isofh.service.model.ProvinceEntity;
import com.isofh.service.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProvinceRepository extends CrudRepository<ProvinceEntity, Long> {
    ProvinceEntity findFirstByUid(String userUid);

    ProvinceEntity findFirstByHisProvinceId(long hisProvinceId);
}
