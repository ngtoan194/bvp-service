package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.*;
import com.isofh.service.utils.ArrayUtils;
import com.isofh.service.utils.RandomUtils;
import com.isofh.service.utils.StrUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/course-lao")
public class CourseLaoController extends BaseController {

    public CourseLaoController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<CourseLaoEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (CourseLaoEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(CourseLaoEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(COURSE_LAO, entity);
        mapData.put(COURSE_TESTS, entity.getCourseTests());
        mapData.put(CONFERENCE, entity.getConference());
        return mapData;
    }

    @ApiOperation(value = "create", notes = "{\"courseLao\":{\"type\":1},\"courseTestIds\":[7,6]}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            CourseLaoEntity entity = getObject(COURSE_LAO, CourseLaoEntity.class);
            entity.setUid(RandomUtils.getRandomId());
            Long conferenceId = getLong("conferenceId");
            ConferenceEntity conference = getConference(conferenceId);
            if (conference != null) {
                entity.setConference(conference);
            }
            Long[] courseTestIds = getObject("courseTestIds", Long[].class);
            if (!ArrayUtils.isNullOrEmpty(courseTestIds)) {
                for (Long courseTestId : courseTestIds) {
                    CourseTestEntity courseTestEntity = getCourseTest(courseTestId);
                    if (courseTestEntity != null) {
                        entity.getCourseTests().add(courseTestEntity);
                    }
                }
            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update", notes = "{}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            CourseLaoEntity entity = getCourseLao(id);
            CourseLaoEntity data = getObject(COURSE_LAO, CourseLaoEntity.class);
            entity.setDescription(data.getDescription());
            entity.setEndTime(data.getEndTime());
            entity.setName(data.getName());
            entity.setImage(data.getImage());
            entity.setPrice(data.getPrice());
            entity.setStartTime(data.getStartTime());
            entity.setType(data.getType());
            Long conferenceId = getLong("conferenceId");
            ConferenceEntity conference = getConference(conferenceId);
            if (conference != null) {
                entity.setConference(conference);
            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "delete", notes = "{}")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            courseLaoRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Get detail", notes = "{}")
    @GetMapping(path = "/get-detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") long id) {
        try {
            init();
            CourseLaoEntity entity = getCourseLao(id);
            List<UserEntity> users = userRepository.getByCourseLao(entity.getId());
            Map<String, Object> mapData = getMapData(entity);
            mapData.put(USERS, users);
            Set<LessonLaoEntity> lessonLaos = entity.getLessonLaos();
            List<LessonLaoEntity> lessons = new ArrayList<>();
            if (lessonLaos != null && !lessonLaos.isEmpty()) {
                //lessons.sort((o1, o2) -> Long.compare(o2.getId(), o1.getId()));
                lessons.addAll(lessonLaos);
                lessons.sort(Comparator.comparing(LessonLaoEntity::getCreatedDate));
            }
            mapData.put(LESSON_LAOS, lessons);
            return response(ok(mapData));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search", notes = "{}")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "type", required = false, defaultValue = DefaultConst.NUMBER) Integer type,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "conferenceId", required = false, defaultValue = DefaultConst.NUMBER) Long conferenceId
    ) {
        try {
            init();
            Page<CourseLaoEntity> results = courseLaoRepository.search(type, name, conferenceId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            CourseLaoEntity entity = getObject(COURSE_LAO, CourseLaoEntity.class);
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

    @ApiOperation(value = "update-conference", notes = "")
    @PostMapping(path = "/update-conference")
    public ResponseEntity<?> updateConference(@RequestBody Object object) {
        try {
            init(object);
            String conferenceUid = getString("conferenceUid");
            String courseLaoUid = getString("courseLaoUid");
            ConferenceEntity conference = conferenceRepository.findFirstByUid(conferenceUid);
            CourseLaoEntity courseLao = courseLaoRepository.findFirstByUid(courseLaoUid);
            if (conference == null || courseLao == null) {
                return response(error(new Exception("post null or comment null")));
            }
            courseLao.setConference(conference);
            save(courseLao);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-field", notes = "")
    @PostMapping(path = "/update-field")
    public ResponseEntity<?> updateField(@RequestBody Object object) {
        try {
            init(object);
            String uid = getString("uid");
            String image = getString("image");
            CourseLaoEntity courseLao = courseLaoRepository.findFirstByUid(uid);
            if (courseLao != null) {
                courseLao.setImage(image);
            }
            save(courseLao);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Get detail by uid", notes = "{}")
    @GetMapping(path = "/get-by-uid/{uid}")
    public ResponseEntity<?> getByUid(@PathVariable("uid") String uid) {
        try {
            init();
            CourseLaoEntity entity = courseLaoRepository.findFirstByUid(uid);
            return getDetail(entity.getId());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-uid", notes = "{}")
    @GetMapping(path = "/update-uid")
    public ResponseEntity<?> updateUid() {
        try {
            init();
            Iterable<CourseLaoEntity> courseLaos = courseLaoRepository.findAll();
            courseLaos.forEach(entity -> {
                if (StrUtils.isNullOrWhiteSpace(entity.getUid())) {
                    entity.setUid(RandomUtils.getRandomId());
                    save(entity);
                }
            });
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-user-ids", notes = "{}")
    @GetMapping(path = "/update-user-ids")
    public ResponseEntity<?> updateUserIds() {
        try {
            init();
            Iterable<CourseLaoEntity> courseLaos = courseLaoRepository.findAll();
            courseLaos.forEach(entity -> {
                updateUserIds(entity.getId());
            });
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


}

