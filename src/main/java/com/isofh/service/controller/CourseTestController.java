package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.CourseLaoEntity;
import com.isofh.service.model.CourseTestEntity;
import com.isofh.service.model.QuestionEntity;
import com.isofh.service.utils.ArrayUtils;
import com.isofh.service.utils.CollectionUtils;
import com.isofh.service.utils.RandomUtils;
import com.isofh.service.utils.StrUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/course-test")
public class CourseTestController extends BaseController {

    public CourseTestController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<CourseTestEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (CourseTestEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(CourseTestEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(COURSE_TEST, entity);
        mapData.put(QUESTIONS, entity.getQuestions());
        return mapData;
    }

    @ApiOperation(value = "Create a courseTest", notes = "{\"courseTest\":{\"name\":\"Khoa khám bệnh\",\"link\":\"dfdggtgrg\",\"image\":\"gfgfgggrg\"}, \"courseLaoId\":1223}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            CourseTestEntity entity = getObject(COURSE_TEST, CourseTestEntity.class);
            entity.setUid(RandomUtils.getRandomId());
            Long courseLaoId = getLong("courseLaoId");
            if (courseLaoId == null) {
                throw getException(2, "thieu courseLaoId");
            }
            CourseLaoEntity courseLao = getCourseLao(courseLaoId);
            Long[] questionsIds = getObject("questionsIds", Long[].class);
            if(!ArrayUtils.isNullOrEmpty(questionsIds)) {
                for (Long questionsId : questionsIds) {
                    QuestionEntity questionEntity = getQuestion(questionsId);
                    if (questionEntity != null) {
                        entity.getQuestions().add(questionEntity);
                    }
                }
            }
            entity.setCourseLao(courseLao);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search course test", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "type", required = false, defaultValue = DefaultConst.NUMBER) Integer type,
            @RequestParam(value = "courseLaoId", required = false, defaultValue = DefaultConst.NUMBER) Long courseLaoId) {
        try {
            init();
            Page<CourseTestEntity> results = courseTestRepository.search(name, type, courseLaoId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update course test", notes = "{\"courseTest\":{\"name\":\"Khoa khám bệnh\",\"link\":\"dfdggtgrg\",\"image\":\"gfgfgggrg\"}, \"courseLaoId\":1223}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            CourseTestEntity data = getObject(COURSE_TEST, CourseTestEntity.class);
            CourseTestEntity entity = getCourseTest(id);
            entity.setName(data.getName());
            entity.setDuration(data.getDuration());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete course test", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            courseTestRepository.deleteById(id);
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
            CourseTestEntity entity = getCourseTest(id);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Get detail", notes = "{}")
    @GetMapping(path = "/get-by-uid/{uid}")
    public ResponseEntity<?> getByUid(@PathVariable("uid") String uid) {
        try {
            init();
            CourseTestEntity entity = courseTestRepository.findFirstByUid(uid);
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
            Iterable<CourseTestEntity> all = courseTestRepository.findAll();
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
