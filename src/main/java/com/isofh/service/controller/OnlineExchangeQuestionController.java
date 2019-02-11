package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.OnlineExchangeEntity;
import com.isofh.service.model.OnlineExchangeQuestionEntity;
import com.isofh.service.model.UserEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/online-exchange-question")
public class OnlineExchangeQuestionController extends BaseController {

    public OnlineExchangeQuestionController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<OnlineExchangeQuestionEntity> onlineExchangeQuestions) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (OnlineExchangeQuestionEntity onlineExchangeQuestion : onlineExchangeQuestions) {
            mapResults.add(getMapData(onlineExchangeQuestion));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(OnlineExchangeQuestionEntity onlineExchangeQuestion) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(ONLINE_EXCHANGE_QUESTION, onlineExchangeQuestion);
        mapData.put(AUTHOR, onlineExchangeQuestion.getCreatedUser());
        mapData.put(DOCTOR, onlineExchangeQuestion.getDoctor());
        return mapData;
    }

    /**
     * tim kiem giao luu truc tuyen
     *
     * @return
     */
    @ApiOperation(value = "Search online exchange question", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "exchangeId", required = false, defaultValue = "-1") Long exchangeId,
            @RequestParam(value = "content", required = false, defaultValue = DefaultConst.STRING) String content,
            @RequestParam(value = "approval", required = false, defaultValue = DefaultConst.NUMBER) Integer approval,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "email", required = false, defaultValue = DefaultConst.STRING) String email,
            @RequestParam(value = "doctorId", required = false, defaultValue = DefaultConst.NUMBER) Integer doctorId
    ) {
        try {
            init();
            Page<OnlineExchangeQuestionEntity> results = onlineExchangeQuestionRepository.search(exchangeId, content, approval, name, email, doctorId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));

        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Dat cau hoi giao luu truc tuyen
     *
     * @param object {"onlineExchangeId":1, "onlineExchangeQuestion":{"content":"cau hoi","hideInfo":0}}
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "Create a online exchange question", notes = "{\"onlineExchangeId\":1, \"onlineExchangeQuestion\":{\"content\":\"cau hoi\",\"hideInfo\":0}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);

            OnlineExchangeQuestionEntity onlineExchangeQuestion = getObject("onlineExchangeQuestion", OnlineExchangeQuestionEntity.class);
            Long onlineExchangeId = getLong("onlineExchangeId");
            OnlineExchangeEntity onlineExchangeEntity = getOnlineExchange(onlineExchangeId);
            if (onlineExchangeEntity != null) {
                onlineExchangeQuestion.setOnlineExchange(onlineExchangeEntity);
            }
            onlineExchangeQuestion.setCreatedUser(getCurrentUser());

            save(onlineExchangeQuestion);
            return response(ok(getMapData(onlineExchangeQuestion)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Bac si tra loi cau hoi truc tuyen
     *
     * @param object {"onlineExchangeQuestion":{"answer":"cau tra loi 2","content":"cau hoi"},"doctorId":10}
     * @return
     */
    @ApiOperation(value = "Update online exchange question", notes = "{\"onlineExchangeQuestion\":{\"answer\":\"cau tra loi 2\",\"content\":\"cau hoi\"},\"doctorId\":10}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            OnlineExchangeQuestionEntity data = getObject("onlineExchangeQuestion", OnlineExchangeQuestionEntity.class);
            OnlineExchangeQuestionEntity entity = getOnlineExchangeQuestion(id);
            Long doctorId = getLong("doctorId");
            UserEntity doctor = getUser(doctorId);

            entity.setContent(data.getContent());
            entity.setAnswer(data.getAnswer());
            entity.setApproval(1);
            entity.setHideInfo(data.getHideInfo());
            entity.setDoctor(doctor);

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
    @ApiOperation(value = "Delete online exchange question", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            onlineExchangeQuestionRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Duyet or huy duyet cau hoi
     *
     * @param object {"approval":1}
     * @return
     */
    @ApiOperation(value = "Update approval online exchange question", notes = "{\"approval\":1}")
    @PutMapping(path = "/approval/{id}")
    public ResponseEntity<?> approval(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer active = getInteger("approval");
            OnlineExchangeQuestionEntity entity = getOnlineExchangeQuestion(id);
            entity.setApproval(active);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
