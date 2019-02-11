package com.isofh.service.controller;

import com.isofh.service.model.ResultEntity;
import com.isofh.service.service.ImageStorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
@EnableSwagger2
public class ImageController extends BaseController {

    @Autowired
    private ImageStorageService imageStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            init();
            log.info("upload " + file.getName());
            List<String> images = imageStorageService.storeImage(file);

            String imageLocation = images.get(0);
            String thumbnailLocation = images.get(images.size() > 1 ? 1 : 0);

            log.info("imageLocation " + imageLocation);
            log.info("thumbnailLocation " + thumbnailLocation);
            Map<String, Object> map = new HashMap<>();
            map.put("image", "/new_images/" + imageLocation);
            map.put("thumbnail", "/new_images/" + thumbnailLocation);

            ResultEntity resultEntity = ok("images", Collections.singletonList(map));
            return response(resultEntity);
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<byte[]> view(@PathVariable String fileName, HttpServletRequest request) {
        try {
            init();
            log.info("view " + fileName);
            Resource resource = imageStorageService.loadFileAsResource(fileName);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(IOUtils.toByteArray(resource.getInputStream()), headers, HttpStatus.OK);
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
