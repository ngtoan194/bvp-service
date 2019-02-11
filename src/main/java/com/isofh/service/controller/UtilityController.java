package com.isofh.service.controller;

import com.isofh.service.model.EmailEntity;
import com.isofh.service.service.UltilityRepository;
import com.isofh.service.utils.EmailUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/utility")
public class UtilityController extends BaseController {

    @Autowired
    UltilityRepository ultilityRepository;

    @ApiOperation(value = "", notes = "{}")
    @GetMapping(path = "/get")
    public ResponseEntity<?> get() {
        try {
            init();
            ultilityRepository.findAll();
            return response(ok());
        } catch (Exception ex) {
            save(new EmailEntity("ledangtuanbk@gmail.com,mainam.ctk33@gmail.com,lenhu1188@gmail.com", "Server bvdhy new test loi", "Server bvdhy new test loi"));
            return response(error(ex));
        }
    }

    @ApiOperation(value = "", notes = "{}")
    @GetMapping(path = "/send-email")
    public ResponseEntity<?> sendEmail() {
        try {
            init();
            synchronized(this) {
                List<EmailEntity> emails = emailRepository.findEmail(0);
                for (EmailEntity entity : emails) {
                    try {
                        EmailUtils.sendEmail(entity);
                        entity.setSent(1);
                        save(entity);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        entity.setResult(ex.getMessage());
                        save(entity);
                    }
                }
            }
            return response(ok());
        } catch (Exception ex) {
            save(new EmailEntity("ledangtuanbk@gmail.com,mainam.ctk33@gmail.com,lenhu1188@gmail.com", "Server bvdhy new test loi", "Server bvdhy new test loi"));
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update email", notes = "{\"email\":{\"content\":\"content update\",\"title\":\"la la la\",\"emails\":\"kkelangthang12345@gmail.com\"}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateEmail(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            EmailEntity data = getObject("email", EmailEntity.class);
            EmailEntity entity = getEmail(id);
            entity.setContent(data.getContent());
            entity.setEmails(data.getEmails());
            entity.setTitle(data.getTitle());
            entity.setSent(data.getSent());
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
