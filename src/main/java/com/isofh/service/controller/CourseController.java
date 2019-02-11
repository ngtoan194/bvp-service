package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.CourseEntity;
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
@RequestMapping(path = "/course")
public class CourseController extends BaseController {

    public CourseController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<CourseEntity> courses) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (CourseEntity course : courses) {
            mapResults.add(getMapData(course));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(CourseEntity course) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(COURSE, course);
        mapData.put(COURSE_ITEMS, course.getCourseItems());
        return mapData;
    }

    /**
     * tim kiem khoa hoc
     *
     * @return
     */
    @ApiOperation(value = "Search course", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name
    ) {
        try {
            init();
            Page<CourseEntity> results = courseRepository.search(name, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tao moi khoa hoc
     *
     * @param object {"course":{"name":"name2","thumbnail":"thumbnail"}}
     * @return
     */
    @ApiOperation(value = "Create a course", notes = "{\"course\":{\"name\":\"name2\",\"thumbnail2\":\"thumbnail\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            CourseEntity course = getObject("course", CourseEntity.class);
            courseRepository.save(course);
            return response(ok(getMapData(course)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat khoa hoc
     *
     * @param object {"course":{"name":"name2","thumbnail":"thumbnail"}}
     * @return
     */
    @ApiOperation(value = "Update course", notes = " {\"course\":{\"name\":\"name2\",\"thumbnail\":\"thumbnail\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            CourseEntity data = getObject("course", CourseEntity.class);
            CourseEntity entity = getCourse(id);

            entity.setName(data.getName());
            entity.setThumbnail(data.getThumbnail());
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
    @ApiOperation(value = "Delete course", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            courseRepository.deleteById(id);
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
            CourseEntity entity = getObject(COURSE, CourseEntity.class);
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

}
