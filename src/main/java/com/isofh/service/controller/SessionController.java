package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.*;
import com.isofh.service.utils.ArrayUtils;
import com.isofh.service.utils.GsonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/session")
public class SessionController extends BaseController {

    public SessionController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<SessionEntity> sessions) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (SessionEntity session : sessions) {
            mapResults.add(getMapData(session));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(SessionEntity session) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(SESSION, session);
        return mapData;
    }

    /**
     * tim kiem phien
     *
     * @return
     */
    @ApiOperation(value = "Search session", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "type", required = false, defaultValue = DefaultConst.NUMBER) Integer type,
            @RequestParam(value = "startTime", required = false, defaultValue = DefaultConst.DATE_TIME) @DateTimeFormat(pattern = AppConst.DATE_TIME_FORMAT) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false, defaultValue = DefaultConst.DATE_TIME) @DateTimeFormat(pattern = AppConst.DATE_TIME_FORMAT) LocalDateTime endTime,
            @RequestParam(value = "location", required = false, defaultValue = DefaultConst.STRING) String location,
            @RequestParam(value = "topic", required = false, defaultValue = DefaultConst.STRING) String topic,
            @RequestParam(value = "conferenceId", required = false, defaultValue = DefaultConst.NUMBER) Long conferenceId
    ) {
        try {
            init();
            Page<SessionEntity> results = sessionRepository.search(name, type, startTime, endTime, location, topic, conferenceId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));

        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    /**
     * them moi session
     *
     * @param object {"session": {"name": "session name","startTime": "2018-11-15 07:30:00","endTime": "2018-11-30 19:00:00","type": "1","location": "abdasd","topic": "abdasd","owners": "abdasd","secretaries": "abdasd","reports": "abdasd"},"conferenceId": 3}
     * @return
     */
    @ApiOperation(value = "Create a session", notes = "{\"session\": {\"name\": \"session name\",\"startTime\": \"2018-11-15 07:30:00\",\"endTime\": \"2018-11-30 19:00:00\",\"type\": \"1\",\"location\": \"abdasd\",\"topic\": \"abdasd\",\"owners\": \"abdasd\",\"secretaries\": \"abdasd\",\"reports\": \"abdasd\"},\"conferenceId\": 3}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);

            SessionEntity session = getObject("session", SessionEntity.class);
            Long conferenceId = getLong("conferenceId");
            ConferenceEntity conference = getConference(conferenceId);
            if (conference != null) {
                session.setConference(conference);
            }
            save(session);
            return response(ok(getMapData(session)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Update session
     *
     * @param object {"session": {"name": "session name","startTime": "2018-11-15 07:30:00","endTime": "2018-11-30 19:00:00","type": "1","location": "abdasd","topic": "abdasd","owners": "abdasd","secretaries": "abdasd","reports": "abdasd"}}
     * @return
     */
    @ApiOperation(value = "Update session", notes = "{\"session\": {\"name\": \"session name\",\"startTime\": \"2018-11-15 07:30:00\",\"endTime\": \"2018-11-30 19:00:00\",\"type\": \"1\",\"location\": \"abdasd\",\"topic\": \"abdasd\",\"owners\": \"abdasd\",\"secretaries\": \"abdasd\",\"reports\": \"abdasd\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            SessionEntity data = getObject("session", SessionEntity.class);
            SessionEntity entity = getSession(id);
            entity.setName(data.getName());
            entity.setStartTime(data.getStartTime());
            entity.setEndTime(data.getEndTime());
            entity.setType(data.getType());
            entity.setLocation(data.getLocation());
            entity.setTopic(data.getTopic());
            entity.setOwners(data.getOwners());
            entity.setSecretaries(data.getSecretaries());
            entity.setReports(data.getReports());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa phien
     *
     * @return
     */
    @ApiOperation(value = "Delete phien", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            sessionRepository.deleteById(id);
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
            SessionEntity entity = getObject(SESSION, SessionEntity.class);
            LocalDateTime createdDate = entity.getCreatedDate();
            String[] owners = getObject("owners", String[].class);
            String[] secretaries = getObject("secretaries", String[].class);
            String[] reports = getObject("reports", String[].class);
            List<Long> ownerIds = new ArrayList<>();
            if (!ArrayUtils.isNullOrEmpty(owners)) {
                for (String uid : owners) {
                    UserEntity user = userRepository.findFirstByUid(uid);
                    if (user != null) {
                        ownerIds.add(user.getId());
                    }
                }
            }

            List<Long> secretaryIds = new ArrayList<>();
            if(!ArrayUtils.isNullOrEmpty(secretaries)) {
                for (String uid : secretaries) {
                    UserEntity user = userRepository.findFirstByUid(uid);
                    if (user != null) {
                        secretaryIds.add(user.getId());
                    }
                }
            }

            List<Long> reportIds = new ArrayList<>();
            if(!ArrayUtils.isNullOrEmpty(reports)) {
                for (String uid : reports) {
                    DocumentEntity user = documentRepository.findFirstByUid(uid);
                    if (user != null) {
                        reportIds.add(user.getId());
                    }
                }
            }
            entity.setOwners(GsonUtils.toStringCompact(ownerIds));
            entity.setSecretaries(GsonUtils.toStringCompact(secretaries));
            entity.setReports(GsonUtils.toStringCompact(reportIds));
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

    @ApiOperation(value = "update-conference", notes = "")
    @PostMapping(path = "/update-conference")
    public ResponseEntity<?> updateConference(@RequestBody Object object) {
        try {
            init(object);
            String sessionUid = getString("sessionUid");
            String conferenceUid = getString("conferenceUid");
            SessionEntity session = sessionRepository.findFirstByUid(sessionUid);
            ConferenceEntity conference = conferenceRepository.findFirstByUid(conferenceUid);
            if (session == null || conference == null) {
                return response(error(new Exception("post null or comment null")));
            }
            session.setConference(conference);
            save(session);
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
            SessionEntity entity = getSession(id);
            List<UserConferenceEntity> owners = new ArrayList<>();
            List<UserConferenceEntity> secretaries = new ArrayList<>();
            List<DocumentEntity> reports = new ArrayList<>();
            Long[] arrOwners = GsonUtils.toObject(entity.getOwners(), Long[].class);
            Long[] arrSecretaries = GsonUtils.toObject(entity.getSecretaries(), Long[].class);
            Long[] arrReports = GsonUtils.toObject(entity.getReports(), Long[].class);
            Long conferenceId = entity.getConference().getId();
            if (!ArrayUtils.isNullOrEmpty(arrOwners)) {
                for (Long userId : arrOwners) {
                    UserConferenceEntity userConference = getUserConference(userId);
                    if (userConference != null) {
                        owners.add(userConference);
                    }
                }
            }

            if (!ArrayUtils.isNullOrEmpty(arrSecretaries)) {
                for (Long userId : arrSecretaries) {
                    UserConferenceEntity userConference = getUserConference(userId);
                    if (userConference != null) {
                        secretaries.add(userConference);
                    }
                }
            }

            if (!ArrayUtils.isNullOrEmpty(arrReports)) {
                for (Long documentId : arrReports) {
                    DocumentEntity document = getDocument(documentId);
                    if (document != null) {
                        reports.add(document);
                    }
                }
            }
            Map<String, Object> mapResult = new HashMap<>();
            mapResult.put(SESSION, entity);
            mapResult.put(USER_CONFERENCE, entity.getConference().getUserConference());
            mapResult.put("owners", owners);
            mapResult.put("secretaries", secretaries);
            mapResult.put("reports", reports);
            return response(ok(mapResult));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
