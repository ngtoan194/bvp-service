package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.PageEntity;
import com.isofh.service.model.UserEntity;
import com.isofh.service.utils.ArrayUtils;
import com.isofh.service.utils.GsonUtils;
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
@RequestMapping(path = "/page")
public class PageController extends BaseController {

    public PageController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<PageEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (PageEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(PageEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(PAGE, entity);
        return mapData;
    }

    @ApiOperation(value = "Create a page", notes = "{\"page\":{\"name\":\"Khoa khám bệnh\",\"link\":\"dfdggtgrg\",\"image\":\"gfgfgggrg\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            PageEntity entity = getObject(PAGE, PageEntity.class);
            entity.setLinkAlias(getTextUrl(entity.getName()));
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search page", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "type", required = false, defaultValue = DefaultConst.NUMBER) Integer type,
            @RequestParam(value = CommonField.WEB_TYPE, required = false, defaultValue = DefaultConst.NUMBER) Integer webType
    ) {
        try {
            init();
            Page<PageEntity> results = pageRepository.search(name, type, webType, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update page", notes = "{\"page\":{\"name\":\"Khoa khám bệnh\",\"url\":\"dfdggtgrg\",\"thumbnail\":\"gfgfgggrg\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            PageEntity data = getObject(PAGE, PageEntity.class);
            PageEntity entity = getPage(id);
            entity.setName(data.getName());
            entity.setNameE(data.getNameE());
            entity.setColor(data.getColor());
            entity.setColor2(data.getColor2());
            entity.setColor3(data.getColor3());
            entity.setAdminIds(data.getAdminIds());
            entity.setBannerImage(data.getBannerImage());
            entity.setCommonInfo(data.getCommonInfo());
            entity.setContent(data.getContent());
            entity.setDeputyDirectors(data.getDeputyDirectors());
            entity.setDevelopment(data.getDevelopment());
            entity.setDirectors(data.getDirectors());
            entity.setEmployees(data.getEmployees());
            entity.setFile(data.getFile());
            entity.setFunction(data.getFunction());
            entity.setImages(data.getImages());
            entity.setOldDeputyDirectors(data.getOldDeputyDirectors());
            entity.setOldDirectors(data.getOldDirectors());
            entity.setPhone(data.getPhone());
            entity.setPhoneExt(data.getPhoneExt());
            entity.setReward(data.getReward());
            entity.setTitle(data.getTitle());
            entity.setType(data.getType());
            entity.setWebType(data.getWebType());
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
            pageRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Get Detail", notes = "")
    @GetMapping(path = "/get-detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") long id) {
        try {
            init();
            PageEntity entity = getPage(id);
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
            PageEntity entity = pageRepository.findFirstByLinkAlias(alias);
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
            PageEntity entity = getObject(PAGE, PageEntity.class);
            String[] strAdminIds = getObject("adminIds", String[].class);
            List<Long> adminIds = new ArrayList<>();
            if (!ArrayUtils.isNullOrEmpty(strAdminIds)) {
                for (String strAdminId : strAdminIds) {
                    UserEntity admin = userRepository.findFirstByUid(strAdminId);
                    if (admin != null) {
                        adminIds.add(admin.getId());
                    }
                }
            }
            entity.setAdminIds(GsonUtils.toString(adminIds));
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

    @ApiOperation(value = "update-field", notes = "")
    @PostMapping(path = "/update-field")
    public ResponseEntity<?> updateField(@RequestBody Object object) {
        try {
            init(object);
            String uid = getString("uid");
            String oldDirectors = getString("oldDirectors");
            String oldDeputyDirectors = getString("oldDeputyDirectors");
            String directors = getString("directors");
            String deputyDirectors = getString("deputyDirectors");
            String commonInfo = getString("commonInfo");
            PageEntity page = pageRepository.findFirstByUid(uid);
            if (page != null) {
                page.setOldDirectors(oldDirectors);
                page.setOldDeputyDirectors(oldDeputyDirectors);
                page.setDirectors(directors);
                page.setDeputyDirectors(deputyDirectors);
                page.setCommonInfo(commonInfo);
            }
            save(page);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
