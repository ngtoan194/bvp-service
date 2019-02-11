package com.isofh.service.controller;

import com.isofh.service.model.ProvinceEntity;
import com.isofh.service.model.ResultEntity;
import com.isofh.service.model.UserEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/booking-province")
public class ProvinceController extends BaseController {

    public ProvinceController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<ProvinceEntity> provinces) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (ProvinceEntity province : provinces) {
            mapResults.add(getMapData(province));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(ProvinceEntity province) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(PROVINCES, province);
        return mapData;
    }

    /**
     * get all province
     *
     * @return
     */
    @ApiOperation(value = "Get all province", notes = "")
    @GetMapping(path = "/get-all")
    public ResponseEntity<?> getAll() {
        try {
            init();
            Iterable<ProvinceEntity> result = provinceRepository.findAll();
            ResultEntity resultEntity = ok("provinces", result);
            return response(resultEntity);
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            ProvinceEntity entity = getObject(PROVINCE, ProvinceEntity.class);
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
