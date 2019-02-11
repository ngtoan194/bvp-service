package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.WeblinkEntity;
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
@RequestMapping(path = "/web-link")
public class WebLinkController extends BaseController {

    public WebLinkController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<WeblinkEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (WeblinkEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(WeblinkEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(WEB_LINK, entity);
        return mapData;
    }

    /**
     * tao moi link
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "Create a web link", notes = "{\"webLink\":{\"name\":\"Khoa khám bệnh\",\"link\":\"dfdggtgrg\",\"image\":\"gfgfgggrg\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            WeblinkEntity entity = getObject(WEB_LINK, WeblinkEntity.class);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a contribute", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "link", required = false, defaultValue = DefaultConst.STRING) String link,
            @RequestParam(value = "image", required = false, defaultValue = DefaultConst.STRING) String image,
            @RequestParam(value = "startDate", defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate startDate,
            @RequestParam(value = "endDate", defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate endDate
    ) {
        try {
            init();
            Page<WeblinkEntity> results = webLinkRepository.search(name, link, image, startDate, endDate, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update contribute", notes = "{\"webLink\":{\"name\":\"Khoa khám bệnh\",\"link\":\"dfdggtgrg\",\"image\":\"gfgfgggrg\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            WeblinkEntity data = getObject(WEB_LINK, WeblinkEntity.class);
            WeblinkEntity entity = getWebLink(id);
            entity.setName(data.getName());
            entity.setLink(data.getLink());
            entity.setImage(data.getImage());
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
            webLinkRepository.deleteById(id);
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
            WeblinkEntity entity = getObject(WEB_LINK, WeblinkEntity.class);
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
