package com.isofh.service.controller;

import com.isofh.service.model.TestEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/test")
public class TestController extends BaseController {

    public TestController() {
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

    @ApiOperation(value = "get", notes = "")
    @GetMapping(path = "/get")
    public ResponseEntity<?> search() {
        try {
            init();
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "post", notes = "{}")
    @PostMapping(path = "/post")
    public ResponseEntity<?> post(@RequestBody Object object) {
        try {
            init(object);
            TestEntity testEntity = getObject("test", TestEntity.class);
            save(testEntity);
            return response(ok(object));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
