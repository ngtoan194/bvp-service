package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.TagEntity;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/tag")
public class TagController extends BaseController {

    public TagController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<TagEntity> tags) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (TagEntity tag : tags) {
            mapResults.add(getMapData(tag));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(TagEntity tags) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(TAG, tags);
        return mapData;
    }

    /**
     * Tao moi tin tuc
     *
     * @param object {"tags":["tag1", "tag2", "tag3"]}
     * @return
     */
    @ApiOperation(value = "Create tags", notes = "{\"tags\":[\"tag1\", \"tag2\", \"tag3\"]}")
    @PostMapping(path = "/create-multiple")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            JSONArray tagNames = getJSONArray("tags");
            List<TagEntity> tags = new ArrayList<>();
            List<TagEntity> tagExisted = new ArrayList<>();
            for (int i = 0; i < tagNames.length(); i++) {
                String value = tagNames.getString(i);
                List<TagEntity> results = tagRepository.checkExistedTagNames(value);
                TagEntity tag = new TagEntity();
                if (results.size() != 1) {
                    tag.setName(value);
                    tag.setLinkAlias(getTextUrl(value));
                    tags.add(tag);
                } else {
                    tag.setName(value);
                    tagExisted.add(tag);
                }
            }
            tagRepository.saveAll(tags);
            return response(ok(Arrays.asList("tags", "tagExisted"), Arrays.asList(tags, tagExisted)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search tags", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "linkAlias", required = false, defaultValue = DefaultConst.STRING) String linkAlias
    ) {
        try {
            init();
            Page<TagEntity> results = tagRepository.search(name, linkAlias, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), results.getContent()));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update tag", notes = "{\"name\":\"new name\"}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            String name = getString("name");
            TagEntity entity = getTag(id);
            entity.setLinkAlias(getTextUrl(name));
            entity.setName(name);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete tag", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            tagRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search tags", notes = "")
    @GetMapping(path = "/get-all")
    public ResponseEntity<?> search() {
        try {
            init();
            Iterable<TagEntity> results = tagRepository.findAll();
            return response(ok(getMapDatas(results)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

//    @ApiOperation(value = "Search hot tags", notes = "")
//    @GetMapping(path = "/get-hot-tag")
//    public ResponseEntity<?> hotTagSearch(
//            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
//            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size
//    ) {
//        try {
//            init();
//            Page<TagEntity> results = tagRepository.search(name, getDefaultPage(page, size));
//            return response(ok(results.getTotalElements(), results.getContent()));
//        } catch (Exception ex) {
//            return response(error(ex));
//        }
//    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            TagEntity entity = getObject("tag", TagEntity.class);
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
