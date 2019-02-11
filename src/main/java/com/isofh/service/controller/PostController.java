package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.*;
import com.isofh.service.utils.ArrayUtils;
import com.isofh.service.utils.GsonUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/post")
public class PostController extends BaseController {

    public PostController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<PostEntity> posts) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (PostEntity post : posts) {
            mapResults.add(getMapData(post));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(PostEntity post) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(POST, post);
        mapData.put(DEPARTMENT, post.getDepartment());
        mapData.put(TAGS, post.getTags());
        mapData.put(AUTHOR, post.getAuthor());
        mapData.put(ASIGNEE, post.getAssignee());
        return mapData;
    }

    /**
     * Dat cau hoi or tao bai viet
     *
     * @param object {"departmentId": "1", "tags": ["1","2"], "post": {"title": "dfasdf dfasd", "content": "fasdf sdfasd", "images": "image1,images", "privated": 1}}
     * @return
     */
    @ApiOperation(value = "Create a post", notes = "{\"departmentId\": \"1\", \"tags\": [\"1\",\"2\"], \"post\": {\"title\": \"dfasdf dfasd\", \"content\": \"fasdf sdfasd\", \"images\": \"image1,images\", \"privated\": 1}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            PostEntity post = getObject("post", PostEntity.class);
            post.setLinkAlias(getTextUrl(post.getTitle()));
            post.setAuthor(getCurrentUser());
            Long[] tagIds = getObject("tags", Long[].class);
            if (!ArrayUtils.isNullOrEmpty(tagIds)) {
                for (Long tagid : tagIds) {
                    TagEntity tagEntity = getTag(tagid);
                    if (tagid != null) {
                        post.getTags().add(tagEntity);
                    }
                }
            }
            Long departmentId = getLong("departmentId");
            DepartmentEntity departmentEntity = getDepartment(departmentId);
            post.setDepartment(departmentEntity);
            postRepository.save(post);
            save((post));
            return response(ok(getMapData(post)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * tim kiem cau hoi or bai viet
     *
     * @return
     */
    @ApiOperation(value = "Search post", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "isAnswered", required = false, defaultValue = DefaultConst.NUMBER) Integer isAnswered,
            @RequestParam(value = "isAssigned", required = false, defaultValue = DefaultConst.NUMBER) Integer isAssigned,
            @RequestParam(value = "isPublished", required = false, defaultValue = DefaultConst.NUMBER) Integer isPublished,
            @RequestParam(value = "authorId", required = false, defaultValue = DefaultConst.NUMBER) Long authorId,
            @RequestParam(value = "departmentId", required = false, defaultValue = DefaultConst.NUMBER) Long departmentId,
            @RequestParam(value = "assigneeId", required = false, defaultValue = DefaultConst.NUMBER) Long assigneeId,
            @RequestParam(value = "startTime", required = false, defaultValue = DefaultConst.DATE_TIME) @DateTimeFormat(pattern = AppConst.DATE_TIME_FORMAT) LocalDateTime startTime
    ) {
        try {
            init();
            Page<PostEntity> results = postRepository.search(isAnswered, isAssigned, isPublished, authorId, departmentId, assigneeId, startTime, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * cap nhat bai viet or cau hoi
     *
     * @param object {"departmentId": "1", "tags": ["1","2"], "post": {"title": "dfasdf dfasd", "content": "fasdf sdfasd", "images": "image1,images", "privated": 1}}
     * @return
     */
    @ApiOperation(value = "Update a post", notes = "{\"departmentId\": \"1\", \"tags\": [\"1\",\"2\"], \"post\": {\"title\": \"dfasdf dfasd\", \"content\": \"fasdf sdfasd\", \"images\": \"image1,images\", \"privated\": 1}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            PostEntity post = getObject("post", PostEntity.class);
            PostEntity entity = getPost(id);
            entity.setTitle(post.getTitle());
            entity.setContent(post.getContent());
            entity.setLinkAlias(getTextUrl(post.getTitle()));
            entity.setImages(post.getImages());
            entity.setIsPrivate(post.getIsPrivate());

            Long departmentId = getLong("departmentId");
            DepartmentEntity department = getDepartment(departmentId);
            entity.setDepartment(department);

            entity.getTags().clear();
            Long[] tagIds = getObject("tags", Long[].class);
            if (!ArrayUtils.isNullOrEmpty(tagIds)) {
                for (Long tagId : tagIds) {
                    TagEntity tagEntity = getTag(tagId);
                    if (tagId != null) {
                        entity.getTags().add(tagEntity);
                    }
                }
            }
            postRepository.save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa post
     *
     * @return
     */
    @ApiOperation(value = "Delete post", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            postRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Duyet or huy duyet bai viet cua bac si
     *
     * @param object {"isPublished":1}
     * @return
     */
    @ApiOperation(value = "Change status is published", notes = "{\"isPublished\":1}")
    @PutMapping(path = "/approved-post/{id}")
    public ResponseEntity<?> approvedPost(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer isPublished = getInteger("isPublished");
            PostEntity entity = getPost(id);
            entity.setIsPublished(isPublished);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    /**
     * Asignee post cho bac si tra loi
     *
     * @param object {"doctorId":"8","departmentId":"1"}
     * @return
     */
    @ApiOperation(value = "Assign post to a doctor", notes = "{\"assigneeId\":\"8\",\"departmentId\":\"1\"}")
    @PutMapping(path = "/assign/{id}")
    public ResponseEntity<?> assignPost(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            PostEntity entity = getPost(id);

            Long doctorId = getLong("assigneeId");
            UserEntity doctorAssignee = getUser(doctorId);

            Long departmentId = getLong("departmentId");
            DepartmentEntity department = getDepartment(departmentId);

            entity.setAssignee(doctorAssignee);
            entity.setDepartment(department);
            entity.setIsAssigned(1);

            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * like bai viet or cau hoi
     *
     * @param object {"isLiked":1}
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "Like post", notes = "{\"isLiked\":1}")
    @PutMapping(path = "/like/{id}")
    public ResponseEntity<?> like(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer isLiked = getInteger("isLiked");

            PostEntity entity = getPost(id);
            Long[] arrLikedUserIds = GsonUtils.toObject(entity.getLikedUserIds(), Long[].class);
            List<Long> likedUserIds = new ArrayList<>();
            if (arrLikedUserIds != null) {
                likedUserIds.addAll(Arrays.asList(arrLikedUserIds));
            }
            if (isLiked == 1) {
                Long userId = getUserId();
                if (!likedUserIds.contains(userId)) {
                    likedUserIds.add(userId);
                }
            } else if (isLiked == 0) {
                likedUserIds.remove(getUserId());
            }
            entity.setLikeCount(likedUserIds.size());
            entity.setLikedUserIds(GsonUtils.toStringCompact(likedUserIds));
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * like bai viet or cau hoi
     *
     * @param object {"isLiked":1}
     * @return
     */
    @ApiOperation(value = "follow post", notes = "{\"isFollowed\":1}")
    @PutMapping(path = "/follow/{id}")
    public ResponseEntity<?> follow(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer isFollowed = getInteger("isFollowed");
            PostEntity entity = getPost(id);
            Long[] arrFollowedUserIds = GsonUtils.toObject(entity.getLikedUserIds(), Long[].class);
            List<Long> followedUserIds = new ArrayList<>();
            if (arrFollowedUserIds != null) {
                followedUserIds.addAll(Arrays.asList(arrFollowedUserIds));
            }
            if (isFollowed == 1) {
                if (!followedUserIds.contains(id)) {
                    followedUserIds.add(id);
                }
            } else if (isFollowed == 0) {
                followedUserIds.remove(id);
            }
            UserEntity user = getCurrentUser();
            entity.getFollowedUsers().add(user);
            save(entity);
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
            PostEntity entity = getPost(id);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "get-by-alias news", notes = "")
    @GetMapping(path = "/get-by-alias/{alias}")
    public ResponseEntity<?> getDetail(@PathVariable("alias") String alias) {
        try {
            init();
            PostEntity entity = postRepository.findFirstByLinkAlias(alias);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * tim kiem cau hoi or bai viet
     *
     * @return
     */
    @ApiOperation(value = "get-posts-followed-by-user", notes = "")
    @GetMapping(path = "get-followed-by-user/{userId}")
    public ResponseEntity<?> getFollowedByUser(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @PathVariable("userId") long userId
    ) {
        try {
            init();
            Page<PostEntity> results = postRepository.getFollowedByUser(userId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Get Post by Tag
     *
     * @return
     */
    @ApiOperation(value = "get-by-tag", notes = "")
    @GetMapping(path = "get-by-tag/{tagId}")
    public ResponseEntity<?> getByTag(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "tagId", required = false, defaultValue = DefaultConst.NUMBER) Long tagId
    ) {
        try {
            init();
            Page<PostEntity> results = postRepository.getByTag(tagId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    /**
     * Admin change status of Post Approved/Not Approved
     * After that this post will be display as ads
     * 1- Not found Post
     * 2- Value is not valid
     *
     * @param postId id cua bai post
     * @param object {"isPublished":1}
     * @return {"code":0,"message":"no message","data":{"post":{"title":"Viêm tiết niệu","content":"Tôi thường xuyên bị tiểu buốt, liệu có phải là viêm tiết niệu ko? Bệnh này điều trị thế nào?","imageUrls":[],"thumbnailImageUrls":[],"categoryId":"002519151379929889081-cda49f30b6cb4ff9bd216d7e9191f846","isClassified":1,"assignedDate":1488257041000,"isPublished":1,"likeCount":1,"commentCount":2,"linkAlias":"viem-tiet-nieu-20170214110426000.html","titleSearch":"viem tiet nieu","contentSearch":"toi thuong xuyen bi tieu buot, lieu co phai la viem tiet nieu ko? benh nay dieu tri the nao?","answered":1,"isPrivate":0,"uid":"002519152557338931609-e663692c687e4e2395f5720b514a915c","createdDate":1487045066000,"updatedDate":1513911990171},"user":{"email":"ngo.giakiet@congdongyte.vn","nickname":"ngo.giakiet","fullname":"Ngô Gia Kiệt","job":"","company":"","dob":0,"address":"kim giang","gender":1,"thumbnailAvatarUrl":"http://123.24.206.9:38192/Thumbnail/14022017035154AM-002519152564857908719-da09bdce7f7046ab81389ca85bf4ea95.png","avatarUrl":"http://123.24.206.9:38192/Images/14022017035154AM-002519152564857908719-da09bdce7f7046ab81389ca85bf4ea95.png","phone":"09123344","socialId":"","socialType":0,"role":0,"isVerified":0,"jobTitle":"","isBlocked":0,"loginToken":"","adminRole":0,"uid":"002519152607980385500-ae030e82f81b49de84b88c53c032f8dd","createdDate":1487040001000,"updatedDate":1487040001000000},"tags":[]}}
     */
    @ApiOperation(value = "Them moi hoi vien cua Hoi Lao", notes = "{}")
    @PutMapping(path = "set-post-approved-status/{postId}")
    public ResponseEntity<?> setPostApprovedStatus(
            @PathVariable("postId") long postId,
            @RequestBody Object object) {
        try {
            init(object);
            Integer isPublished = getInteger("isPublished");
            PostEntity postEntity = getPost(postId);
            postEntity.setIsPublished(isPublished);
            postEntity.setIsAssigned(isPublished);
            save(postEntity);
            return response(ok(getMapData(postEntity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Đếm số lượng câu hỏi chưa duyệt", notes = "{}")
    @GetMapping(path = "get-classify-count")
    public ResponseEntity<?> getClassifyCount() {
        try {
            init();
            long count = postRepository.getClassifyCount();
            Map<String, Object> mapResult = new HashMap<>();
            mapResult.put("unclassifiedCount", count);
            return response(ok(mapResult));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Đếm số lượng câu hỏi chưa duyệt", notes = "{}")
    @GetMapping(path = "get-highlight-post")
    public ResponseEntity<?> getHighlightPost() {
        try {
            init();
            List<PostEntity> posts = postRepository.getHighlightPost();

            List<Map<String, Object>> mapResults = new ArrayList<>();
            for (PostEntity post : posts) {
                Map<String, Object> mapResult = new HashMap<>();
                mapResult.put("post", post);
                mapResult.put("postAuthor", post.getAuthor());
                mapResult.put("comment", post.getFirstDoctorComment());
                mapResult.put("commentAuthor", post.getFirstDoctorComment().getAuthor());
                mapResults.add(mapResult);
            }
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("data", mapResults);
            return response(ok(objectMap));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "import", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            PostEntity entity = getObject("post", PostEntity.class);
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


    @ApiOperation(value = "update-author", notes = "")
    @PostMapping(path = "/update-author")
    public ResponseEntity<?> updateAuthor(@RequestBody Object object) {
        try {
            init(object);
            String postUid = getString("postUid");
            String authorUid = getString("authorUid");
            UserEntity author = userRepository.findFirstByUid(authorUid);
            PostEntity post = postRepository.findFirstByUid(postUid);
            if (author == null || post == null) {
                return response(error(new Exception("post null or comment null")));
            }
            post.setAuthor(author);
            save(post);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-tag", notes = "")
    @PostMapping(path = "/update-tag")
    public ResponseEntity<?> updateTag(@RequestBody Object object) {
        try {
            init(object);
            String postUid = getString("postUid");
            String tagUid = getString("tagUid");
            TagEntity tag = tagRepository.findFirstByUid(tagUid);
            PostEntity post = postRepository.findFirstByUid(postUid);
            if (tag == null || post == null) {
                return response(error(new Exception("post null or comment null")));
            }
            post.getTags().add(tag);
            save(post);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-assignee", notes = "")
    @PostMapping(path = "/update-assignee")
    public ResponseEntity<?> updateAssignee(@RequestBody Object object) {
        try {
            init(object);
            String postUid = getString("postUid");
            String assigneeUid = getString("assigneeUid");
            UserEntity assignee = userRepository.findFirstByUid(assigneeUid);
            PostEntity post = postRepository.findFirstByUid(postUid);
            if (assignee == null || post == null) {
                return response(error(new Exception("post null or comment null")));
            }
            post.setAssignee(assignee);
            save(post);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-first-comment", notes = "")
    @PostMapping(path = "/update-first-comment")
    public ResponseEntity<?> updateFirstComment(@RequestBody Object object) {
        try {
            init(object);
            String commentUid = getString("commentUid");
            String postUid = getString("postUid");
            PostEntity post = postRepository.findFirstByUid(postUid);
            CommentEntity comment = commentRepository.findFirstByUid(commentUid);
            if (post == null || comment == null) {
                return response(error(new Exception("post null or comment null")));
            }
            post.setFirstDoctorComment(comment);
            save(post);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-department", notes = "")
    @PostMapping(path = "/update-department")
    public ResponseEntity<?> updateDepartment(@RequestBody Object object) {
        try {
            init(object);
            String departmentUid = getString("departmentUid");
            String postUid = getString("postUid");
            PostEntity post = postRepository.findFirstByUid(postUid);
            DepartmentEntity department = departmentRepository.findFirstByUid(departmentUid);
            if (post == null || department == null) {
                return response(error(new Exception("post null or comment null")));
            }
            post.setDepartment(department);
            save(post);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
