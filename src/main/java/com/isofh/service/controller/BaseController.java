package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.enums.LogType;
import com.isofh.service.model.*;
import com.isofh.service.service.*;
import com.isofh.service.utils.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class BaseController {

    //region khai bao cacs constance name of database
    protected static final String USER = "user";
    protected static final String USERS = "users";
    protected static final String USER_CONFERENCE = "userConference";
    protected static final String CONFERENCE = "conference";
    protected static final String DEVICE = "device";
    protected static final String IMAGE = "image";
    protected static final String IMAGES = "images";
    protected static final String KEY_VALUE = "keyValue";
    protected static final String SCHEDULE = "schedule";
    protected static final String SCHEDULES = "schedules";
    protected static final String SUPPORT = "support";
    protected static final String CONFERENCE_TOPIC = "conferenceTopic";
    protected static final String CONFERENCE_SESSION = "conferenceSession";
    protected static final String CONFERENCE_QUESTION = "conferenceQuestion";
    protected static final String PROVINCE = "province";
    protected static final String PROVINCES = "provinces";
    protected static final String ADMIN_SALE = "adminSale";
    protected static final String ADVERTISE = "advertise";
    protected static final String USER_SESSION = "userSession";
    protected static final String SURVEY = "survey";
    protected static final String ANSWER = "answer";
    protected static final String USER_ANSWER = "userAnswer";
    protected static final String CONFERENCE_NOTIFICATION = "conferenceNotification";
    protected static final String SPECIALIST = "specialist";
    protected static final String SPECIALISTS = "specialists";
    protected static final String SYMPTOM = "symptom";
    protected static final String SYMPTOMS = "symptoms";
    protected static final String DISEASE = "disease";
    protected static final String DISEASES = "diseases";
    protected static final String FACILITY = "facility";
    protected static final String DRUG = "drug";
    protected static final String COUNTRY = "country";
    protected static final String METHOD_USES = "methodUses";
    protected static final String MENU = "menu";
    protected static final String MENUS = "menus";
    protected static final String PHARMACY = "pharmacy";
    protected static final String SLIDE_ITEM = "slideItem";
    protected static final String SLIDE_ITEMS = "slideItems";
    protected static final String SLIDE = "slide";
    protected static final String SLIDE_PLACE = "slidePlace";
    protected static final String DEPARTMENT = "department";
    protected static final String DEPARTMENTS = "departments";
    protected static final String DOCTOR = "doctor";
    protected static final String DOCTORS = "doctors";
    protected static final String ROOM = "room";
    protected static final String ROOMS = "rooms";
    protected static final String SERVICE = "service";
    protected static final String SERVICES = "services";
    protected static final String PROFILE = "profile";
    protected static final String PROFILES = "profiles";
    protected static final String DISTRICT = "district";
    protected static final String DISTRICTS = "districts";

    protected static final String ZONE = "zone";
    protected static final String ZONES = "zones";
    protected static final String BOOKING = "booking";
    protected static final String BOOKINGS = "bookings";
    protected static final String NOTIFICATION = "notification";
    protected static final String NEWS = "news";
    protected static final String WEB_LINK = "webLink";
    protected static final String VIDEO = "video";
    protected static final String HOSPITAL_UTILITY = "hospitalUtility";
    protected static final String FUNCTION_BLOCK = "functionBlock";
    protected static final String HIGH_LIGHT_IMAGE = "highLightImage";
    protected static final String KEYVALUE = "keyValue";
    protected static final String ONLINE_EXCHANGE = "onlineExchange";
    protected static final String ONLINE_EXCHANGE_QUESTION = "onlineExchangeQuestion";
    protected static final String ALBUM = "album";
    protected static final String PAGE = "page";
    protected static final String COURSE = "course";
    protected static final String COURSE_ITEMS = "courseItems";
    protected static final String COURSE_ITEM = "courseItem";
    protected static final String COURSE_ITEM_COMMENT = "courseItemComment";
    protected static final String SPONSOR = "sponsor";
    protected static final String TAG = "tag";
    protected static final String TAGS = "tags";
    protected static final String POST = "post";
    protected static final String QUESTION = "question";
    protected static final String QUESTIONS = "questions";
    protected static final String COURSE_TEST = "courseTest";
    protected static final String COURSE_TESTS = "courseTests";
    protected static final String COURSE_TEST_PRE = "courseTestPre";
    protected static final String COURSE_TEST_POST = "courseTestPost";
    protected static final String LESSON = "lesson";
    protected static final String LESSON_LAOS = "lessonLaos";
    protected static final String COURSE_LAO = "courseLao";
    protected static final String LESSON_LAO_COMMENT = "lessonLaoComment";
    protected static final String LESSON_LAO_COMMENTS = "lessonLaoComments";
    protected static final String AUTHOR = "author";
    protected static final String ASIGNEE = "assignee";
    protected static final String COMMENT = "comment";
    protected static final String SUB_COMMENT = "subComment";
    protected static final String DOCUMENT = "document";
    protected static final String REPRESENT_ROOM = "representRoom";
    protected static final String USER_COURSE = "userCourse";
    protected static final String SESSION = "session";
    protected static final String CONTRIBUTE = "contribute";
    protected static final String USER_ACCESS_COUNT = "userAccessCount";
    protected static final String POSITION = "position";


    //endregion

    /**
     * Log controller
     */
    protected LogUtils log;

    /**
     * The time the service is called, used to calculate "service running time"
     */
    protected long startTime;
    protected JSONObject jsonData;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected DeviceRepository deviceRepository;
    @Autowired
    protected MenuRepository menuRepository;
    @Autowired
    protected PageRepository pageRepository;
    @Autowired
    protected NewsRepository newsRepository;
    @Autowired
    protected TokenPasswordRepository tokenPasswordRepository;
    @Autowired
    protected ContributeRepository contributeRepository;
    @Autowired
    protected DepartmentRepository departmentRepository;
    @Autowired
    protected TagRepository tagRepository;
    @Autowired
    protected SlideRepository slideRepository;
    @Autowired
    protected SlideItemRepository slideItemRepository;
    @Autowired
    protected SlidePlaceRepository slidePlaceRepository;
    @Autowired
    protected WebLinkRepository webLinkRepository;
    @Autowired
    protected VideoRepository videoRepository;
    @Autowired
    protected HospitalUtilityRepository hospitalUtilityRepository;
    @Autowired
    protected FunctionBlockRepository functionBlockRepository;
    @Autowired
    protected HighLightImageRepository highLightImageRepository;
    @Autowired
    protected KeyValueRepository keyValueRepository;
    @Autowired
    protected OnlineExchangeRepository onlineExchangeRepository;
    @Autowired
    protected OnlineExchangeQuestionRepository onlineExchangeQuestionRepository;
    @Autowired
    protected AlbumRepository albumRepository;
    @Autowired
    protected CourseRepository courseRepository;
    @Autowired
    protected CourseLaoRepository courseLaoRepository;
    @Autowired
    protected CourseItemRepository courseItemRepository;
    @Autowired
    protected CourseItemCommentRepository courseItemCommentRepository;
    @Autowired
    protected SponsorRepository sponsorRepository;
    @Autowired
    protected ConferenceRepository conferenceRepository;
    @Autowired
    protected ApiLogRepository apiLogRepository;
    @Autowired
    protected QuestionRepository questionRepository;
    @Autowired
    protected CourseTestRepository courseTestRepository;
    @Autowired
    protected LessonLaoRepository lessonLaoRepository;
    @Autowired
    protected LessonLaoCommentRepository lessonLaoCommentRepository;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected SubCommentRepository subCommentRepository;
    @Autowired
    protected DocumentRepository documentRepository;
    @Autowired
    protected DocumentCommentRepository documentCommentRepository;
    @Autowired
    protected RepresentRoomRepository representRoomRepository;
    @Autowired
    protected ProvinceRepository provinceRepository;
    @Autowired
    protected FileStorageService fileStorageService;
    @Autowired
    protected UserCourseRepository userCourseRepository;
    @Autowired
    protected UserConferenceRepository userConferenceRepository;

    //region khai bao Repository
    @Autowired
    protected UltilityRepository ultilityRepository;
    @Autowired
    protected EmailRepository emailRepository;
    @Autowired
    protected EmaiTokenRepository emaiTokenRepository;
    @Autowired
    protected SessionRepository sessionRepository;

    @Autowired
    protected UserAccessRepository userAccessRepository;

    @Autowired
    protected UserAccessCountRepository userAccessCountRepository;

    @Autowired
    protected PositionRepository positionRepository;

    @Autowired
    protected TestRepository testRepository;

    /**
     * Template response
     */
    private ResultEntity resultEntity;
    private Long userId;
    private Iterator<Map<String, Object>> queryResult;

    public BaseController() {

    }

    /**
     * paging dung trong tim kiem phan trang
     */

    protected void init() {
        init(null);
    }

    protected void init(Object object) {
        userId = null;
        log = LogUtils.getInstance();

        startTime = DateTimeUtils.getCurrentTimeInMilis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        resultEntity = new ResultEntity();

        String url = request.getMethod() + " " + request.getRequestURI();
        String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            url += "?" + queryString;
        }
        log.info(url);
        log.info("getRemoteAddr: " + request.getRemoteAddr());

        String token = request.getHeader("Authorization");
        if (!StrUtils.isNullOrWhiteSpace(token)) {
            log.info("Token " + token);
            userId = JWTokenUtils.getUserIdFromToken(token);
            log.info("userId " + userId);
        } else {
            log.info("khong co token Token ");
        }

        String strObj = "";
        if (object != null) {
            strObj = GsonUtils.toString(object);
            log.info(strObj);
            jsonData = new JSONObject(GsonUtils.toString(object));
        }
        saveLog(object);
    }

    private void saveLog(Object body) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String remoteAddr = request.getRemoteAddr();
        String userAgent = request.getHeader("user-agent");
        String cookie = request.getHeader("cookie");
        String forward = request.getHeader("X-Forwarded-For");

//        log.info(request.getHeaderNames());
        ApiLogEntity entity = new ApiLogEntity();
        entity.setMethod(method);
        entity.setUri(uri);
        entity.setQueryString(queryString);
        entity.setRemoteAddr(remoteAddr);
        entity.setUserAgent(userAgent);
        entity.setCookie(cookie);
        if (body != null)
            entity.setBody(GsonUtils.toString(body));
        entity.setForward(forward);

        new Thread(() -> {
            try {
                save(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Create response when exception
     */
    protected ResultEntity error(Exception ex) {
        log.error(ex);
        resultEntity.setMessage(ex.getMessage());
        if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            resultEntity.setCode(customException.getCode());
            resultEntity.setData(customException.getData());
        } else {
            resultEntity.setCode(500);
            resultEntity.setMessage("Internal Server Error " + ex.getMessage());
        }
        return resultEntity;
    }

    /**
     * Get Object with key in jsonData
     */
    public <U> U getObject(String key, Class<U> cls) {
        if (!jsonData.has(key)) return null;
        try {
            return GsonUtils.toObject(jsonData.get(key).toString(), cls);
        } catch (Exception ex) {
            return null;
        }
    }

    public JSONObject getJSONObject(String key) {
        if (!jsonData.has(key)) return null;
        try {
            return jsonData.getJSONObject(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public JSONArray getJSONArray(String key) {
        if (!jsonData.has(key)) return null;
        try {
            return jsonData.getJSONArray(key);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get param input as Integer
     */
    public Integer getInteger(String key) {
        if (!jsonData.has(key))
            return null;
        try {
            return jsonData.getInt(key);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get param input as Integer
     */
    public Integer getInteger(String key, Integer defaultValue) {
        if (!jsonData.has(key)) {
            return defaultValue;
        }
        try {
            return jsonData.getInt(key);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get param input as Long
     */
    public Long getLong(String key) {
        if (!jsonData.has(key))
            return null;
        try {
            return jsonData.getLong(key);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get param input as LocalDate
     */
    public LocalDate getDate(String key) {
        if (!jsonData.has(key))
            return null;
        try {
            String date = jsonData.getString(key);
            return LocalDate.parse(date, AppConst.DATE_FORMATTER);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get param input as LocalDate
     */
    public LocalDateTime getDateTime(String key) {
        if (!jsonData.has(key)) return null;
        try {
            String date = jsonData.getString(key);
            return LocalDateTime.parse(date, AppConst.DATE_TIME_FORMATTER);
        } catch (Exception ex) {
            return null;
        }
    }

    public Long getLong(String key, Long defaultValue) {
        if (!jsonData.has(key)) return defaultValue;
        try {
            return jsonData.getLong(key);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get param input as String, neu khong co properties nay thi tra ve null
     */
    public String getString(String key) {
        if (!jsonData.has(key)) return null;
        try {
            return jsonData.getString(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public String getString(String key, String defaultValue) {
        if (!jsonData.has("key")) return defaultValue;
        try {
            return jsonData.getString(key);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Log service running time and response json object
     *
     * @param entity Object data muon chuyen sang json
     * @return {"code":0,"message":"no message","data":{}}
     */
    protected ResponseEntity response(ResultEntity entity) {
        log.info("Time " + (DateTimeUtils.getCurrentTimeInMilis() - startTime));
        return new ResponseEntity(entity, HttpStatus.OK);
    }

    protected ResultEntity ok() throws Exception {
        return ok("status", 1);
    }

    protected ResultEntity ok(List<String> keys, List<Object> values) throws Exception {
        return ok(getResult(keys, values));
    }

    /**
     * Create standard response code=0
     */
    protected ResultEntity ok(Object data) {
        resultEntity.setData(data);
        return resultEntity;
    }

    protected <T> ResultEntity ok(Page<T> result) throws Exception {
        return ok(result.getTotalElements(), result.getContent());
    }

    protected <T> ResultEntity ok(Long total, Iterable<T> data) throws Exception {
        resultEntity.setData(getResult(total, data));
        return resultEntity;
    }

    protected <T> ResultEntity ok(Integer total, Iterable<T> data) throws Exception {
        return ok(Long.valueOf(total), data);
    }

    protected ResultEntity ok(String key, Object value) throws Exception {
        resultEntity.setData(getResult(key, value));
        return resultEntity;
    }

    /**
     * Create custom result from array keys and values
     */
    public Map<String, Object> getResult(List<String> keys, List<Object> values) throws Exception {
        if (keys.size() != values.size()) {
            throw new Exception("keys and values must be the same size");
        }
        Map<String, Object> mapResult = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            mapResult.put(keys.get(i), values.get(i));
        }
        return mapResult;
    }

    public <T> Map<String, Object> getResult(Long total, Iterable<T> objects) throws Exception {
        return getResult(Arrays.asList("total", "data"), Arrays.asList(total, objects));
    }

    /**
     * Create result with one key and value
     */
    public Map<String, Object> getResult(String key, Object value) throws Exception {
        return getResult(Collections.singletonList(key), Collections.singletonList(value));
    }

    protected CustomException getException(String message, Object... objects) {
        return new CustomException(String.format(message, objects));
    }

    /**
     * Tra ve loi voi cac thong so
     *
     * @param code    ma loi
     * @param message Noi dung loi
     */
    protected CustomException getException(int code, String message, Object... objects) {
        return new CustomException(code, String.format(message, objects));
    }

    /**
     * sort by id giam dan
     *
     * @return
     */
    protected Sort getSortDESC() {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        return Sort.by(order);
    }

    /**
     * Sort by Id tang dan
     *
     * @return
     */
    protected Sort getSortASC() {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        return Sort.by(order);
    }

    private <T> T getEntityById(CrudRepository repository, Long id) {
        if (id == null || Objects.equals(0L, id)) return null;

        Optional entityOpt = repository.findById(id);
        if (!entityOpt.isPresent()) {
            return null;
        }
        return (T) entityOpt.get();
    }

    protected Pageable getDefaultPage(int page, int size) {
        return getPaging(page, size, getSortDESC());
    }

    protected Pageable getPaging(int page, int size, Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }

    protected Pageable getPaging(int page, int size) {
        return PageRequest.of(page - 1, size);
    }

    /**
     * format du lieu
     *
     * @param format
     * @param args
     * @return
     */
    protected String format(String format, Object... args) {
        return String.format(format, args);
    }

    /**
     * Tao HisObject tu string data
     *
     * @param data du lieu dau vao
     * @return HisObject
     */
    protected HisObject getHisObject(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            HisObject hisObject = new HisObject();
            hisObject.setCode(jsonObject.getInt("code"));
            if (hisObject.isSuccess()) {
                hisObject.setData(jsonObject.getJSONObject("data"));
            } else {
                hisObject.setComment(jsonObject.getString("comment"));
                Object object = jsonObject.get("data");
                if (object instanceof JSONObject) {
                    hisObject.setData(jsonObject.getJSONObject("data"));
                }
            }
            return hisObject;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * Tao HisArray tu string
     *
     * @param data String Object
     * @return HisArray
     */
    protected HisArray getHisArray(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            HisArray hisArray = new HisArray();
            hisArray.setCode(jsonObject.getInt("code"));
            if (hisArray.isSuccess()) {
                hisArray.setData(jsonObject.getJSONArray("data"));
            } else {
                hisArray.setComment(jsonObject.getString("comment"));
            }
            return hisArray;
        } catch (Exception ex) {
            return null;
        }
    }

    //endregion

    protected void save(Object o) {
        // save user
        if (o instanceof UserEntity) {
            userRepository.save((UserEntity) o);
        } else if (o instanceof MenuEntity) {
            menuRepository.save((MenuEntity) o);
        } else if (o instanceof NewsEntity) {
            newsRepository.save((NewsEntity) o);
        } else if (o instanceof DeviceEntity) {
            deviceRepository.save((DeviceEntity) o);
        } else if (o instanceof PageEntity) {
            pageRepository.save((PageEntity) o);
        } else if (o instanceof ContributeEntity) {
            contributeRepository.save((ContributeEntity) o);
        } else if (o instanceof DepartmentEntity) {
            departmentRepository.save((DepartmentEntity) o);
        } else if (o instanceof TagEntity) {
            tagRepository.save((TagEntity) o);
        } else if (o instanceof SlideItemEntity) {
            slideItemRepository.save((SlideItemEntity) o);
        } else if (o instanceof SlideEntity) {
            slideRepository.save((SlideEntity) o);
        } else if (o instanceof SlidePlaceEntity) {
            slidePlaceRepository.save((SlidePlaceEntity) o);
        } else if (o instanceof WeblinkEntity) {
            webLinkRepository.save((WeblinkEntity) o);
        } else if (o instanceof VideoEntity) {
            videoRepository.save((VideoEntity) o);
        } else if (o instanceof HospitalUtilityEntity) {
            hospitalUtilityRepository.save((HospitalUtilityEntity) o);
        } else if (o instanceof FunctionBlockEntity) {
            functionBlockRepository.save((FunctionBlockEntity) o);
        } else if (o instanceof HighLightImageEntity) {
            highLightImageRepository.save((HighLightImageEntity) o);
        } else if (o instanceof KeyValueEntity) {
            keyValueRepository.save((KeyValueEntity) o);
        } else if (o instanceof OnlineExchangeEntity) {
            onlineExchangeRepository.save((OnlineExchangeEntity) o);
        } else if (o instanceof OnlineExchangeQuestionEntity) {
            onlineExchangeQuestionRepository.save((OnlineExchangeQuestionEntity) o);
        } else if (o instanceof AlbumEntity) {
            albumRepository.save((AlbumEntity) o);
        } else if (o instanceof CourseEntity) {
            courseRepository.save((CourseEntity) o);
        } else if (o instanceof CourseItemEntity) {
            courseItemRepository.save((CourseItemEntity) o);
        } else if (o instanceof CourseItemCommentEntity) {
            courseItemCommentRepository.save((CourseItemCommentEntity) o);
        } else if (o instanceof SponsorEntity) {
            sponsorRepository.save((SponsorEntity) o);
        } else if (o instanceof ConferenceEntity) {
            conferenceRepository.save((ConferenceEntity) o);
        } else if (o instanceof ApiLogEntity) {
            apiLogRepository.save((ApiLogEntity) o);
        } else if (o instanceof QuestionEntity) {
            questionRepository.save((QuestionEntity) o);
        } else if (o instanceof CourseTestEntity) {
            courseTestRepository.save((CourseTestEntity) o);
        } else if (o instanceof LessonLaoEntity) {
            lessonLaoRepository.save((LessonLaoEntity) o);
        } else if (o instanceof PostEntity) {
            postRepository.save((PostEntity) o);
        } else if (o instanceof CommentEntity) {
            commentRepository.save((CommentEntity) o);
        } else if (o instanceof SubCommentEntity) {
            subCommentRepository.save((SubCommentEntity) o);
        } else if (o instanceof TokenPasswordEntity) {
            tokenPasswordRepository.save((TokenPasswordEntity) o);
        } else if (o instanceof DocumentEntity) {
            documentRepository.save((DocumentEntity) o);
        } else if (o instanceof DocumentCommentEntity) {
            documentCommentRepository.save((DocumentCommentEntity) o);
        } else if (o instanceof RepresentRoomEntity) {
            representRoomRepository.save((RepresentRoomEntity) o);
        } else if (o instanceof ProvinceEntity) {
            provinceRepository.save((ProvinceEntity) o);
        } else if (o instanceof CourseLaoEntity) {
            courseLaoRepository.save((CourseLaoEntity) o);
        } else if (o instanceof UserConferenceEntity) {
            userConferenceRepository.save((UserConferenceEntity) o);
        } else if (o instanceof UserCourseEntity) {
            userCourseRepository.save((UserCourseEntity) o);
        } else if (o instanceof EmailEntity) {
            emailRepository.save((EmailEntity) o);
        } else if (o instanceof EmailTokenEntity) {
            emaiTokenRepository.save((EmailTokenEntity) o);
        } else if (o instanceof SessionEntity) {
            sessionRepository.save((SessionEntity) o);
        } else if (o instanceof TestEntity) {
            testRepository.save((TestEntity) o);
        } else if (o instanceof LessonLaoCommentEntity) {
            lessonLaoCommentRepository.save((LessonLaoCommentEntity) o);
        } else if (o instanceof UserAccessEntity) {
            userAccessRepository.save((UserAccessEntity) o);
        } else if (o instanceof UserAccessCountEntity) {
            userAccessCountRepository.save((UserAccessCountEntity) o);
        } else if (o instanceof PositionEntity) {
            positionRepository.save((PositionEntity) o);
        }
    }

    /**
     * Run a runnable Object in other thread
     *
     * @param runnable
     */
    void runInThread(Runnable runnable) {
        try {
            Thread thread = new Thread(runnable::run);
            thread.start();
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }

    }

    //region Get Entity by id
    protected UserEntity getUser(Long id) {
        return getEntityById(userRepository, id);
    }

    protected MenuEntity getMenu(Long id) {
        return getEntityById(menuRepository, id);
    }

    protected PageEntity getPage(Long id) {
        return getEntityById(pageRepository, id);
    }

    protected NewsEntity getNews(Long id) {
        return getEntityById(newsRepository, id);
    }

    protected ContributeEntity getContribute(Long id) {
        return getEntityById(contributeRepository, id);
    }

    protected TagEntity getTag(Long id) {
        return getEntityById(tagRepository, id);
    }

    protected SlideItemEntity getSlideItem(Long id) {
        return getEntityById(slideItemRepository, id);
    }

    protected SlideEntity getSlide(Long id) {
        return getEntityById(slideRepository, id);
    }

    protected SlidePlaceEntity getSlidePlace(Long id) {
        return getEntityById(slidePlaceRepository, id);
    }

    protected WeblinkEntity getWebLink(Long id) {
        return getEntityById(webLinkRepository, id);
    }

    protected VideoEntity getVideo(Long id) {
        return getEntityById(videoRepository, id);
    }

    protected HospitalUtilityEntity getHospitalUtility(Long id) {
        return getEntityById(hospitalUtilityRepository, id);
    }

    protected FunctionBlockEntity getFunctionBlock(Long id) {
        return getEntityById(functionBlockRepository, id);
    }

    protected HighLightImageEntity getHighLightImage(Long id) {
        return getEntityById(highLightImageRepository, id);
    }

    protected KeyValueEntity getKeyValue(Long id) {
        return getEntityById(keyValueRepository, id);
    }

    protected DepartmentEntity getDepartment(Long id) {
        return getEntityById(departmentRepository, id);
    }

    protected OnlineExchangeEntity getOnlineExchange(Long id) {
        return getEntityById(onlineExchangeRepository, id);
    }

    protected OnlineExchangeQuestionEntity getOnlineExchangeQuestion(Long id) {
        return getEntityById(onlineExchangeQuestionRepository, id);
    }

    protected AlbumEntity getAlbumById(Long id) {
        return getEntityById(albumRepository, id);
    }

    protected PageEntity getPageBy(Long id) {
        return getEntityById(pageRepository, id);
    }

    protected CourseEntity getCourse(Long id) {
        return getEntityById(courseRepository, id);
    }

    protected CourseItemEntity getCourseItem(Long id) {
        return getEntityById(courseItemRepository, id);
    }

    protected CourseItemCommentEntity getCourseItemComment(Long id) {
        return getEntityById(courseItemCommentRepository, id);
    }

    protected SponsorEntity getSponsor(Long id) {
        return getEntityById(sponsorRepository, id);
    }

    protected ConferenceEntity getConference(Long id) {
        return getEntityById(conferenceRepository, id);
    }

    protected CourseLaoEntity getCourseLao(Long id) {
        return getEntityById(courseLaoRepository, id);
    }

    protected QuestionEntity getQuestion(Long id) {
        return getEntityById(questionRepository, id);
    }

    protected CourseTestEntity getCourseTest(Long id) {
        return getEntityById(courseTestRepository, id);
    }

    protected LessonLaoEntity getLession(Long id) {
        return getEntityById(lessonLaoRepository, id);
    }

    protected LessonLaoCommentEntity getLessionLaoComment(Long id) {
        return getEntityById(lessonLaoCommentRepository, id);
    }

    protected PostEntity getPost(Long id) {
        return getEntityById(postRepository, id);
    }

    protected CommentEntity getComment(Long id) {
        return getEntityById(commentRepository, id);
    }

    protected SubCommentEntity getSubComment(Long id) {
        return getEntityById(subCommentRepository, id);
    }

    protected DocumentEntity getDocument(Long id) {
        return getEntityById(documentRepository, id);
    }

    protected DocumentCommentEntity getDocumentComment(Long id) {
        return getEntityById(documentCommentRepository, id);
    }

    protected RepresentRoomEntity getRepresentRoom(Long id) {
        return getEntityById(representRoomRepository, id);
    }

    protected ProvinceEntity getProvince(Long id) {
        return getEntityById(provinceRepository, id);
    }

    protected LessonLaoEntity getLessonLao(Long id) {
        return getEntityById(lessonLaoRepository, id);
    }

    protected LessonLaoCommentEntity getLessonLaoComment(long id) {
        return getEntityById(lessonLaoCommentRepository, id);
    }

    protected UserCourseEntity getUserCourse(Long id) {
        return getEntityById(userCourseRepository, id);
    }

    protected UserConferenceEntity getUserConference(Long id) {
        return getEntityById(userConferenceRepository, id);
    }

    protected EmailEntity getEmail(Long id) {
        return getEntityById(emailRepository, id);
    }

    //endregion

    protected EmailTokenEntity getEmailToken(Long id) {
        return getEntityById(emaiTokenRepository, id);
    }

    protected SessionEntity getSession(Long id) {
        return getEntityById(sessionRepository, id);
    }

    protected PositionEntity getPosition(Long id) {
        return getEntityById(positionRepository, id);
    }

    /**
     * Tao text hien thi tren adress trinh duyet (linkAlias)
     *
     * @param text text muon chuyen thanh linkAlias
     * @return linkAlias
     */
    public String getTextUrl(String text) {
        return StrUtils.createUrlFromString(text);
    }

    protected void saveLog(LogType logType, Long objId, String objName, Object note) {
        log.info("saveLog() called with: logType = [" + logType + "], objId = [" + objId + "], objName = [" + objName + "], note = [" + note + "]");
        UserEntity currentUser = getCurrentUser();
        log.info("after get current user is null");
        if (currentUser == null) {
            log.info("currentUser is null");
            return;
        }
        LogEntity logEntity = new LogEntity();

        logEntity.setAdmin(currentUser);
        logEntity.setType(logType.getValue());
        logEntity.setObjId(objId);

        logEntity.setObjName(objName);
        logEntity.setActionTime(LocalDateTime.now());
        if (note != null)
            logEntity.setNote(GsonUtils.toString(note));
        log.info("before save");
        save(logEntity);
        log.info("end  save " + GsonUtils.toString(logEntity));
    }

    public UserEntity getCurrentUser() {
        if (userId == null) return null;
        return getUser(userId);
    }

    /**
     * Tra ve vien private queryResult
     *
     * @return
     */
    public Iterator<Map<String, Object>> getQueryResult() {
        return queryResult;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    /**
     * update UserIds luu trong courselao
     *
     * @param courseLaoId
     */
    protected void updateUserIds(long courseLaoId) {
        CourseLaoEntity courseLao = getCourseLao(courseLaoId);
        List<UserCourseEntity> userCourses = userCourseRepository.getByCourseLao(courseLaoId);
        List<Long> userIds = new ArrayList<>();
        userCourses.forEach(entity -> {
            if (entity.getUser() != null) {
                userIds.add(entity.getUser().getId());
            }
        });
        courseLao.setUserIds(GsonUtils.toStringCompact(userIds));
        save(courseLao);

    }

    protected UserAccessCountEntity getUserAccessCount() {
        UserAccessCountEntity entity;
        Iterator<UserAccessCountEntity> entities = userAccessCountRepository.findAll().iterator();
        if (!entities.hasNext()) {
            entity = new UserAccessCountEntity();
            entity.setMonthlyCount(1L);
            entity.setOnlineCount(1);
            entity.setTotalCount(1);
            save(entity);
        } else {
            entity = entities.next();
        }
        return entity;
    }

}
