package com.isofh.service.service;

import com.isofh.service.controller.ImageController;
import com.isofh.service.exception.FileStorageException;
import com.isofh.service.exception.MyFileNotFoundException;
import com.isofh.service.property.ImageStorageProperties;
import com.isofh.service.utils.FileUtils;
import com.isofh.service.utils.RandomUtils;
import com.isofh.service.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageStorageService {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);
    private final Path imageLocation;

    @Autowired
    public ImageStorageService(ImageStorageProperties imageStorageProperties) {
        this.imageLocation = Paths.get(imageStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            if (!imageLocation.toFile().exists())
                Files.createDirectories(this.imageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.imageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<String> storeImage(MultipartFile file) {
        log.info("file " + file.getOriginalFilename());
        List<String> images = new ArrayList<>();
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String fileExt = "";
        if (fileName.contains(".")) {
            fileExt = fileName.substring(fileName.lastIndexOf('.'));
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            fileName = StrUtils.convertToUnsignedLowerCase(fileName);
            fileName = StrUtils.removeSpecialCharactor(fileName);
        }

        log.info("file Ext " + fileExt);
        if (StrUtils.isNullOrWhiteSpace(fileExt)) {
            fileExt = fileExt + ".jpg";
        }
        log.info("file Ext " + fileExt);
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String image = fileName + "_" + RandomUtils.getRandomId() + fileExt;
            images.add(image);

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.imageLocation.resolve(image);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            try {
                String imageThumbnail = fileName + "_" + RandomUtils.getRandomId() + fileExt;
                File fileThumbnail = FileUtils.createFile(imageLocation.toString() + "/" + imageThumbnail);
                BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
                img.createGraphics().drawImage(ImageIO.read(this.imageLocation.resolve(image).toFile()).getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);
                ImageIO.write(img, "jpg", fileThumbnail);
                images.add(imageThumbnail);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return images;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
