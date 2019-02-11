package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.AlbumEntity;
import com.isofh.service.model.PageEntity;
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
@RequestMapping(path = "/album")
public class AlbumController extends BaseController {

    public AlbumController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<AlbumEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (AlbumEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(AlbumEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(ALBUM, entity);
        mapData.put(PAGE, entity.getPage());
        return mapData;
    }

    @ApiOperation(value = "Create a album", notes = "{\"album\": {\"name\": \"this is name of album\", \"imagePreview\": \"image preview\", \"images\": \"image1,image2,image3\"},\"pageId\":\"1\"}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            AlbumEntity entity = getObject(ALBUM, AlbumEntity.class);
            entity.setLinkAlias(getTextUrl(entity.getName()));

            Long pageId = getLong("pageId");
            if (pageId != null) {
                PageEntity page = getPage(pageId);
                if (page != null) {
                    entity.setPage(page);
                }
            }

            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search album", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "belongDepartment", required = false, defaultValue = DefaultConst.NUMBER) Integer belongDepartment,
            @RequestParam(value = "pageId", required = false, defaultValue = DefaultConst.NUMBER) Integer pageId
    ) {
        try {
            init();
            Page<AlbumEntity> results = albumRepository.search(name, belongDepartment, pageId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update album", notes = "{\"album\": {\"name\": \"this is name of album\", \"imagePreview\": \"image preview\", \"images\": \"image1,image2\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            AlbumEntity data = getObject(ALBUM, AlbumEntity.class);
            AlbumEntity entity = getAlbumById(id);
            entity.setName(data.getName());
            entity.setBelongDepartment(data.getBelongDepartment());
            entity.setLinkAlias(getTextUrl(entity.getName()));
            entity.setImagePreview(data.getImagePreview());
            entity.setImages(data.getImages());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete contribute", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            albumRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Lay chi tiet theo id", notes = "")
    @GetMapping(path = "/get-detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") long id) {
        try {
            init();
            AlbumEntity entity = getAlbumById(id);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "get-by-alias", notes = "")
    @GetMapping(path = "/get-by-alias/{alias}")
    public ResponseEntity<?> getDetail(@PathVariable("alias") String alias) {
        try {
            init();
            AlbumEntity entity = albumRepository.findFirstByLinkAlias(alias);
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
            AlbumEntity entity = getObject(ALBUM, AlbumEntity.class);
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
