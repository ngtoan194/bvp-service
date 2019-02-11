package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.VideoEntity;
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
@RequestMapping(path = "/video")
public class VideoController extends BaseController {

    public VideoController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<VideoEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (VideoEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(VideoEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(VIDEO, entity);
        return mapData;
    }

    /**
     * @param object {"video":{"name":"name","url":"url youtube","thumbnail":"image", "webType":1, "isHotVideo":1}}
     * @return
     */
    @ApiOperation(value = "Create a video", notes = "{\"video\":{\"name\":\"name\",\"url\":\"url youtube\",\"thumbnail\":\"image\", \"webType\":1, \"isHotVideo\":1}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            VideoEntity entity = getObject(VIDEO, VideoEntity.class);
            entity.setLinkAlias(getTextUrl(entity.getName()));
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a video", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "isHotVideo", required = false, defaultValue = DefaultConst.NUMBER) Integer isHotVideo,
            @RequestParam(value = "webType", required = false, defaultValue = DefaultConst.NUMBER) Integer webType
    ) {
        try {
            init();
            Page<VideoEntity> results = videoRepository.search(name, isHotVideo, webType, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * @param object {"video":{"name":"name","url":"url youtube","thumbnail":"image", "isHotVideo":1}}
     * @param id
     * @return
     */
    @ApiOperation(value = "Update video", notes = "{\"video\":{\"name\":\"name\",\"url\":\"url youtube\",\"thumbnail\":\"image\", \"isHotVideo\":1}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            VideoEntity data = getObject(VIDEO, VideoEntity.class);
            VideoEntity entity = getVideo(id);
            entity.setName(data.getName());
            entity.setUrl(data.getUrl());
            entity.setLinkAlias(getTextUrl(data.getName()));
            entity.setThumbnail(data.getThumbnail());
            entity.setIsHotVideo(data.getIsHotVideo());
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
            videoRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat trang thai video
     *
     * @param object {"active":1}
     * @return
     */
    @ApiOperation(value = "Change status active", notes = "{\"active\":1}")
    @PutMapping(path = "/active/{id}")
    public ResponseEntity<?> active(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer active = getInteger("active");
            VideoEntity entity = getVideo(id);
            entity.setIsHotVideo(active);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation(value = "Lay chi tiet theo id", notes = "")
    @GetMapping(path = "/get-detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") long id) {
        try {
            init();
            VideoEntity entity = getVideo(id);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * @param alias
     * @return
     */
    @ApiOperation(value = "get-by-alias news", notes = "")
    @GetMapping(path = "/get-by-alias/{alias}")
    public ResponseEntity<?> getDetail(@PathVariable("alias") String alias) {
        try {
            init();
            VideoEntity entity = videoRepository.findFirstByLinkAlias(alias);
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
            VideoEntity entity = getObject(VIDEO, VideoEntity.class);
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
