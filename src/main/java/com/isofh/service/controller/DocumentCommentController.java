package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.DocumentCommentEntity;
import com.isofh.service.model.DocumentEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/document-comment")
public class DocumentCommentController extends BaseController {

    public DocumentCommentController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<DocumentCommentEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (DocumentCommentEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(DocumentCommentEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(COMMENT, entity);
        mapData.put(AUTHOR, entity.getAuthor());
        return mapData;
    }

    /**
     * tim kiem giao luu truc tuyen
     *
     * @return
     */
    @ApiOperation(value = "Search document comment", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "documentId", required = false, defaultValue = DefaultConst.NUMBER) Long documentId
    ) {
        try {
            init();
            Page<DocumentCommentEntity> results = documentCommentRepository.search(documentId, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));

        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * comment trong document
     *
     * @param object {"documentId":1, "documentComment":{"content":"cau hoi"}}
     * @return
     */
    @ApiOperation(value = "comment in document", notes = "{\"documentId\":1, \"documentComment\":{\"content\":\"cau hoi\"}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            DocumentCommentEntity documentComment = getObject("documentComment", DocumentCommentEntity.class);
            Long documentId = getLong("documentId");
            DocumentEntity document = getDocument(documentId);
            if (document != null) {
                documentComment.setDocument(document);
            }
            documentComment.setAuthor(getCurrentUser());
            save(documentComment);
            return response(ok(getMapData(documentComment)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Update comment
     *
     * @param object {"documentComment":{"content":"cau hoi"}}
     * @return
     */
    @ApiOperation(value = "Update comment", notes = "{\"documentComment\":{\"content\":\"cau hoi\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            DocumentCommentEntity data = getObject("documentComment", DocumentCommentEntity.class);
            DocumentCommentEntity entity = getDocumentComment(id);
            entity.setContent(data.getContent());
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Xoa comemnt
     *
     * @return
     */
    @ApiOperation(value = "Delete comment", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            documentCommentRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
