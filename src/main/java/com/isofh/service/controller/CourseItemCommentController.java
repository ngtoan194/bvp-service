package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.CourseItemCommentEntity;
import com.isofh.service.model.CourseItemEntity;
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
@RequestMapping(path = "/course-item-comment")
public class CourseItemCommentController extends BaseController {

    public CourseItemCommentController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<CourseItemCommentEntity> courseItemComments) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (CourseItemCommentEntity courseItemComment : courseItemComments) {
            mapResults.add(getMapData(courseItemComment));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(CourseItemCommentEntity courseItemComment) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(AUTHOR, courseItemComment.getAuthor());
        mapData.put(COURSE_ITEM_COMMENT, courseItemComment);
        return mapData;
    }

    /**
     * lay danh sach comment trong bai hoc
     *
     * @return
     */
    @ApiOperation(value = "Search comment in course item", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "courseItemId", required = false, defaultValue = DefaultConst.NUMBER) Integer courseItemId,
            @RequestParam(value = "content", required = false, defaultValue = DefaultConst.STRING) String content
    ) {
        try {
            init();
            Page<CourseItemCommentEntity> results = courseItemCommentRepository.search(courseItemId, content, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * comment trong bai hoc
     *
     * @param object {"courseItemId":1, "courseItemComment":{"content":"cau hoi"}}
     * @return
     */
    @ApiOperation(value = "Create a comment in course item", notes = "{\"courseItemId\":1, \"courseItemComment\":{\"content\":\"cau hoi\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);

            CourseItemCommentEntity courseItemComment = getObject("courseItemComment", CourseItemCommentEntity.class);
            Long courseItemId = getLong("courseItemId");
            CourseItemEntity courseItem = getCourseItem(courseItemId);
            if (courseItem != null) {
                courseItemComment.setCourseItem(courseItem);
            }
            courseItemComment.setAuthor(getCurrentUser());

            courseItemCommentRepository.save(courseItemComment);
            return response(ok(getMapData(courseItemComment)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * cap nhat comment
     *
     * @param object {"courseItemComment":{"content":"cau hoi"}}
     * @return
     */
    @ApiOperation(value = "Update comment", notes = "{\"courseItemComment\":{\"content\":\"cau hoi\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            CourseItemCommentEntity data = getObject("courseItemComment", CourseItemCommentEntity.class);
            CourseItemCommentEntity entity = getCourseItemComment(id);
            entity.setContent(data.getContent());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa comment
     *
     * @return
     */
    @ApiOperation(value = "Delete comment", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            courseItemCommentRepository.deleteById(id);
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
            CourseItemCommentEntity entity = getObject(COURSE_ITEM_COMMENT, CourseItemCommentEntity.class);
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

    @ApiOperation(value = "update-course-item", notes = "")
    @PostMapping(path = "/update-course-item")
    public ResponseEntity<?> updateConference(@RequestBody Object object) {
        try {
            init(object);
            String courseItemCommentUid = getString("courseItemCommentUid");
            String courseItemUid = getString("courseItemUid");
            CourseItemCommentEntity courseItemComment = courseItemCommentRepository.findFirstByUid(courseItemCommentUid);
            CourseItemEntity courseItem = courseItemRepository.findFirstByUid(courseItemUid);
            if (courseItemComment == null || courseItem == null) {
                return response(error(new Exception("courseLao null or lessonLao null")));
            }
            courseItemComment.setCourseItem(courseItem);
            save(courseItemComment);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-author", notes = "")
    @PostMapping(path = "/update-author")
    public ResponseEntity<?> updateAuthor(@RequestBody Object object) {
        try {
            init(object);
            String courseItemCommentUid = getString("courseItemCommentUid");
            String authorUid = getString("authorUid");
            CourseItemCommentEntity courseItemComment = courseItemCommentRepository.findFirstByUid(courseItemCommentUid);
            UserEntity author = userRepository.findFirstByUid(authorUid);
            if (courseItemComment == null || author == null) {
                return response(error(new Exception("courseLao null or lessonLao null")));
            }
            courseItemComment.setAuthor(author);
            save(courseItemComment);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
