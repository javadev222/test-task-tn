package com.test_task_tn.business.service.impl;

import com.test_task_tn.application.ApplicationConfig;
import com.test_task_tn.shared.exceptions.NoContentImageException;
import com.test_task_tn.shared.exceptions.BadRequestImageException;
import com.test_task_tn.shared.exceptions.TypeException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
@Slf4j
@Value
public class ImageService {

    private static final String ERROR_TEMPLATE = "Can not upload file %s. Cause %s";
    ApplicationConfig applicationConfig;

    private String getExtension(String originalFilename) {
        return originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : null;
    }

    public URI uploadImage(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        if (image.isEmpty()) {
            log.error(String.format(ERROR_TEMPLATE, originalFilename, "file is empty"));
            throw new NoContentImageException();
        }
        String extension = getExtension(originalFilename);
        if (image.getContentType() != null && image.getContentType().startsWith("image/")) {
            File file = new File(applicationConfig.getLocation() + File.separator + UUID.randomUUID().toString() + extension);
            image.transferTo(file);
            return file.toURI();
        }
        log.error(String.format(ERROR_TEMPLATE, originalFilename, "type is unsupported"));
        throw new BadRequestImageException(image.getContentType(), TypeException.NO_IMAGE);
    }
}
