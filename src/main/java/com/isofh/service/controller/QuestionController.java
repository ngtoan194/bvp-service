package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.CourseTestEntity;
import com.isofh.service.model.NewsEntity;
import com.isofh.service.model.QuestionEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/question")
public class QuestionController extends BaseController {

    public QuestionController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<QuestionEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (QuestionEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(QuestionEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(QUESTION, entity);
        mapData.put(COURSE_TEST, entity.getCourseTest());
        return mapData;
    }

    @ApiOperation(value = "Create a question", notes = "{\"question\":{\"content\":\"Khoa khám bệnh\",\"answers\":\"dfdggtgrg\",\"correctAnswer\":1},\"courseTestId\":3}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            QuestionEntity entity = getObject(QUESTION, QuestionEntity.class);
            Long courseTestId = getLong("courseTestId");
            CourseTestEntity courseTest = getCourseTest(courseTestId);
            if(courseTest==null){
                throw getException(2, "courseTest khong ton tai voi id " + courseTestId);
            }
            entity.setCourseTest(courseTest);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a question", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "courseTestId", required = false, defaultValue = DefaultConst.NUMBER) Long courseTestId
    ) {
        try {
            init();
            Page<QuestionEntity> results = questionRepository.search(courseTestId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update question", notes = "{\"question\":{\"content\":\"Khoa khám bệnh\",\"answers\":\"dfdggtgrg\",\"correctAnswer\":1}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            QuestionEntity data = getObject(QUESTION, QuestionEntity.class);
            QuestionEntity entity = getQuestion(id);
            entity.setContent(data.getContent());
            entity.setAnswers(data.getAnswers());
            entity.setCorrectAnswer(data.getCorrectAnswer());
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
            questionRepository.deleteById(id);
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
            QuestionEntity entity = getQuestion(id);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
