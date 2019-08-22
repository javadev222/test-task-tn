package com.test_task_tn.business.service.impl;

import com.test_task_tn.shared.exceptions.NoContentImageException;
import com.test_task_tn.shared.exceptions.BadRequestImageException;
import com.test_task_tn.shared.exceptions.TypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    private static final String ERROR_TEMPLATE = "Can not upload file %s. Cause %s";

    private String getExtension(String originalFilename) {
        return originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : null;
    }

    public String uploadImage(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        if (image.isEmpty()) {
            log.error(String.format(ERROR_TEMPLATE, originalFilename, "file is empty"));
            throw new NoContentImageException();
        }
        String extension = getExtension(originalFilename);
        if (image.getContentType() != null && image.getContentType().startsWith("image/")) {
            File file = new File(UUID.randomUUID().toString() + extension);
            image.transferTo(file);
            return file.getName();
        }
        log.error(String.format(ERROR_TEMPLATE, originalFilename, "type is unsupported"));
        throw new BadRequestImageException(image.getContentType(), TypeException.NO_IMAGE);
    }
}
