package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.ContributeEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/contribute")
public class ContributeController extends BaseController {

    public ContributeController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<ContributeEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (ContributeEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(ContributeEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(CONTRIBUTE, entity);
        return mapData;
    }

    /**
     * Tao moi tin tuc
     *
     * @param object {"contribute":{"author":"Lê Đăng Tuấn","authorAvatar":"/images/abvcv.png","title":"tieu de","content":"noi dung"}}
     * @return
     */
    @ApiOperation(value = "Create a contribute", notes = "{\"contribute\":{\"author\":\"Lê Đăng Tuấn\",\"authorAvatar\":\"/images/abvcv.png\",\"title\":\"tieu de\",\"title\":\"content\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            ContributeEntity contribute = getObject(CONTRIBUTE, ContributeEntity.class);
            contribute.setLinkAlias(getTextUrl(contribute.getTitle()));
            save(contribute);
            return response(ok(getMapData(contribute)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a contribute", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "content", required = false, defaultValue = DefaultConst.STRING) String content,
            @RequestParam(value = "title", required = false, defaultValue = DefaultConst.STRING) String title,
            @RequestParam(value = "author", required = false, defaultValue = DefaultConst.STRING) String author,
            @RequestParam(value = "startDate", defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate startDate,
            @RequestParam(value = "endDate", defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate endDate
    ) {
        try {
            init();
            Page<ContributeEntity> results = contributeRepository.search(content, title, startDate, endDate, author, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update contribute", notes = "")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            ContributeEntity data = getObject(CONTRIBUTE, ContributeEntity.class);
            ContributeEntity entity = getContribute(id);
            entity.setTitle(data.getTitle());
            entity.setContent(data.getContent());
            entity.setAuthor(data.getAuthor());
            entity.setAuthorAvatar(data.getAuthorAvatar());

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
            contributeRepository.deleteById(id);
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
            ContributeEntity entity = getContribute(id);
            return response(ok(entity));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "get-by-alias news", notes = "")
    @GetMapping(path = "/get-by-alias/{alias}")
    public ResponseEntity<?> getDetail(@PathVariable("alias") String alias) {
        try {
            init();
            ContributeEntity entity = contributeRepository.findFirstByLinkAlias(alias);
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
            ContributeEntity entity = getObject(CONTRIBUTE, ContributeEntity.class);
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


