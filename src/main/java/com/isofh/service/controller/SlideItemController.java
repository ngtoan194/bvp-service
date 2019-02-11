package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.SlideItemEntity;
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
@RequestMapping(path = "/slide-item")
public class SlideItemController extends BaseController {

    public SlideItemController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<SlideItemEntity> slideItems) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (SlideItemEntity slideItem : slideItems) {
            mapResults.add(getMapData(slideItem));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(SlideItemEntity slideItem) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("slideItem", slideItem);
        return mapData;
    }

    /**
     * Tao moi slide item
     *
     * @param object {"slideItem":{"name":"toan","href":"http","image":"/images/abvcv.png","webType":1}}
     * @return
     */
    @ApiOperation(value = "Create a slide item", notes = "{\"slideItem\":{\"name\":\"toan\",\"href\":\"http\",\"image\":\"/images/abvcv.png\",\"webType\":1}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            SlideItemEntity slideItem = getObject("slideItem", SlideItemEntity.class);
            slideItemRepository.save(slideItem);
            return response(ok(getMapData(slideItem)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * tim kiem slide item
     *
     * @return
     */
    @ApiOperation(value = "Search slide item", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = CommonField.WEB_TYPE, required = false, defaultValue = DefaultConst.NUMBER) Integer webType
    ) {
        try {
            init();
            Page<SlideItemEntity> results = slideItemRepository.search(name, webType, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat slide item
     *
     * @param object {"slideItem":{"name":"toan","href":"http","image":"/images/abvcv.png","webType":1}}
     * @return
     */
    @ApiOperation(value = "Update slide item", notes = "{\"slideItem\":{\"name\":\"toan\",\"href\":\"http\",\"image\":\"/images/abvcv.png\",\"webType\":1}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            SlideItemEntity data = getObject("slideItem", SlideItemEntity.class);
            SlideItemEntity entity = getSlideItem(id);
            entity.setName(data.getName());
            entity.setHref(data.getHref());
            entity.setImage(data.getImage());
            entity.setWebType(data.getWebType());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa slide item
     *
     * @return
     */
    @ApiOperation(value = "Delete slide item", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            slideItemRepository.deleteById(id);
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
            SlideItemEntity entity = getObject("slideItem", SlideItemEntity.class);
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
