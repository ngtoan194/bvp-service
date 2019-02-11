package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.model.ResultEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            init();
            String fileName ="/file/view/" + fileStorageService.storeFile(file);
            ResultEntity resultEntity = ok("file", fileName);
            return response(resultEntity);
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String fileName, HttpServletRequest request,
            @RequestParam(value = "download", required = false, defaultValue = DefaultConst.NUMBER) Integer download

    ) {
        // Load file as Resource
        init();
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

//         Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        ResponseEntity.BodyBuilder builder = ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType));
        if (download == 1) {
            builder = builder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        }
        return builder.body(resource);
    }
}
