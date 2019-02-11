package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.ProvinceEntity;
import com.isofh.service.model.RepresentRoomEntity;
import com.isofh.service.model.UserEntity;
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
@RequestMapping(path = "/represent-room")
public class RepresentRoomController extends BaseController {

    public RepresentRoomController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<RepresentRoomEntity> representRooms) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (RepresentRoomEntity representRoom : representRooms) {
            mapResults.add(getMapData(representRoom));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(RepresentRoomEntity representRoom) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(REPRESENT_ROOM, representRoom);
        return mapData;
    }


    @ApiOperation(value = "Create a represent room", notes = "{\"representRoom\":{\"name\":\"Khoa khám bệnh\",\"image\":\"image1\",\"url\":\"dfdfd\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            RepresentRoomEntity entity = getObject(REPRESENT_ROOM, RepresentRoomEntity.class);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search represent room", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name
    ) {
        try {
            init();
            Page<RepresentRoomEntity> results = representRoomRepository.search(name, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update represent room", notes = "{\"representRoom\":{\"name\":\"Khoa khám bệnh\",\"image\":\"image1\",\"url\":\"dfdfd\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            RepresentRoomEntity data = getObject(REPRESENT_ROOM, RepresentRoomEntity.class);
            RepresentRoomEntity entity = getRepresentRoom(id);
            entity.setName(data.getName());
            entity.setImage(data.getImage());
            entity.setUrl(data.getUrl());
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
            representRoomRepository.deleteById(id);
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
            RepresentRoomEntity entity = getObject(REPRESENT_ROOM, RepresentRoomEntity.class);
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
