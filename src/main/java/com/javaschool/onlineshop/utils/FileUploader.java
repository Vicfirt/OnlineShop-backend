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

    private static String PATH;

    @Value("${upload.path}")
    public void setPATH(String PATH) {
        FileUploader.PATH = PATH;
    }

    /**
     * This method tries to transfer created image in specified directory.
     * @param multipartFile             file received from the form
     */
    public static String uploadFile(MultipartFile multipartFile) {
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "." + multipartFile.getOriginalFilename();
        try {
            multipartFile.transferTo(new File(PATH + "/" + fileName));
            return fileName;
        } catch (IOException e) {
            throw new FileTransferException("Error during file transfer. File name: " + fileName);
        }
    }
}
