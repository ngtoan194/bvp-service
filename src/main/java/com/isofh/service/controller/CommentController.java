package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.enums.UserType;
import com.isofh.service.model.CommentEntity;
import com.isofh.service.model.PostEntity;
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
@RequestMapping(path = "/comment")
public class CommentController extends BaseController {

    public CommentController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<CommentEntity> comments) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (CommentEntity comment : comments) {
            mapResults.add(getMapData(comment));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(CommentEntity comment) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(AUTHOR, comment.getAuthor());
        mapData.put(COMMENT, comment);
        return mapData;
    }

    /**
     * comment vao bai viet or cau hoi
     *
     * @param object {"postId":"6","comment":{"content":"content","images":"image1, image2"}}
     * @return
     */
    @ApiOperation(value = "Create a comment", notes = "{\"postId\":\"6\",\"comment\":{\"content\":\"content\",\"images\":\"image1, image2\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            CommentEntity comment = getObject("comment", CommentEntity.class);
            comment.setAuthor(getCurrentUser());
            Long postId = getLong("postId");
            PostEntity postEntity = getPost(postId);
            postEntity.setCommentCount(postEntity.getComments().size() + 1);
            comment.setPost(postEntity);
            commentRepository.save(comment);

            if ((UserType.DOCTOR.getValue() & getCurrentUser().getRole()) != 0) {
                postEntity.setFirstDoctorComment(comment);
                save(postEntity);
            }

            return response(ok(getMapData(comment)));
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
            @RequestParam(value = "postId", required = false, defaultValue = DefaultConst.NUMBER) Integer postId
    ) {
        try {
            init();
            Page<CommentEntity> results = commentRepository.search(postId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * cap nhat comment
     *
     * @param object {"comment":{"content":"content update","images":"image1, image2"}}
     * @return
     */
    @ApiOperation(value = "Update a comment", notes = "{\"comment\":{\"content\":\"content update\",\"images\":\"image1, image2\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            CommentEntity comment = getObject("comment", CommentEntity.class);
            CommentEntity entity = getComment(id);
            PostEntity postEntity = getPost(getLong("postId"));
            postEntity.setCommentCount(postEntity.getCommentCount() + 1);
            save(postEntity);
            entity.setContent(comment.getContent());
            entity.setImages(comment.getImages());
            save(entity);
            commentRepository.save(entity);
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
            commentRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Duyet or huy duyet comment giai phap
     *
     * @param object {"isSolution":1}
     * @return
     */
    @ApiOperation(value = "Change status is published", notes = "{\"isSolution\":1}")
    @PutMapping(path = "/accept-as-solution/{id}")
    public ResponseEntity<?> acceptAsSolution(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer isSolution = getInteger("isSolution");
            CommentEntity entity = getComment(id);
            entity.setIsSolution(isSolution);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * like comment
     *
     * @param object {"isLiked":1}
     * @return
     */
    @ApiOperation(value = "Like comment", notes = "{\"isLiked\":1}")
    @PutMapping(path = "/like/{id}")
    public ResponseEntity<?> like(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer isLiked = getInteger("isLiked");
            CommentEntity entity = getComment(id);
            if (isLiked == 1) {
                entity.setLikeCount(entity.getLikeCount() + 1);
            } else if (isLiked == 0 && entity.getLikeCount() != 0) {
                entity.setLikeCount(entity.getLikeCount() - 1);
            } else if (isLiked == 0 && entity.getLikeCount() == 0) {
                entity.setLikeCount(0);
            }
            save(entity);
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
            CommentEntity entity = getObject("comment", CommentEntity.class);
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

    @ApiOperation(value = "update-author", notes = "")
    @PostMapping(path = "/update-author")
    public ResponseEntity<?> updateAuthor(@RequestBody Object object) {
        try {
            init(object);
            String commentUid = getString("commentUid");
            String authorUid = getString("authorUid");
            UserEntity author = userRepository.findFirstByUid(authorUid);
            CommentEntity comment = commentRepository.findFirstByUid(commentUid);
            if (author == null || comment == null) {
                return response(error(new Exception("post null or comment null")));
            }
            comment.setAuthor(author);
            save(comment);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-post", notes = "")
    @PostMapping(path = "/update-post")
    public ResponseEntity<?> updatePost(@RequestBody Object object) {
        try {
            init(object);
            String commentUid = getString("commentUid");
            String postUid = getString("postUid");
            PostEntity post = postRepository.findFirstByUid(postUid);
            CommentEntity comment = commentRepository.findFirstByUid(commentUid);
            if (post == null || comment == null) {
                return response(error(new Exception("post null or comment null")));
            }
            comment.setPost(post);
            save(comment);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
