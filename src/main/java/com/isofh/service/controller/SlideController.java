package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.SlideEntity;
import com.isofh.service.model.SlideItemEntity;
import com.isofh.service.utils.ArrayUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/slide")
public class SlideController extends BaseController {

    public SlideController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<SlideEntity> slides) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (SlideEntity slide : slides) {
            mapResults.add(getMapData(slide));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(SlideEntity slide) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(SLIDE, slide);
        return mapData;
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
            @RequestParam(value = "active", required = false, defaultValue = DefaultConst.NUMBER) Integer active,
            @RequestParam(value = "intervalTime", required = false, defaultValue = DefaultConst.NUMBER) Long intervalTime,
            @RequestParam(value = "autoPlay", required = false, defaultValue = DefaultConst.NUMBER) Integer autoPlay,
            @RequestParam(value = CommonField.WEB_TYPE, required = false, defaultValue = DefaultConst.NUMBER) Integer webType
    ) {
        try {
            init();
            Page<SlideEntity> results = slideRepository.search(name, active, intervalTime, autoPlay, webType, getDefaultPage(page, size));
            List<Map<String, Object>> mapResults = new ArrayList<>();
            for (SlideEntity entity : results.getContent()) {
                Map<String, Object> mapResult = new HashMap<>();
                mapResults.add(mapResult);
                mapResult.put(SLIDE, entity);
                mapResult.put(SLIDE_ITEMS, entity.getSlideItems());
            }
            return response(ok(results.getTotalElements(), mapResults));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tao moi slide item
     *
     * @param object {"slide":{"name":"SlideName","active":1,"autoPlay":0,"timeInterval":3000,"webType":1},"slideItemIds":[1,2]}
     * @return
     */
    @ApiOperation(value = "Create a slide item", notes = "{\"slide\":{\"name\":\"SlideName\",\"active\":1,\"autoPlay\":0,\"timeInterval\":3000,\"webType\":1},\"slideItemIds\":[1,2]}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            SlideEntity slide = getObject("slide", SlideEntity.class);
            Long[] slideItemIds = getObject("slideItemIds", Long[].class);
            if(!ArrayUtils.isNullOrEmpty(slideItemIds)) {
                for (Long slideItemId : slideItemIds) {
                    SlideItemEntity slideItemEntity = getSlideItem(slideItemId);
                    if (slideItemEntity != null) {
                        slide.getSlideItems().add(slideItemEntity);
                    }
                }
            }
            slideRepository.save(slide);
            return response(ok(Arrays.asList("slide", "slideItem"), Arrays.asList(slide, slide.getSlideItems())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat slide item
     *
     * @param object {"slide":{"name":"SlideName","active":1,"autoPlay":0,"timeInterval":3000,"webType":1},"slideItemIds":[1,2]}
     * @return
     */
    @ApiOperation(value = "Update slide item", notes = "{\"slide\":{\"name\":\"SlideName\",\"active\":1,\"autoPlay\":0,\"timeInterval\":3000,\"webType\":1},\"slideItemIds\":[1,2]}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            SlideEntity data = getObject("slide", SlideEntity.class);
            SlideEntity entity = getSlide(id);

            entity.setName(data.getName());
            entity.setActive(data.getActive());
            entity.setAutoPlay(data.getAutoPlay());
            entity.setIntervalTime(data.getIntervalTime());
            entity.setWebType(data.getWebType());

            entity.getSlideItems().clear();
            Long[] slideItemIds = getObject("slideItemIds", Long[].class);
            if(!ArrayUtils.isNullOrEmpty(slideItemIds)) {
                for (Long slideItemId : slideItemIds) {
                    SlideItemEntity slideItemEntity = getSlideItem(slideItemId);
                    if (slideItemEntity != null) {
                        entity.getSlideItems().add(slideItemEntity);
                    }
                }
            }
            save(entity);
            return response(ok(Arrays.asList("slide", "slideItem"), Arrays.asList(entity, entity.getSlideItems())));
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
            slideRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat trang thai Active
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
            SlideEntity entity = getSlide(id);
            entity.setActive(active);
            save(entity);
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
            SlideEntity entity = getObject("slide", SlideEntity.class);
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

    @ApiOperation(value = "update-slide-item", notes = "")
    @PostMapping(path = "/update-slide-item")
    public ResponseEntity<?> updateTag(@RequestBody Object object) {
        try {
            init(object);
            String slideUid = getString("slideUid");
            String slideItemUid = getString("slideItemUid");
            SlideEntity slide = slideRepository.findFirstByUid(slideUid);
            SlideItemEntity slideItem = slideItemRepository.findFirstByUid(slideItemUid);
            if (slide == null || slideItem == null) {
                return response(error(new Exception("post null or comment null")));
            }
            slide.getSlideItems().add(slideItem);
            save(slideItem);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
