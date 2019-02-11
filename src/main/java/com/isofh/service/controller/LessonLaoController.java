package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.CourseLaoEntity;
import com.isofh.service.model.CourseTestEntity;
import com.isofh.service.model.LessonLaoEntity;
import com.isofh.service.utils.RandomUtils;
import com.isofh.service.utils.StrUtils;
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
@RequestMapping(path = "/lesson-lao")
public class LessonLaoController extends BaseController {

    public LessonLaoController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<LessonLaoEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (LessonLaoEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(LessonLaoEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(LESSON, entity);
        mapData.put(COURSE_LAO, entity.getCourseLao());
        mapData.put(LESSON_LAO_COMMENTS, entity.getLessonLaoComments());
        return mapData;
    }

    @ApiOperation(value = "Create a lesson", notes = "{\"lesson\":{\"name\":\"Khoa khám bệnh\",\"link\":\"dfdggtgrg\",\"image\":\"gfgfgggrg\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            LessonLaoEntity entity = getObject(LESSON, LessonLaoEntity.class);
            entity.setUid(RandomUtils.getRandomId());
            Long courseLaoId = getLong("courseLaoId");
            CourseLaoEntity courseLao = getCourseLao(courseLaoId);
            if (courseLao != null) {
                entity.setCourseLao(courseLao);
            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search Lesson Lao", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "type", required = false, defaultValue = DefaultConst.NUMBER) Integer type,
            @RequestParam(value = "courseLaoId", required = false, defaultValue = DefaultConst.NUMBER) Integer courseLaoId
    ) {
        try {
            init();
            Page<LessonLaoEntity> results = lessonLaoRepository.search(name, type, courseLaoId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update lesson", notes = "{\"lesson\":{\"name\":\"Khoa khám bệnh\",\"url\":\"dfdggtgrg\",\"thumbnail\":\"gfgfgggrg\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            LessonLaoEntity data = getObject(LESSON, LessonLaoEntity.class);
            LessonLaoEntity entity = getLession(id);
            entity.setName(data.getName());
            entity.setEndTime(data.getEndTime());
            entity.setFile(data.getFile());
            entity.setImages(data.getImages());
            entity.setLinkYoutube(data.getLinkYoutube());
            entity.setStartTime(data.getStartTime());
            entity.setType(data.getType());
            entity.getLessonLaoComments().clear();
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete lesson", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            lessonLaoRepository.deleteById(id);
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
            LessonLaoEntity entity = getObject(LESSON, LessonLaoEntity.class);
            LocalDateTime createdDate = entity.getCreatedDate();
            save(entity);
            if (createdDate != null) {
                entity.setCreatedDate(createdDate);
                save(entity);
            }
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-course-lao", notes = "")
    @PostMapping(path = "/update-course-lao")
    public ResponseEntity<?> updateConference(@RequestBody Object object) {
        try {
            init(object);
            String courseLaoUid = getString("courseLaoUid");
            String lessonLaoUid = getString("lessonLaoUid");
            CourseLaoEntity courseLao = courseLaoRepository.findFirstByUid(courseLaoUid);
            LessonLaoEntity lessonLao = lessonLaoRepository.findFirstByUid(lessonLaoUid);
            if (courseLao == null || lessonLao == null) {
                return response(error(new Exception("courseLao null or lessonLao null")));
            }
            lessonLao.setCourseLao(courseLao);
            save(lessonLao);
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
            LessonLaoEntity entity = getLessonLao(id);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "get-by-uid", notes = "{}")
    @GetMapping(path = "/get-by-uid/{uid}")
    public ResponseEntity<?> getByUid(@PathVariable("uid") String uid) {
        try {
            init();
            LessonLaoEntity entity = lessonLaoRepository.findFirstByUid(uid);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-uid", notes = "{}")
    @GetMapping(path = "/update-uid")
    public ResponseEntity<?> updateUid() {
        try {
            init();
            Iterable<LessonLaoEntity> all = lessonLaoRepository.findAll();
            all.forEach(entity-> {
                if(StrUtils.isNullOrWhiteSpace(entity.getUid())){
                    entity.setUid(RandomUtils.getRandomId());
                    save(entity);
                }
            });
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
