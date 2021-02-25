package com.javaschool.onlineshop.utils;

import com.javaschool.onlineshop.exception.FileTransferException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * This class is responsible for loading an image by a user.
 */
@Component
public class FileUploader {

    @Value("${upload.path}")
    private String path;

    /**
     * This method tries to transfer created image in specified directory.
     *
     * @param multipartFile file received from the form
     */
    public String uploadFile(MultipartFile multipartFile) {
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "." + multipartFile.getOriginalFilename();
        try {
            multipartFile.transferTo(new File(path + "/" + fileName));
            return fileName;
        } catch (IOException e) {
            throw new FileTransferException("Error during file transfer. File name: " + fileName);
        }
    }
}
