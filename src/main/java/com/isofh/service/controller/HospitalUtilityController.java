package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.HospitalUtilityEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/hospital-utility")
public class HospitalUtilityController extends BaseController {

    public HospitalUtilityController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<HospitalUtilityEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (HospitalUtilityEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(HospitalUtilityEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(HOSPITAL_UTILITY, entity);
        return mapData;
    }

    @ApiOperation(value = "Create a hospital utility", notes = "{\"hospitalUtility\":{\"name\":\"Khoa khám bệnh\",\"href\":\"dfdggtgrg\",\"image\":\"gfgfgggrg\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            HospitalUtilityEntity entity = getObject(HOSPITAL_UTILITY, HospitalUtilityEntity.class);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search hospital utility", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "href", required = false, defaultValue = DefaultConst.STRING) String href,
            @RequestParam(value = "image", required = false, defaultValue = DefaultConst.STRING) String image,
            @RequestParam(value = "startDate", defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate startDate,
            @RequestParam(value = "endDate", defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate endDate
    ) {
        try {
            init();
            Page<HospitalUtilityEntity> results = hospitalUtilityRepository.search(name, href, image, startDate, endDate, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update hospital utility", notes = "{\"hospitalUtility\":{\"name\":\"Khoa khám bệnh\",\"href\":\"dfdggtgrg\",\"image\":\"gfgfgggrg\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            HospitalUtilityEntity data = getObject(HOSPITAL_UTILITY, HospitalUtilityEntity.class);
            HospitalUtilityEntity entity = getHospitalUtility(id);
            entity.setName(data.getName());
            entity.setHref(data.getHref());
            entity.setImage(data.getImage());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete hospital utility", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            hospitalUtilityRepository.deleteById(id);
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
            HospitalUtilityEntity entity = getObject("hospitalUtility", HospitalUtilityEntity.class);
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
