package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.ConferenceEntity;
import com.isofh.service.model.DocumentEntity;
import com.isofh.service.model.MenuEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/document")
public class DocumentController extends BaseController {

    public DocumentController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<DocumentEntity> documents) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (DocumentEntity document : documents) {
            mapResults.add(getMapData(document));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(DocumentEntity document) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(DOCUMENT, document);
        mapData.put(MENU, document.getMenu());
        return mapData;
    }

    /**
     * tim kiem tai lieu
     *
     * @return
     */
    @ApiOperation(value = "Search document", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "authorName", required = false, defaultValue = DefaultConst.STRING) String authorName,
            @RequestParam(value = "code", required = false, defaultValue = DefaultConst.STRING) String code,
            @RequestParam(value = "publishedDate", required = false, defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate publishedDate,
            @RequestParam(value = "effectiveDate", required = false, defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate effectiveDate,
            @RequestParam(value = "publishedOrg", required = false, defaultValue = DefaultConst.STRING) String publishedOrg,
            @RequestParam(value = "isHighLight", required = false, defaultValue = DefaultConst.NUMBER) Integer isHighLight,
            @RequestParam(value = "type", required = false, defaultValue = DefaultConst.NUMBER) Integer type,
            @RequestParam(value = "webType", required = false, defaultValue = DefaultConst.NUMBER) Integer webType,
            @RequestParam(value = "menuId", required = false, defaultValue = DefaultConst.NUMBER) Integer menuId,
            @RequestParam(value = "conferenceId", required = false, defaultValue = DefaultConst.NUMBER) Long conferenceId
    ) {
        try {
            init();
            Page<DocumentEntity> results = documentRepository.search(name,authorName , code, publishedDate, effectiveDate, publishedOrg, isHighLight, type, webType, menuId, conferenceId, getDefaultPage(page, size));
            List<Map<String, Object>> mapResults = new ArrayList<>();
            for (DocumentEntity document : results.getContent()) {
                Map<String, Object> mapResult = new HashMap<>();
                mapResults.add(mapResult);
                mapResult.put(DOCUMENT, document);
                if (document.getMenu().getLevel() == 1) {
                    mapResult.put(MENU, document.getMenu());
                } else if (document.getMenu().getLevel() == 2) {
                    mapResult.put(MENU, document.getMenu());
                    mapResult.put("menuParent", document.getMenu().getParent());
                }
                if (document.getConference() != null) {
                    mapResult.put("conferenceId", document.getConference().getId());
                }
            }
            return response(ok(results.getTotalElements(), mapResults));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tao moi tai lieu
     *
     * @param object {"document": {"type": 1,"code": "1234","name": "document","publishedOrg":"bvp","file": "file.png","image":"image.png","publishedDate": "2018-10-20","effectiveDate": "2018-10-20","webType":1},"menuId": "1"}
     * @return
     */
    @ApiOperation(value = "Create a document", notes = "{\"document\": {\"type\": 1,\"code\": \"1234\",\"name\": \"document\",\"publishedOrg\":\"bvp\",\"file\": \"file.png\",\"image\":\"image.png\",\"publishedDate\": \"2018-10-20\",\"effectiveDate\": \"2018-10-20\",\"webType\":1},\"menuId\": \"1\"}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            DocumentEntity document = getObject("document", DocumentEntity.class);
            document.setLinkAlias(getTextUrl(document.getName()));
            Long menuId = getLong("menuId");
            Long conferenceId = getLong("conferenceId");
            MenuEntity menu = getMenu(menuId);
            ConferenceEntity conference = getConference(conferenceId);
            if (menu != null) {
                document.setMenu(menu);
            }

            if (conference != null) {
                document.setConference(conference);
            }
            save(document);
            return response(ok(getMapData(document)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Cap nhat giao luu truc tuyen
     *
     * @param object {"document": {"type": 1,"code": "1234","name": "document","publishedOrg":"bvp","file": "file.png","image":"image.png","publishedDate": "2018-10-20","effectiveDate": "2018-10-20"},"menuId": "1"}
     * @return
     */
    @ApiOperation(value = "Update online exchange", notes = "{\"document\": {\"type\": 1,\"code\": \"1234\",\"name\": \"document\",\"publishedOrg\":\"bvp\",\"file\": \"file.png\",\"image\":\"image.png\",\"publishedDate\": \"2018-10-20\",\"effectiveDate\": \"2018-10-20\"},\"menuId\": \"1\"}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            DocumentEntity data = getObject("document", DocumentEntity.class);
            DocumentEntity entity = getDocument(id);
            Long conferenceId = getLong("conferenceId");
            if(conferenceId!=null) {
                ConferenceEntity conferenceEntity = getConference(conferenceId);
                entity.setConference(conferenceEntity);
            }

            entity.setName(data.getName());
            entity.setCode(data.getCode());
            entity.setLinkAlias(getTextUrl(data.getName()));
            entity.setPublishedDate(data.getPublishedDate());
            entity.setEffectiveDate(data.getEffectiveDate());
            entity.setPublishedOrg(data.getPublishedOrg());
            entity.setFile(data.getFile());
            entity.setFilePreview(data.getFilePreview());
            entity.setType(data.getType());
            entity.setIsHighLight(data.getIsHighLight());
            entity.setImage(data.getImage());
            entity.setAuthorName(data.getAuthorName());
            entity.setPublishedYear(data.getPublishedYear());
            entity.setDocumentNumber(data.getDocumentNumber());
            entity.setFreeForMember(data.getFreeForMember());
            entity.setLinkYoutube(data.getLinkYoutube());
            entity.setPrice(data.getPrice());


            Long menuId = getLong("menuId");
            MenuEntity menu = getMenu(menuId);
            if (menu != null) {
                entity.setMenu(menu);
            }

            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa tai lieu
     *
     * @return
     */
    @ApiOperation(value = "Delete document", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            documentRepository.deleteById(id);
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
            DocumentEntity entity = getDocument(id);
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
            DocumentEntity entity = documentRepository.findFirstByLinkAlias(alias);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tim kiem thong tin cua co so y te, sap xep theo review giam dan
     *
     * @return
     */
    @ApiOperation(value = "Search disease", notes = "")
    @GetMapping(path = "/search-by-query")
    public ResponseEntity<?> searchByQuery(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "query", required = false, defaultValue = DefaultConst.STRING) String query
    ) {
        try {
            init();
            Page<DocumentEntity> result = documentRepository.searchByQuery(query, getPaging(page, size));
            return response(ok(result.getTotalElements(), getMapDatas(result)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update high light document", notes = "{\"isHighlight\":1}")
    @PutMapping(path = "/set-high-light/{id}")
    public ResponseEntity<?> approval(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer isHighlight = getInteger("isHighlight");
            DocumentEntity entity = getDocument(id);
            entity.setIsHighLight(isHighlight);
            save(entity);
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
            DocumentEntity entity = getObject("document", DocumentEntity.class);
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

    @ApiOperation(value = "update-menu", notes = "")
    @PostMapping(path = "/update-menu")
    public ResponseEntity<?> updateAuthor(@RequestBody Object object) {
        try {
            init(object);
            String documentUid = getString("documentUid");
            String menuUid = getString("menuUid");
            DocumentEntity document = documentRepository.findFirstByUid(documentUid);
            MenuEntity menu = menuRepository.findFirstByUid(menuUid);
            if (document == null || menu == null) {
                return response(error(new Exception("document null or menu null")));
            }
            document.setMenu(menu);
            save(menu);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-type", notes = "")
    @PostMapping(path = "/update-type")
    public ResponseEntity<?> updateType(@RequestBody Object object) {
        try {
            init(object);
            String documentUid = getString("documentUid");
            Integer type = getInteger("type");
            DocumentEntity document = documentRepository.findFirstByUid(documentUid);
            document.setType(type);
            save(document);
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
            String documentUid = getString("documentUid");
            String conferenceUid = getString("conferenceUid");
            ConferenceEntity conference = conferenceRepository.findFirstByUid(conferenceUid);
            DocumentEntity document = documentRepository.findFirstByUid(documentUid);
            document.setConference(conference);
            save(document);
            return response(ok());
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
            String file = getString("file");
            String image = getString("image");
            DocumentEntity entity = documentRepository.findFirstByUid(uid);
            if (entity != null) {
                entity.setFile(file);
                entity.setImage(image);
            }
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update count dowload", notes = "{\"downloadCount\":1}")
    @PutMapping(path = "/up-dowload-count/{id}")
    public ResponseEntity<?> upDownload(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer downloadCount = getInteger("downloadCount");
            DocumentEntity entity = getDocument(id);
            entity.setDownloadCount(entity.getDownloadCount()+1);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update count view", notes = "{\"viewCount\":1}")
    @PutMapping(path = "/up-view-count/{id}")
    public ResponseEntity<?> upView(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer viewCount = getInteger("viewCount");
            DocumentEntity entity = getDocument(id);
            entity.setViewCount(entity.getViewCount()+1);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
