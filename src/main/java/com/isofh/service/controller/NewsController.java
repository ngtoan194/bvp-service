package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.enums.LogType;
import com.isofh.service.enums.WebType;
import com.isofh.service.model.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/news")
public class NewsController extends BaseController {

    public NewsController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<NewsEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (NewsEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(NewsEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("news", entity);
        mapData.put(MENU, entity.getMenu());
        mapData.put(PAGE, entity.getPage());
        return mapData;
    }

    /**
     * Tao moi tin tuc
     *
     * @param object {"news":{"title":"Lê Đăng Tuấn","content":"Đại học bach khoa hà nội","imagePreview":"http://localhost.png","contentPreview":"Đây là nội dung content preview edited","keyword":"tieu duong, dau bung"}, "menuId":"dsfasdf"}
     * @return
     */
    @ApiOperation(value = "Create a news", notes = "{\"news\":{\"title\":\"Lê Đăng Tuấn\",\"content\":\"Đại học bach khoa hà nội\",\"imagePreview\":\"http://localhost.png\",\"contentPreview\":\"Đây là nội dung content preview edited\",\"keyword\":\"tieu duong, dau bung\"}, \"menuId\":\3}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            NewsEntity newsEntity = getObject("news", NewsEntity.class);
            newsEntity.setLinkAlias(getTextUrl(newsEntity.getTitle()));

            if (Objects.equals(newsEntity.getBelongDepartment(), 1)) {
                Long pageId = getLong("pageId");
                PageEntity pageEntity = getPage(pageId);
                if (pageEntity != null) {
                    newsEntity.setPage(pageEntity);
                    saveLog(LogType.PAGE_CREATE_NEWS, pageEntity.getId(), pageEntity.getName(), "");
                }
            } else {
                Long menuId = getLong("menuId");
                if (menuId != null) {
                    MenuEntity menuEntity = getMenu(menuId);
                    if (menuEntity != null) {
                        newsEntity.setMenu(menuEntity);
                    }
                }
            }
            save(newsEntity);
            return response(ok(getMapData(newsEntity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a news", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "content", required = false, defaultValue = DefaultConst.STRING) String content,
            @RequestParam(value = "isHotNews", required = false, defaultValue = DefaultConst.NUMBER) Integer isHotNews,
            @RequestParam(value = "menuId", required = false, defaultValue = DefaultConst.NUMBER) Long menuId,
            @RequestParam(value = "webType", required = false, defaultValue = DefaultConst.NUMBER) Integer webType,
            @RequestParam(value = "fromHoiLao", required = false, defaultValue = DefaultConst.NUMBER) Integer fromHoiLao,
            @RequestParam(value = "charity", required = false, defaultValue = DefaultConst.NUMBER) Integer charity,
            @RequestParam(value = "highlightNoti", required = false, defaultValue = DefaultConst.NUMBER) Integer highlightNoti,
            @RequestParam(value = "belongDepartment", required = false, defaultValue = DefaultConst.NUMBER) Integer belongDepartment,
            @RequestParam(value = "pageId", required = false, defaultValue = DefaultConst.NUMBER) Long pageId,
            @RequestParam(value = "type", required = false, defaultValue = DefaultConst.NUMBER) Integer type,
            @RequestParam(value = "title", required = false, defaultValue = DefaultConst.STRING) String title,
            @RequestParam(value = "startDate", defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate startDate,
            @RequestParam(value = "endDate", defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate endDate
    ) {
        try {
            init();
            Page<NewsEntity> queryResults = newsRepository.search(content, isHotNews, menuId, webType, fromHoiLao, charity, highlightNoti, belongDepartment, pageId, type, title, startDate, endDate, getDefaultPage(page,size));
            return response(ok(queryResults.getTotalElements(), getMapDatas(queryResults.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update news", notes = "{\"news\":{\"title\":\"Lê Đăng Tuấn\",\"content\":\"Đại học bach khoa hà nội\",\"imagePreview\":\"http://localhost.png\",\"contentPreview\":\"Đây là nội dung content preview edited\",\"keyword\":\"tieu duong, dau bung\"}, \"menuId\":\3}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            NewsEntity data = getObject(NEWS, NewsEntity.class);
            NewsEntity entity = getNews(id);
            entity.setTitle(data.getTitle());
            entity.setContent(data.getContent());
            entity.setImagePreview(data.getImagePreview());
            entity.setContentPreview(data.getContentPreview());
            entity.setKeyword(data.getKeyword());
            entity.setIsHotNews(data.getIsHotNews());
            entity.setCharity(data.getCharity());
            entity.setHighlightNoti(data.getHighlightNoti());
            entity.setBelongDepartment(data.getBelongDepartment());


            if (Objects.equals(data.getBelongDepartment(), 1)) {
                Long pageId = getLong("pageId");
                PageEntity pageEntity = getPage(pageId);
                if (pageEntity != null) {
                    entity.setPage(pageEntity);
                    saveLog(LogType.PAGE_UPDATE_NEWS, pageEntity.getId(), pageEntity.getName(), "");
                }
            } else {
                Long menuId = getLong("menuId");
                MenuEntity menuEntity = getMenu(menuId);
                if (menuEntity != null) {
                    entity.setMenu(menuEntity);
                } else {

                }
            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete news", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            newsRepository.deleteById(id);
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
            NewsEntity entity = getNews(id);
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
            NewsEntity entity = newsRepository.findFirstByLinkAlias(alias);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "set-hot-news news", notes = "{\"isHotNews\":1}")
    @PutMapping(path = "/set-hot-news/{id}")
    public ResponseEntity<?> getDetail(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            NewsEntity entity = getNews(id);
            Integer isHotNews = getInteger("isHotNews");
            if (isHotNews == null) {
                throw getException(1, "gia tri isHotNews khong tim thay");
            }
            entity.setIsHotNews(isHotNews);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "assign-to-menu news", notes = "{\"menuId\":1}")
    @PutMapping(path = "/assign-to-menu/{id}")
    public ResponseEntity<?> assignToMenu(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            NewsEntity entity = getNews(id);
            Long menuId = getLong("menuId");
            MenuEntity menuEntity = getMenu(menuId);
            entity.setMenu(menuEntity);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "admin hoi lao chuyen sang benh vien phoi", notes = "")
    @PutMapping(path = "/move-to-bvp/{id}")
    public ResponseEntity<?> moveToBvp(@PathVariable("id") long id) {
        try {
            init();
            NewsEntity entity = getNews(id);
            entity.setFromHoiLao(1);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "admin hoi lao tu choi chuyen sang benh vien phoi", notes = "")
    @PutMapping(path = "/reject-move-to-bvp/{id}")
    public ResponseEntity<?> rejectMoveToBvp(@PathVariable("id") long id) {
        try {
            init();
            NewsEntity entity = getNews(id);
            entity.setFromHoiLao(0);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "admin hoi lao dc chap nhan chuyen sang benh vien phoi", notes = "")
    @PutMapping(path = "/accept-move-to-bvp/{id}")
    public ResponseEntity<?> acceptMoveToBvp(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            NewsEntity entity = getNews(id);
            entity.setFromHoiLao(2);
            entity.setWebType(entity.getWebType() | WebType.BVP.getValue());
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "set-charity news", notes = "{\"charity\":1}")
    @PutMapping(path = "/set-charity/{id}")
    public ResponseEntity<?> setCharity(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer charity = getInteger("charity");
            if (charity == null) {
                throw getException(1, "gia tri isHotNews khong tim thay");
            }
            NewsEntity entity = getNews(id);
            entity.setCharity(charity);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "admin hoi lao huy viec chuyen sang benh vien phoi", notes = "")
    @PutMapping(path = "/cancel-move-to-bvp/{id}")
    public ResponseEntity<?> cancelMoveToBvp(@PathVariable("id") long id) {
        try {
            init();
            NewsEntity entity = getNews(id);
            entity.setFromHoiLao(null);
            entity.setWebType(WebType.HOILAO.getValue());
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    @ApiOperation(value = "set-high-light-noti ", notes = "{\"highlightNoti\":1}")
    @PutMapping(path = "/set-high-light-noti/{id}")
    public ResponseEntity<?> setHighLightNoti(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer highlightNoti = getInteger("highlightNoti");
            if (highlightNoti == null) {
                throw getException(1, "gia tri highlightNoti khong tim thay");
            }
            NewsEntity entity = getNews(id);
            entity.setHighlightNoti(highlightNoti);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Tao moi tin tuc
     *
     * @param object {"news":{"title":"Lê Đăng Tuấn","content":"Đại học bach khoa hà nội","imagePreview":"http://localhost.png","contentPreview":"Đây là nội dung content preview edited","keyword":"tieu duong, dau bung"}, "menuId":"dsfasdf"}
     * @return
     */
    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            NewsEntity entity = getObject("news", NewsEntity.class);
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
    public ResponseEntity<?> updateBlock(@RequestBody Object object) {
        try {
            init(object);
            String uid = getString("uid");
            String imagePreview = getString("imagePreview");
            Integer charity = getInteger("charity");
            NewsEntity news = newsRepository.findFirstByUid(uid);
            if (news != null) {
                if (imagePreview != null) {
                    news.setImagePreview(imagePreview);
                }
                news.setCharity(charity);
            }
            save(news);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-menu", notes = "")
    @PostMapping(path = "/update-menu")
    public ResponseEntity<?> updateFirstComment(@RequestBody Object object) {
        try {
            init(object);
            String menuUid = getString("menuUid");
            String newsUid = getString("newsUid");
            MenuEntity menu = menuRepository.findFirstByUid(menuUid);
            NewsEntity news = newsRepository.findFirstByUid(newsUid);
            if (menu == null || news == null) {
                return response(error(new Exception("post null or comment null")));
            }
            news.setMenu(menu);
            save(news);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
