package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.*;
import com.isofh.service.utils.ArrayUtils;
import com.isofh.service.utils.EmailUtils;
import com.isofh.service.utils.StrUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/user-course")
public class UserCourseController extends BaseController {

    public UserCourseController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<UserCourseEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (UserCourseEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(UserCourseEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(USER_COURSE, entity);
        mapData.put(USER, entity.getUser());
        return mapData;
    }

    @ApiOperation(value = "create", notes = "{\"userIds\":[109,108],\"courseLaoId\":1,\"userCourse\":{\"finish\":1}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            Long[] listUserIds = getObject("userIds", Long[].class);
            Long courseLaoId = getLong("courseLaoId");
            int fromAdmin = getInteger("fromAdmin", 0);
            List<UserCourseEntity> userCourses = new ArrayList<>();
            CourseLaoEntity courseLaoEntity = getCourseLao(courseLaoId);

            if (ArrayUtils.isNullOrEmpty(listUserIds)) {
                throw getException(2, "khong ban ghi nao dc tao");
            }

            for (Long userId : listUserIds) {
                Long existedItem = userCourseRepository.countByUserAndCourseLao(userId, courseLaoId);
                if (existedItem > 0) continue;
                UserCourseEntity userCourseEntity = new UserCourseEntity();
                userCourseEntity.setCourseLao(courseLaoEntity);
                userCourseEntity.setIsVerified(fromAdmin == 1 ? 1 : 0);
                UserEntity userEntity = getUser(userId);
                if (userEntity == null) {
                    continue;
                }
                userCourseEntity.setUser(userEntity);
                save(userCourseEntity);
                userCourses.add(userCourseEntity);
            }
            updateUserIds(courseLaoId);
            return response(ok(Arrays.asList("courseLao", "userCourses"), Arrays.asList(courseLaoEntity, userCourses)));
        } catch (Exception ex) {
            return response(error(ex));
        }

    }

    @ApiOperation(value = "Update question", notes = "{\"video\":{\"question\":\"Khoa khám bệnh\",\"url\":\"dfdggtgrg\",\"thumbnail\":\"gfgfgggrg\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            UserCourseEntity data = getObject(USER_COURSE, UserCourseEntity.class);
            UserCourseEntity entity = getUserCourse(id);
            entity.setCertificate(data.getCertificate());
            entity.setCertificateCode(data.getCertificateCode());
            entity.setFinish(data.getFinish());
            entity.setIsCompletedPostTest(data.getIsCompletedPostTest());
            entity.setIsCompletedPreTest(data.getIsCompletedPreTest());
            entity.setIsIssuedCertificate(data.getIsIssuedCertificate());
            entity.setIsPaidFee(data.getIsPaidFee());
            entity.setIsVerified(data.getIsVerified());
            entity.setPostTestResults(data.getPostTestResults());
            entity.setPostTestScore(data.getPostTestScore());
            entity.setPreTestResults(data.getPreTestResults());
            entity.setPostTestScore(data.getPostTestScore());
            entity.setRequestCertificate(data.getRequestCertificate());
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
            userCourseRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a user course", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "username", required = false, defaultValue = DefaultConst.STRING) String username,
            @RequestParam(value = "certificateCode", required = false, defaultValue = DefaultConst.STRING) String certificateCode,
            @RequestParam(value = "courseLaoId", required = false, defaultValue = DefaultConst.NUMBER) Integer courseLaoId
    ) {
        try {
            init();
            Page<UserCourseEntity> results = userCourseRepository.search(name, username, certificateCode, courseLaoId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "set info", notes = "{}")
    @PutMapping(path = "/set-finish/{id}")
    public ResponseEntity<?> setFinish(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init(object);
            Integer data = getInteger("finish");
            UserCourseEntity entity = getUserCourse(id);
            entity.setFinish(data);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "set info", notes = "{\"requestCertificate\":1}")
    @PutMapping(path = "/set-request-certificate/{id}")
    public ResponseEntity<?> setRequestCertificate(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init(object);
            Integer requestCertificate = getInteger("requestCertificate");
            UserCourseEntity entity = getUserCourse(id);
            entity.setRequestCertificate(requestCertificate);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "set info", notes = "{\"isPaidFee\":1}")
    @PutMapping(path = "/set-paid-fee/{id}")
    public ResponseEntity<?> setPaidFee(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init(object);
            Integer isPaidFee = getInteger("isPaidFee");
            UserCourseEntity entity = getUserCourse(id);
            entity.setIsPaidFee(isPaidFee);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "set info", notes = "{\"isIssuedCertificate\":1, \"certificate\":\"/new_files/filepd_f3cad4e4_0ad2_44e4_b17d_9806e1150c85.pdf\"}")
    @PutMapping(path = "/set-issued-certificate/{id}")
    public ResponseEntity<?> setIssuedCertificate(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init(object);
            UserCourseEntity entity = getUserCourse(id);
            Integer isIssuedCertificate = getInteger("isIssuedCertificate");
            String certificate = getString("certificate");
            entity.setIsIssuedCertificate(isIssuedCertificate);
            entity.setCertificate(certificate);
            save(entity);
            if (isIssuedCertificate == 1) {
                try {
                    UserEntity user = entity.getUser();
                    if (!StrUtils.isNullOrWhiteSpace(certificate) && user != null && !StrUtils.isNullOrWhiteSpace(user.getEmail())) {
                        String link = AppConst.SERVICE + certificate;
                        save(new EmailEntity(user.getEmail(), "Chứng nhận khóa học Hội Lao và Bệnh Phổi Việt Nam", EmailUtils.getBodySendCertificate(user.getName(), link), AppConst.CC_EMAIL));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "set info", notes = "{\"testResults\":\"a,c,a,d,b,b,a,c,a,d\",\"testType\":2,\"userId\":1,\"courseLaoId\":1,\"requestCertificate\":1}")
    @PutMapping(path = "/set-result/{id}")
    public ResponseEntity<?> setResult(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init(object);
            String testResult = getString("testResults");
            String[] arStr = testResult.split("\\,");
            int testType = getInteger("testType", 0);
            Long userId = getLong("userId");
            Long courseLaoId = getLong("courseLaoId");
            int requestCertificate = getInteger("requestCertificate", 0);
            UserCourseEntity entity = getUserCourse(id);

            UserEntity userEntity = getUser(userId);
            CourseLaoEntity courseLao = getCourseLao(courseLaoId);
            // 1 preTest
            if (testType == 1) {
                entity.setPreTestResults(testResult);
                if (courseLao.getCourseTests().iterator().hasNext()) {
                    List<QuestionEntity> questions = new ArrayList<>();
                    CourseTestEntity pre = courseTestRepository.findByType(1, courseLaoId);
                    if (pre != null) {
                        for (int i = 0; i < pre.getQuestions().size(); i++) {
                            QuestionEntity element1 = pre.getQuestions().iterator().next();
                            questions.add(element1);
                        }
                    }
                    int len = arStr.length < questions.size() ? arStr.length : questions.size();
                    int correctAnswer = 0;
                    for (int i = 0; i < len; i++) {
                        if (arStr[i].equals(questions.get(i).getCorrectAnswer())) {
                            correctAnswer++;
                        }
                    }
                    entity.setPreTestScore(correctAnswer);
                    entity.setIsCompletedPreTest(1);
                    entity.setRequestCertificate(requestCertificate);
                }
            }
            // 2 postTest
            else if (testType == 2) {
                entity.setPostTestResults(testResult);

                if (courseLao.getCourseTests().iterator().hasNext()) {

                    List<QuestionEntity> questions = new ArrayList<>();
                    CourseTestEntity post = courseTestRepository.findByType(2, courseLaoId);
                    if (post != null) {
                        for (int i = 0; i < post.getQuestions().size(); i++) {
                            QuestionEntity element1 = post.getQuestions().iterator().next();
                            questions.add(element1);
                        }
                    }
                    int len = arStr.length < questions.size() ? arStr.length : questions.size();
                    int correctAnswer = 0;
                    for (int i = 0; i < len; i++) {
                        if (arStr[i].equals(questions.get(i).getCorrectAnswer())) {
                            correctAnswer++;
                        }
                    }
                    entity.setPostTestScore(correctAnswer);
                    entity.setIsCompletedPostTest(1);
                    entity.setRequestCertificate(requestCertificate);
                }

            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    @ApiOperation(value = "getByUserCourseLao", notes = "")
    @GetMapping(path = "/get-by-user-course-lao/{UserCourseLaoId}")
    public ResponseEntity<?> getByUserCourseLao(
            @PathVariable("UserCourseLaoId") Long UserCourseLaoId
    ) {
        try {
            init();
            UserCourseEntity entity = getUserCourse(UserCourseLaoId);
            if (entity == null) {
                throw getException(2, "khong co hoc vien nao co id nay");
            }
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "getByCourseLao", notes = "")
    @GetMapping(path = "/get-by-course-lao/{courseLaoId}")
    public ResponseEntity<?> getByCourseLao(
            @PathVariable("courseLaoId") Long courseLaoId,
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size
    ) {
        try {
            init();
            Page<UserCourseEntity> results = userCourseRepository.findByCourseLao(courseLaoId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "set Verified", notes = "{\"isPaidFee\":1}")
    @PutMapping(path = "/set-verified/{id}")
    public ResponseEntity<?> setVerified(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init(object);
            UserCourseEntity entity = getUserCourse(id);
            CourseLaoEntity courseLaoEntity= entity.getCourseLao();
            Integer isVerified = getInteger("isVerified");
            String certificate = getString("certificate");
            entity.setIsVerified(isVerified);
            entity.setCertificate(certificate);
            save(entity);
            if (isVerified == 1) {
                String subject = "Xác thực thành viên đăng ký khóa đào tạo trực tuyến";
                String linkWeb = AppConst.WEB_ADDRESS_HOILAO + "/dang-nhap";
                UserEntity user = entity.getUser();
                save(new EmailEntity(user.getEmail(), subject, EmailUtils.getBodyVerifyLaoMember(user.getName(),courseLaoEntity.getName(), linkWeb)));
            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Get detail", notes = "{}")
    @GetMapping(path = "/get-detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") long id) {
        try {
            init();
            UserCourseEntity entity = getUserCourse(id);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "lay theo user va course lao", notes = "")
    @GetMapping(path = "/get-by-user-and-course-lao")
    public ResponseEntity<?> getByUserAndCourseLao(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "courseLaoId") Long courseLaoId
    ) {
        try {
            init();
            UserCourseEntity entity = userCourseRepository.findFirstByUserAndCourseLao(userId, courseLaoId);
            if (entity == null) {
                throw getException(2, "khong tim thay ban ghi nao ");
            }
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            UserCourseEntity entity = getObject(USER_COURSE, UserCourseEntity.class);
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


    @ApiOperation(value = "update-user-course-lao", notes = "")
    @PostMapping(path = "/update-user-course-lao")
    public ResponseEntity<?> updateUserCourseLao(@RequestBody Object object) {
        try {
            init(object);
            String userCourseUid = getString("userCourseUid");
            String courseLaoUid = getString("courseLaoUid");
            String userUid = getString("userUid");
            UserEntity user = userRepository.findFirstByUid(userUid);
            UserCourseEntity userCourse = userCourseRepository.findFirstByUid(userCourseUid);
            CourseLaoEntity courselao = courseLaoRepository.findFirstByUid(courseLaoUid);
            if (userCourse == null || courselao == null || user == null) {
                return response(error(new Exception("userCourse null or courselao null or user = null")));
            }
            userCourse.setCourseLao(courselao);
            userCourse.setUser(user);
            save(userCourse);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
    @ApiOperation(value = "Lay chi tiet thông tin moi nhat", notes = "")
    @GetMapping(path = "/get-new-detail/{userId}")
    public ResponseEntity<?> getNewDetail(@PathVariable("userId") long userId) {
        try {
            init();
            UserCourseEntity entity= userCourseRepository.findMaxId(userId);
            if(entity!=null) {
                return response(ok(getMapData(entity)));
            } else {
                throw getException(1, "tai khoan chua dawng ki khoa hoc nao ");
            }
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
