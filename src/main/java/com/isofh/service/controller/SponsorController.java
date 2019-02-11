package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.ConferenceEntity;
import com.isofh.service.model.SponsorEntity;
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
@RequestMapping(path = "/sponsor")
public class SponsorController extends BaseController {

    public SponsorController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<SponsorEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (SponsorEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(SponsorEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(SPONSOR, entity);
        mapData.put(CONFERENCE, entity.getConference());
        return mapData;
    }

    @ApiOperation(value = "Create a sponsor", notes = "{\"sponsor\":{\"logo\":\"Khoa khám bệnh\",\"description\":\"dfdggtgrg\",\"numSponsor\":9},\"conferenceId\":1}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            SponsorEntity entity = getObject(SPONSOR, SponsorEntity.class);
            Long conference = getLong("conferenceId");
            ConferenceEntity conferenceEntity = getConference(conference);
            entity.setConference(conferenceEntity);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a sponsor", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "conferenceId", required = false, defaultValue = DefaultConst.NUMBER) Integer conferenceId
    ) {
        try {
            init();
            Page<SponsorEntity> results = sponsorRepository.search(name, conferenceId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update sponsor", notes = "{\"sponsor\":{\"logo\":\"Khoa khám bệnh\",\"description\":\"dfdggtgrg\",\"numSponsor\":9},\"conferenceIds\":[1,2]}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            SponsorEntity data = getObject(SPONSOR, SponsorEntity.class);
            SponsorEntity entity = getSponsor(id);
            entity.setName(data.getName());
            entity.setDescription(data.getDescription());
            entity.setLogo(data.getLogo());
            entity.setNumSponsor(data.getNumSponsor());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete sponsor", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            sponsorRepository.deleteById(id);
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
            SponsorEntity entity = getSponsor(id);
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
            SponsorEntity entity = getObject(SPONSOR, SponsorEntity.class);
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

    @ApiOperation(value = "update-conference", notes = "")
    @PostMapping(path = "/update-conference")
    public ResponseEntity<?> updateConference(@RequestBody Object object) {
        try {
            init(object);
            String conferenceUid = getString("conferenceUid");
            String sponsorUid = getString("sponsorUid");
            ConferenceEntity conference = conferenceRepository.findFirstByUid(conferenceUid);
            SponsorEntity sponsor = sponsorRepository.findFirstByUid(sponsorUid);
            if (conference == null || sponsor == null) {
                return response(error(new Exception("post null or comment null")));
            }
            sponsor.setConference(conference);
            save(sponsor);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
