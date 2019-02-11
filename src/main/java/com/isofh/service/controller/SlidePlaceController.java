package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.SlideEntity;
import com.isofh.service.model.SlidePlaceEntity;
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
@RequestMapping(path = "/slide-place")
public class SlidePlaceController extends BaseController {

    public SlidePlaceController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<SlidePlaceEntity> slidePlaces) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (SlidePlaceEntity slidePlace : slidePlaces) {
            mapResults.add(getMapData(slidePlace));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(SlidePlaceEntity slidePlace) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(SLIDE_PLACE, slidePlace);
        mapData.put(SLIDE, slidePlace.getSlide());
        mapData.put(SLIDE_ITEMS, slidePlace.getSlide().getSlideItems());
        return mapData;
    }

    /**
     * tim kiem slide place
     *
     * @return
     */
    @ApiOperation(value = "Search slide place", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = CommonField.WEB_TYPE, required = false, defaultValue = DefaultConst.NUMBER) Integer webType
    ) {
        try {
            init();
            Page<SlidePlaceEntity> results = slidePlaceRepository.search(name, webType, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tao moi slide place
     *
     * @param object {"slidePlace":{"name":"slide-home","webType":1},"slideId":1}
     * @return
     */
    @ApiOperation(value = "Create a slide item", notes = "{\"slidePlace\":{\"name\":\"slide-home\",\"webType\":1},\"slideId\":1}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            SlidePlaceEntity slidePlace = getObject("slidePlace", SlidePlaceEntity.class);
            Long slideId = getLong("slideId");
            SlideEntity slide = getSlide(slideId);
            slidePlace.setSlide(slide);
            slidePlaceRepository.save(slidePlace);
            return response(ok(getMapData(slidePlace)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat slide place
     *
     * @param object {"slidePlace":{"name":"slide-home","webType":1},"slideId":1}
     * @return
     */
    @ApiOperation(value = "Update a slide item", notes = "{\"slidePlace\":{\"name\":\"slide-home\",\"webType\":1},\"slideId\":1}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            SlidePlaceEntity slidePlace = getObject("slidePlace", SlidePlaceEntity.class);
            SlidePlaceEntity entity = getSlidePlace(id);
            entity.setName(slidePlace.getName());
            entity.setWebType(slidePlace.getWebType());
            Long slideId = getLong("slideId");
            SlideEntity slide = getSlide(slideId);
            entity.setSlide(slide);
            slidePlaceRepository.save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa slide place
     *
     * @return
     */
    @ApiOperation(value = "Delete slide place", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            slidePlaceRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * tim kiem slide place theo dung ten
     *
     * @return
     */
    @ApiOperation(value = "Get name slide place", notes = "")
    @GetMapping(path = "/get-name/{name}")
    public ResponseEntity<?> getName(
            @PathVariable("name") String name
    ) {
        try {
            init();
            List<SlidePlaceEntity> results = slidePlaceRepository.getName(name);
            Map<String, Object> mapResult = new HashMap<>();
            for (SlidePlaceEntity entity : results) {
                mapResult.put(SLIDE_PLACE, entity);
                mapResult.put(SLIDE, entity.getSlide());
                mapResult.put(SLIDE_ITEMS, entity.getSlide().getSlideItems());
            }
            return response(ok(mapResult));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            SlidePlaceEntity entity = getObject("slidePlace", SlidePlaceEntity.class);
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

    @ApiOperation(value = "update-slide", notes = "")
    @PostMapping(path = "/update-slide")
    public ResponseEntity<?> updateTag(@RequestBody Object object) {
        try {
            init(object);
            String slideUid = getString("slideUid");
            String slidePlaceUid = getString("slidePlaceUid");
            SlideEntity slide = slideRepository.findFirstByUid(slideUid);
            SlidePlaceEntity slidePlace = slidePlaceRepository.findFirstByUid(slidePlaceUid);
            if (slide == null || slidePlace == null) {
                return response(error(new Exception("post null or comment null")));
            }
            slidePlace.setSlide(slide);
            save(slidePlace);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
