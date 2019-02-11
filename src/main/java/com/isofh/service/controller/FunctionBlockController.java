package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.FunctionBlockEntity;
import com.isofh.service.model.RepresentRoomEntity;
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
@RequestMapping(path = "/function-block")
public class FunctionBlockController extends BaseController {

    public FunctionBlockController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<FunctionBlockEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (FunctionBlockEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(FunctionBlockEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(FUNCTION_BLOCK, entity);
        return mapData;
    }

    @ApiOperation(value = "Create a function block", notes = "{\"functionBlock\":{\"title\":\"Khoa khám bệnh\",\"content\":\"gfgfgggrg\",\"titleLink\":\"ffdf\",\"contentLink\":\"fđfdf\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            FunctionBlockEntity entity = getObject(FUNCTION_BLOCK, FunctionBlockEntity.class);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search function block", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "title", required = false, defaultValue = DefaultConst.STRING) String title
    ) {
        try {
            init();
            Page<FunctionBlockEntity> results = functionBlockRepository.search(title, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update function block", notes = "{\"functionBlock\":{\"title\":\"Khoa khám bệnh\",\"content\":\"gfgfgggrg\",\"titleLink\":\"ffdf\",\"contentLink\":\"fđfdf\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            FunctionBlockEntity data = getObject(FUNCTION_BLOCK, FunctionBlockEntity.class);
            FunctionBlockEntity entity = getFunctionBlock(id);
            entity.setTitle(data.getTitle());
            entity.setTitleLink(data.getTitleLink());
            entity.setContent(data.getContent());
            entity.setContentLink(data.getContentLink());
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
            functionBlockRepository.deleteById(id);
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
            FunctionBlockEntity entity = getObject(FUNCTION_BLOCK, FunctionBlockEntity.class);
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
