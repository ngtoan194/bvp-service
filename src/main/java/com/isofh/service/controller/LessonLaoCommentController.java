package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.LessonLaoCommentEntity;
import com.isofh.service.model.LessonLaoEntity;
import com.isofh.service.model.SessionEntity;
import com.isofh.service.model.UserEntity;
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
@RequestMapping(path = "/lesson-lao-comment")
public class LessonLaoCommentController extends BaseController {

    public LessonLaoCommentController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<LessonLaoCommentEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (LessonLaoCommentEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(LessonLaoCommentEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(LESSON_LAO_COMMENT, entity);
        mapData.put(USER, entity.getAuthor());
        return mapData;
    }

    @ApiOperation(value = "Search lession lao", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "content", required = false, defaultValue = DefaultConst.STRING) String content
    ) {
        try {
            init();
            Page<LessonLaoCommentEntity> results = lessonLaoCommentRepository.search(content, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));

        } catch (Exception ex) {
            return response(error(ex));
        }
    }
    @ApiOperation(value = "create", notes = "{}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            LessonLaoCommentEntity entity = getObject(LESSON_LAO_COMMENT, LessonLaoCommentEntity.class);
            Long userId = getLong("userId");
            Long lessonLaoId = getLong("lessonLaoId");

            UserEntity userEntity = getUser(userId);
            if (userEntity == null) {
                throw getException(1, "khong tim thay thong tin user");
            }

            LessonLaoEntity lessonLaoEntity = getLessonLao(lessonLaoId);
            if (lessonLaoEntity == null) {
                throw getException(1, "khong tim thay thong tin bai hoc");
            }

            entity.setAuthor(userEntity);
            entity.setLessonLao(lessonLaoEntity);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "delete", notes = "{}")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            lessonLaoCommentRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update", notes = "{}")
    @PostMapping(path = "/update/{id}")
    public ResponseEntity<?> post(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            LessonLaoCommentEntity entity = getLessonLaoComment(id);
            LessonLaoCommentEntity data = getObject(LESSON_LAO_COMMENT, LessonLaoCommentEntity.class);
            entity.setContent(data.getContent());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "get by lesson lao", notes = "{}")
    @GetMapping(path = "/get-by-lesson-lao/{lessonLaoId}")
    public ResponseEntity<?> getByLessonLao(
            @PathVariable("lessonLaoId") long lessonLaoId,
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size) {
        try {
            init();
            Page<LessonLaoCommentEntity> entities  = lessonLaoCommentRepository.getByLessonLao(lessonLaoId, getDefaultPage(page,size));
            return response(ok(entities.getTotalElements(), getMapDatas(entities.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


}
