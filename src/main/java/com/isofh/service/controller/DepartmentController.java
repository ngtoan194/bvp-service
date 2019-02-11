package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.DepartmentEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController extends BaseController {

    public DepartmentController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<DepartmentEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (DepartmentEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(DepartmentEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(DEPARTMENT, entity);
        return mapData;
    }

    @ApiOperation(value = "Create a news", notes = "{\"department\":{\"name\":\"Khoa khám bệnh\",\"isPublic\":9,\"index\":9}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            DepartmentEntity newsEntity = getObject(DEPARTMENT, DepartmentEntity.class);
            save(newsEntity);
            return response(ok(getMapData(newsEntity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Get all", notes = "")
    @GetMapping(path = "/get-all")
    public ResponseEntity<?> getAll() {
        try {
            init();
            Iterable<DepartmentEntity> departments = departmentRepository.findAll();
            return response(ok(DEPARTMENT, getMapDatas(departments)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search department", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "linkAlias", required = false, defaultValue = DefaultConst.STRING) String linkAlias
    ) {
        try {
            init();
            Page<DepartmentEntity> results = departmentRepository.search(linkAlias, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), results.getContent()));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search department", notes = "")
    @GetMapping(path = "/get-list-doctor-department")
    public ResponseEntity<?> getListGroupByDepartment() {
        try {
            init();
            List<DepartmentEntity> departments = departmentRepository.getListGroupByDepartment();
            List<Map<String, Object>> mapResults = new ArrayList<>();
            for (DepartmentEntity department : departments) {
                Map<String, Object> mapDepartment = new HashMap<>();
                mapDepartment.put(DEPARTMENT, department);
                mapDepartment.put(DOCTORS, department.getUsers());
                mapResults.add(mapDepartment);
            }
            return response(ok(DEPARTMENTS, mapResults));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            DepartmentEntity entity = getObject("department", DepartmentEntity.class);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
