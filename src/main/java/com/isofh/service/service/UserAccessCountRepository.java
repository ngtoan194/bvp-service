package com.isofh.service.service;

import com.isofh.service.model.UserAccessCountEntity;
import com.isofh.service.model.UserAccessEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserAccessCountRepository extends CrudRepository<UserAccessCountEntity, Long> {


}
