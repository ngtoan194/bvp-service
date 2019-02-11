package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.CourseEntity;
import com.isofh.service.model.CourseItemEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/course-item")
public class CourseItemController extends BaseController {

    public CourseItemController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<CourseItemEntity> courseItems) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (CourseItemEntity courseItem : courseItems) {
            mapResults.add(getMapData(courseItem));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(CourseItemEntity courseItem) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(COURSE, courseItem.getCourse());
        mapData.put(COURSE_ITEMS, courseItem);
        return mapData;
    }

    /**
     * tim kiem bai hoc
     *
     * @return
     */
    @ApiOperation(value = "Search course item", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "courseId", required = false, defaultValue = DefaultConst.NUMBER) Integer courseId,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name
    ) {
        try {
            init();
            Page<CourseItemEntity> results = courseItemRepository.search(courseId, name, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tao moi bai hoc
     *
     * @param object {"courseItem":{"name":"course name","file":"file"},"courseId":1}
     * @return
     */
    @ApiOperation(value = "Create a course item", notes = "{\"courseItem\":{\"name\":\"course name\",\"file\":\"file\"},\"courseId\":1}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            CourseItemEntity courseItem = getObject("courseItem", CourseItemEntity.class);
            Long courseId = getLong("courseId");
            CourseEntity course = getCourse(courseId);
            if (course != null) {
                courseItem.setCourse(course);
            }
            courseItemRepository.save(courseItem);
            return response(ok(getMapData(courseItem)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat bai hoc
     *
     * @param object {"courseItem":{"name":"course name","file":"file"}}
     * @return
     */
    @ApiOperation(value = "Update course item", notes = "{\"courseItem\":{\"name\":\"course name\",\"file\":\"file\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            CourseItemEntity data = getObject("courseItem", CourseItemEntity.class);
            CourseItemEntity entity = getCourseItem(id);
            entity.setName(data.getName());
            entity.setFiles(data.getFiles());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa khoa hoc
     *
     * @return
     */
    @ApiOperation(value = "Delete course item", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            courseItemRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * tim kiem tat bai hoc trong khoa hoc
     *
     * @return
     */
    @ApiOperation(value = "Search course", notes = "")
    @GetMapping(path = "/get-by-course/{courseId}")
    public ResponseEntity<?> getByCourse(
            @PathVariable("courseId") Long courseId
    ) {
        try {
            init();
            List<CourseItemEntity> results = courseItemRepository.getByCourse(courseId);
            CourseEntity courseEntity = getCourse(courseId);
            return response(ok(Arrays.asList(COURSE, COURSE_ITEMS), Arrays.asList(courseEntity, results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            CourseItemEntity entity = getObject(COURSE_ITEM, CourseItemEntity.class);
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

    @ApiOperation(value = "update-course", notes = "")
    @PostMapping(path = "/update-course")
    public ResponseEntity<?> updateConference(@RequestBody Object object) {
        try {
            init(object);
            String courseUid = getString("courseUid");
            String courseItemUid = getString("courseItemUid");
            CourseEntity course = courseRepository.findFirstByUid(courseUid);
            CourseItemEntity courseItem = courseItemRepository.findFirstByUid(courseItemUid);
            if (course == null || courseItem == null) {
                return response(error(new Exception("courseLao null or lessonLao null")));
            }
            courseItem.setCourse(course);
            save(courseItem);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
