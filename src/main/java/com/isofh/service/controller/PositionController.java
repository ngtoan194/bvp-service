package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.PositionEntity;
import com.isofh.service.model.TestEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/position")
public class PositionController extends BaseController {

    public PositionController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<PositionEntity> positions) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (PositionEntity position : positions) {
            mapResults.add(getMapData(position));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(PositionEntity position) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(POSITION, position);
        return mapData;
    }

    /**
     * tim kiem giao luu truc tuyen
     *
     * @return
     */
    @ApiOperation(value = "Search position", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name
    ) {
        try {
            init();
            Page<PositionEntity> results = positionRepository.search(name, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tao moi chuc vu
     *
     * @param object {"position":{"name":"name","value":"1"}}
     * @return
     */
    @ApiOperation(value = "Create a position", notes = "{\"position\":{\"name\":\"name\",\"value\":\"1\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            PositionEntity position = getObject("position", PositionEntity.class);
            String value = position.getValue();
            List<PositionEntity> result = positionRepository.checkExistedValue(value);
            if(result.size() == 1){
                throw getException(2, "value already exists !");
            } else {
                save(position);
            }
            return response(ok(getMapData(position)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat chuc vu
     *
     * @param object {"position":{"name":"name","value":"1"}}
     * @return
     */
    @ApiOperation(value = "Update ostion", notes = "{\"position\":{\"name\":\"name\",\"value\":\"1\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            PositionEntity data = getObject("position", PositionEntity.class);
            PositionEntity entity = getPosition(id);

            entity.setName(data.getName());
            entity.setValue(data.getValue());

            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa chuc vu
     *
     * @return
     */
    @ApiOperation(value = "Delete position", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            positionRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
