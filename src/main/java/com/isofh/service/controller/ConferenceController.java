package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.ConferenceEntity;
import com.isofh.service.model.CourseEntity;
import com.isofh.service.model.CourseLaoEntity;
import com.isofh.service.utils.ArrayUtils;
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
@RequestMapping(path = "/conference")
public class ConferenceController extends BaseController {

    public ConferenceController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<ConferenceEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (ConferenceEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(ConferenceEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(CONFERENCE, entity);
        return mapData;
    }

    @ApiOperation(value = "Create a conference", notes = "{\"conference\":{\"fee\":\"Khoa khám bệnh\",\"footer\":\"dfdggtgrg\",\"linkAlias\":\"gfgfgggrg\",\"topics\":\"dfdfd\",\"sponsor\":\"ffdf\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            ConferenceEntity entity = getObject(CONFERENCE, ConferenceEntity.class);
            entity.setLinkAlias(getTextUrl(entity.getName()));
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a Conference", notes = "")
    @GetMapping(path = "/search")


    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "startDate", defaultValue = DefaultConst.DATE_TIME) @DateTimeFormat(pattern = AppConst.DATE_TIME_FORMAT) LocalDateTime startDate,
            @RequestParam(value = "endDate", defaultValue = DefaultConst.DATE_TIME) @DateTimeFormat(pattern = AppConst.DATE_TIME_FORMAT) LocalDateTime endDate
    ) {
        try {
            init();
            Page<ConferenceEntity> results = conferenceRepository.search(name, startDate, endDate, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update Conference", notes = "{\"conference\":{\"fee\":\"Khoa khám bệnh\",\"footer\":\"dfdggtgrg\",\"linkAlias\":\"gfgfgggrg\",\"topics\":\"dfdfd\",\"sponsor\":\"ffdf\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            ConferenceEntity data = getObject(CONFERENCE, ConferenceEntity.class);
            ConferenceEntity entity = getConference(id);
            entity.setName(data.getName());
            entity.setAdminIds(data.getAdminIds());
            entity.setAdsImages(data.getAdsImages());
            entity.setBanners(data.getBanners());
            entity.setEndDate(data.getEndDate());
            entity.setFee(data.getFee());
            entity.setFooter(data.getFooter());
            entity.setGuideRegister(data.getGuideRegister());
            entity.setGuideReport(data.getGuideReport());
            entity.setHalls(data.getHalls());
            entity.setLogo(data.getLogo());
            entity.setPageCommonInfo(data.getPageCommonInfo());
            entity.setPageConferenceLocation(data.getPageConferenceLocation());
            entity.setPageConferenceSponsor(data.getPageConferenceSponsor());
            entity.setPageExhibitionPoster(data.getPageExhibitionPoster());
            entity.setPageHotel(data.getPageHotel());
            entity.setPageIntroduction(data.getPageIntroduction());
            entity.setPageRelatedConference(data.getPageRelatedConference());
            entity.setPageScienceCouncil(data.getPageScienceCouncil());
            entity.setPageTourism(data.getPageTourism());
            entity.setPageWelcome(data.getPageWelcome());
            entity.setPrintName(data.getPrintName());
            entity.setRegisterFee(data.getRegisterFee());
            entity.setSponsor(data.getSponsor());
            entity.setStartDate(data.getStartDate());
            entity.setTopics(data.getTopics());

            entity.getCourseLaos().clear();
            Long[] courseLaoIds = getObject("courseLaoIds", Long[].class);
            if (!ArrayUtils.isNullOrEmpty(courseLaoIds)) {
                for (Long courseLaoId : courseLaoIds) {
                    CourseLaoEntity conference = getCourseLao(courseLaoId);
                    if (conference != null) {
                        entity.getCourseLaos().add(conference);
                    }
                }
            }

            entity.getCourses().clear();
            Long[] courseIds = getObject("courseIds", Long[].class);
            if (!ArrayUtils.isNullOrEmpty(courseIds)) {
                for (Long courseId : courseIds) {
                    CourseEntity conference = getCourse(courseId);
                    if (conference != null) {
                        entity.getCourses().add(conference);
                    }
                }
            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete conference", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            conferenceRepository.deleteById(id);
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
            ConferenceEntity entity = getConference(id);
            Map<String, Object> mapData = getMapData(entity);
            mapData.put("adminIds", entity.getAdminIds());
            return response(ok(mapData));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "get-by-alias", notes = "")
    @GetMapping(path = "/get-by-alias/{alias}")
    public ResponseEntity<?> getDetail(@PathVariable("alias") String alias) {
        try {
            init();
            ConferenceEntity entity = conferenceRepository.findFirstByLinkAlias(alias);
            Map<String, Object> mapData = getMapData(entity);
            mapData.put("adminIds", entity.getAdminIds());
            return response(ok(mapData));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "updatePageGuide", notes = "{\"type\":1, \"index\":2, \"value\":\"url text\"}")
    @PutMapping(path = "/update-page-guide/{conferenceId}")
    public ResponseEntity<?> updatePageGuide(
            @PathVariable("conferenceId") long conferenceId,
            @RequestBody Object object) {
        try {
            init(object);
            ConferenceEntity conferenceEntity = getConference(conferenceId);
            Integer type = getInteger("type");
            Integer index = getInteger("index");
            String value = getString("value");
            if (type == 1) {
                switch (index) {
                    case 1:
                        conferenceEntity.setPageIntroduction(value);
                        break;
                    case 2:
                        conferenceEntity.setPageCommonInfo(value);
                        break;
                    case 3:
                        conferenceEntity.setPageScienceCouncil(value);
                        break;
                    case 4:
                        conferenceEntity.setPageWelcome(value);
                        break;
                    case 5:
                        conferenceEntity.setPageConferenceLocation(value);
                        break;
                    case 6:
                        conferenceEntity.setPageHotel(value);
                        break;
                    case 7:
                        conferenceEntity.setPageTourism(value);
                        break;
                    case 8:
                        conferenceEntity.setPageExhibitionPoster(value);
                        break;
                    case 9:
                        conferenceEntity.setPageConferenceSponsor(value);
                        break;
                    case 10:
                        conferenceEntity.setPageRelatedConference(value);
                        break;
                    default:
                        throw getException(2, "gia tri value khong phu hop");
                }
            } else if (type == 2) {
                switch (index) {
                    case 1:
                        conferenceEntity.setGuideReport(value);
                        break;
                    case 2:
                        conferenceEntity.setGuideRegister(value);
                        break;
                    case 3:
                        conferenceEntity.setRegisterFee(value);
                        break;
                    default:
                        throw getException(2, "gia tri value khong phu hop");
                }
            } else {
                throw getException(2, "gia tri type khong phu hop");
            }
            save(conferenceEntity);
            return response(ok(getMapData(conferenceEntity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update link alias", notes = "{\"linkAlias\":\"ggggg\"}")
    @PutMapping(path = "/update-link-alias/{id}")
    public ResponseEntity<?> updateLinkAlias(
            @RequestBody Object object,
            @PathVariable("id") long id) {
        try {
            init(object);
            ConferenceEntity entity = getConference(id);
            String link = getString("linkAlias");
            entity.setLinkAlias(link);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update print name", notes = "{\"printName\":\"ggggg\"}")
    @PutMapping(path = "/update-print-name/{id}")
    public ResponseEntity<?> updatePrintName(
            @RequestBody Object object,
            @PathVariable("id") long id) {
        try {
            init(object);
            ConferenceEntity entity = getConference(id);
            String printName = getString("printName");
            entity.setPrintName(printName);
            save(entity);
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
            ConferenceEntity entity = getObject(CONFERENCE, ConferenceEntity.class);
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
}
