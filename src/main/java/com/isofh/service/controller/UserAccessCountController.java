package com.isofh.service.controller;

import com.isofh.service.model.UserAccessCountEntity;
import com.isofh.service.utils.RandomUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/user-access-count")
public class UserAccessCountController extends BaseController {

    public UserAccessCountController() {
        super();
    }

    private Map<String, Object> getMapData(UserAccessCountEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(USER_ACCESS_COUNT, entity);
        return mapData;
    }

    @ApiOperation(value = "get-count", notes = "{}")
    @GetMapping(path = "/get-count")
    public ResponseEntity<?> getCount() {
        try {
            init();
            UserAccessCountEntity entity = getUserAccessCount();
            int random = 2;
            int hour = LocalDateTime.now().getHour();
            if(hour > 22 || hour < 5){
                random   = RandomUtils.getRandomNumber(2,3);
            }else if(hour<8) {
                random = RandomUtils.getRandomNumber(3,5);
            }else {
                random = RandomUtils.getRandomNumber(6,8);
            }

            entity.setOnlineCount(entity.getOnlineCount()+random);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-online-count", notes = "{}")
    @GetMapping(path = "/update-online-count")
    public ResponseEntity<?> updateOnlineCount() {
        try {
            init();
            long onlineCount = userAccessRepository.countAllByLastRequestAfter(LocalDateTime.now().minusMinutes(10));
            UserAccessCountEntity entity = getUserAccessCount();
            entity.setOnlineCount(onlineCount);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-monthly-count", notes = "{}")
    @GetMapping(path = "/update-monthly-count")
    public ResponseEntity<?> updateMonthlyCount() {
        try {
            init();
            long monthlyCount = userAccessRepository.countAllByLastRequestAfter(LocalDateTime.now().minusDays(30));
            UserAccessCountEntity entity = getUserAccessCount();
            entity.setMonthlyCount(monthlyCount);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
