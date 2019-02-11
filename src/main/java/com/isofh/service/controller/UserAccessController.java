package com.isofh.service.controller;

import com.isofh.service.model.UserAccessCountEntity;
import com.isofh.service.model.UserAccessEntity;
import com.isofh.service.utils.RandomUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/user-access")
public class UserAccessController extends BaseController {

    public UserAccessController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<Integer> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (Integer entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(Integer entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("", entity);
        return mapData;
    }

    @ApiOperation(value = "create user access", notes = "{\"deviceId\":\"123456\"}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> post(@RequestBody Object object) {
        try {
            init(object);
            String deviceId = getString("deviceId");
            UserAccessEntity entity = userAccessRepository.findFirstByDeviceId(deviceId);
            boolean newDeviceId = false;
            if (entity == null) {
                entity = new UserAccessEntity();
                entity.setDeviceId(deviceId);
                entity.setLastActive(LocalDateTime.now());
                newDeviceId = true;

            }
            entity.setLastRequest(LocalDateTime.now());

            UserAccessCountEntity userAccessCount = getUserAccessCount();

            LocalDateTime tenMinutesBefore = LocalDateTime.now().minusMinutes(10);
            // lan cuoi cung active cach day 10p thi tang so luong monthly active + totalActive
            if (newDeviceId || tenMinutesBefore.isAfter(entity.getLastActive())) {
                int random = 1;
                userAccessCount.setTotalCount(userAccessCount.getTotalCount() + random);
                userAccessCount.setMonthlyCount(userAccessCount.getMonthlyCount() + random);
                userAccessCount.setOnlineCount(userAccessCount.getOnlineCount() + random);
                entity.setLastActive(LocalDateTime.now());
            }
            save(entity);
            save(userAccessCount);

            return response(ok(object));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
