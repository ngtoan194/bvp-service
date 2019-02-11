package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.OnlineExchangeEntity;
import com.isofh.service.model.UserEntity;
import com.isofh.service.utils.ArrayUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/online-exchange")
public class OnlineExchangeController extends BaseController {

    public OnlineExchangeController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<OnlineExchangeEntity> onlineExchanges) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (OnlineExchangeEntity onlineExchange : onlineExchanges) {
            mapResults.add(getMapData(onlineExchange));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(OnlineExchangeEntity onlineExchange) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(ONLINE_EXCHANGE, onlineExchange);
        mapData.put(DOCTORS, onlineExchange.getDoctors());
        return mapData;
    }

    /**
     * tim kiem giao luu truc tuyen
     *
     * @return
     */
    @ApiOperation(value = "Search online exchange", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "topic", required = false, defaultValue = DefaultConst.STRING) String topic,
            @RequestParam(value = "active", required = false, defaultValue = DefaultConst.NUMBER) Integer active,
            @RequestParam(value = "startTime", required = false, defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate startTime,
            @RequestParam(value = "endTime", required = false, defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate endTime
    ) {
        try {
            init();
            Page<OnlineExchangeEntity> results = onlineExchangeRepository.search(topic, active, startTime, endTime, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tao moi giao luu truc tuyen
     *
     * @param object {"onlineExchange": {"topic": "topic", "startTime": "2018-10-20 07:00:00", "endTime": "2018-10-20 20:00:00", "location": "Ha Noi"}, "doctorIds": [9,10,14]}
     * @return
     */
    @ApiOperation(value = "Create a online exchange", notes = "{\"onlineExchange\": {\"topic\": \"topic\", \"startTime\": \"2018-10-20 07:00:00\", \"endTime\": \"2018-10-20 20:00:00\", \"location\": \"Ha Noi\"}, \"doctorIds\": [9,10,14]}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            inactiveCurrent();
            OnlineExchangeEntity onlineExchange = getObject("onlineExchange", OnlineExchangeEntity.class);
            onlineExchange.setActive(1);
            onlineExchange.setLinkAlias(getTextUrl(onlineExchange.getTopic()));
            Long[] doctorIds = getObject("doctorIds", Long[].class);
            if(!ArrayUtils.isNullOrEmpty(doctorIds)) {
                for (Long doctorId : doctorIds) {
                    UserEntity doctorEntity = getUser(doctorId);
                    if (doctorEntity != null) {
                        onlineExchange.getDoctors().add(doctorEntity);
                    }
                }
            }
            save(onlineExchange);
            return response(ok(getMapData(onlineExchange)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat giao luu truc tuyen
     *
     * @param object {"onlineExchange": {"topic": "topic", "active": 1, "startTime": "2018-20-10", "endTime": "2018-20-10", "location": "Ha Noi"}, "doctorIds": [1,2,3]}
     * @return
     */
    @ApiOperation(value = "Update online exchange", notes = "{\"onlineExchange\": {\"topic\": \"topic\", \"active\": 1, \"startTime\": \"2018-20-10\", \"endTime\": \"2018-20-10\", \"location\": \"Ha Noi\"}, \"doctorIds\": [1,2,3]}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            OnlineExchangeEntity data = getObject("onlineExchange", OnlineExchangeEntity.class);
            OnlineExchangeEntity entity = getOnlineExchange(id);

            entity.setTopic(data.getTopic());
            entity.setActive(data.getActive());
            entity.setStartTime(data.getStartTime());
            entity.setEndTime(data.getEndTime());
            entity.setLocation(data.getLocation());
            entity.setLinkAlias(getTextUrl(data.getTopic()));

            entity.getDoctors().clear();
            Long[] doctorIds = getObject("doctorIds", Long[].class);
            if(!ArrayUtils.isNullOrEmpty(doctorIds)) {
                for (Long doctorId : doctorIds) {
                    UserEntity doctor = getUser(doctorId);
                    if (doctor != null) {
                        entity.getDoctors().add(doctor);
                    }
                }
            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa giao luu truc tuyen
     *
     * @return
     */
    @ApiOperation(value = "Delete online exchange", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            OnlineExchangeEntity entity = getOnlineExchange(id);
            if (entity.getOnlineExchangeQuestions().size() > 0) {
                throw getException(2, "khong the xoa khi da co question!");
            }
            onlineExchangeRepository.deleteById(id);
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
            OnlineExchangeEntity entity = getOnlineExchange(id);
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
            OnlineExchangeEntity entity = onlineExchangeRepository.findFirstByLinkAlias(alias);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat trang thai Active
     *
     * @param object {"active":1}
     * @return
     */
    @ApiOperation(value = "Change status active", notes = "{\"active\":1}")
    @PutMapping(path = "/active/{id}")
    public ResponseEntity<?> active(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            inactiveCurrent();
            Integer active = getInteger("active");
            OnlineExchangeEntity entity = getOnlineExchange(id);
            entity.setActive(active);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    private void inactiveCurrent() {
        List<OnlineExchangeEntity> entities = onlineExchangeRepository.checkActive();
        if (entities != null) {
            for (OnlineExchangeEntity entity: entities
            ) {
                entity.setActive(0);
                save(entity);
            }
        }
    }
}
