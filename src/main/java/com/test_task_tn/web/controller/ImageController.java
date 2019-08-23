package com.test_task_tn.web.controller;

import com.test_task_tn.business.service.impl.ImageService;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Value
@RestController
@RequestMapping(value = "/image", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

    ImageService imageService;

    /**
     * Uploading images.
     * <p>
     * Before uploading you have to specify in application.property a correct directory for uploaded images
     *
     * @param image - the image
     * @return the ResponseEntity with HttpStatus and the String with
     */
    @PostMapping(value = "/upload")
    public ResponseEntity<String> upload(@RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.created(imageService.uploadImage(image)).build();
    }
}
