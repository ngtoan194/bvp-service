package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.HighLightImageEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/high-light-image")
public class HighLightImageController extends BaseController {

    public HighLightImageController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<HighLightImageEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (HighLightImageEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(HighLightImageEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(HIGH_LIGHT_IMAGE, entity);
        return mapData;
    }

    @ApiOperation(value = "Create a high light image", notes = "{\"highLightImage\":{\"name\":\"Khoa khám bệnh\",\"images\":\"image1,image2\",\"url\":\"dfdfd\",\"webType\":2}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            HighLightImageEntity entity = getObject(HIGH_LIGHT_IMAGE, HighLightImageEntity.class);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search high light image", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "webType", required = false, defaultValue = DefaultConst.NUMBER) Integer webType
    ) {
        try {
            init();
            Page<HighLightImageEntity> results = highLightImageRepository.search(name, webType, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update high light image", notes = "{\"highLightImage\":{\"name\":\"Khoa khám bệnh\",\"images\":\"image1,image2\",\"url\":\"dfdfd\",\"webType\":2}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            HighLightImageEntity data = getObject(HIGH_LIGHT_IMAGE, HighLightImageEntity.class);
            HighLightImageEntity entity = getHighLightImage(id);
            entity.setName(data.getName());
            entity.setImage(data.getImage());
            entity.setUrl(data.getUrl());
            entity.setWebType(data.getWebType());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            highLightImageRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            HighLightImageEntity entity = getObject(HIGH_LIGHT_IMAGE, HighLightImageEntity.class);
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
