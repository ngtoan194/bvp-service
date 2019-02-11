package com.isofh.service.controller;

import com.isofh.service.model.KeyValueEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/key-value")
public class KeyValueController extends BaseController {

    public KeyValueController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<KeyValueEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (KeyValueEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(KeyValueEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(KEY_VALUE, entity);
        return mapData;
    }

    @ApiOperation(value = "Create or update a keyvalue", notes = "{\"keyValue\":{\"key\":\"Khoa khám bệnh\",\"value\":\"dfdggtgrg\"}}")
    @PostMapping(path = "/create-or-update")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            KeyValueEntity data = getObject(KEY_VALUE, KeyValueEntity.class);
            KeyValueEntity keyValue = keyValueRepository.findFirstByKey(data.getKey());
            if (keyValue != null) {
                keyValue.setValue(data.getValue());
                save(keyValue);
            } else{
                keyValue = data;
            }
            save(keyValue);
            return response(ok(getMapData(keyValue)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "get Value", notes = "{\"key\":\"hhh\"}")
    @GetMapping(path = "/get-value/{key}")
    public ResponseEntity<?> getValue(@PathVariable("key") String key) {
        try {
            init();
            KeyValueEntity entity = keyValueRepository.findFirstByKey(key);
            if (entity == null) {
                throw getException(1, "Khong tim thay value voi key " + key);
            }
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            KeyValueEntity entity = getObject(KEY_VALUE, KeyValueEntity.class);
            LocalDateTime createdDate = entity.getCreatedDate();
            save(entity);
            if (createdDate != null) {
                entity.setCreatedDate(createdDate);
                save(entity);
            }
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
