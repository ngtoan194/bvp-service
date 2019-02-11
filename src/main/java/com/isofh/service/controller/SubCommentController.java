package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.CommentEntity;
import com.isofh.service.model.SubCommentEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/sub-comment")
public class SubCommentController extends BaseController {

    public SubCommentController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<SubCommentEntity> subComments) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (SubCommentEntity subComment : subComments) {
            mapResults.add(getMapData(subComment));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(SubCommentEntity subComment) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(SUB_COMMENT, subComment);
        return mapData;
    }

    /**
     * comment vao comment trong bai viet
     *
     * @param object {"commentId":"6","subComment":{"content":"content","images":"image1, image2"}}
     * @return
     */
    @ApiOperation(value = "Create a sub comment", notes = "{\"commentId\":\"6\",\"subComment\":{\"content\":\"content\",\"images\":\"image1, image2\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            SubCommentEntity subComment = getObject("subComment", SubCommentEntity.class);
            subComment.setAuthor(getCurrentUser());
            Long commentId = getLong("commentId");
            CommentEntity comment = getComment(commentId);
            subComment.setComment(comment);
            subCommentRepository.save(subComment);
            return response(ok(getMapData(subComment)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * tim kiem comment trong post
     *
     * @return
     */
    @ApiOperation(value = "Search comment in post", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "commentId", required = false, defaultValue = DefaultConst.NUMBER) Integer commentId
    ) {
        try {
            init();
            Page<SubCommentEntity> results = subCommentRepository.search(commentId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * cap nhat sub comment
     *
     * @param object {"subComment":{"content":"content update","images":"image1, image2"}}
     * @return
     */
    @ApiOperation(value = "Update a sub comment", notes = "{\"subComment\":{\"content\":\"content update\",\"images\":\"image1, image2\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            SubCommentEntity subComment = getObject("subComment", SubCommentEntity.class);
            SubCommentEntity entity = getSubComment(id);
            entity.setContent(subComment.getContent());
            entity.setImages(subComment.getImages());
            subCommentRepository.save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa sub comment
     *
     * @return
     */
    @ApiOperation(value = "Delete sub comment", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            subCommentRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
