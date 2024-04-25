package com.personal.gadgetstore.service.impl;

import com.personal.gadgetstore.constants.ImageFileTypes;
import com.personal.gadgetstore.exception.BadApiRequestException;
import com.personal.gadgetstore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String filename = file.getOriginalFilename();
        assert filename != null;
        String extension = filename.substring(filename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID()
                .toString()
                .concat(extension);
        String fileNameWithpath = path + File.separator + uniqueFileName;
        logger.info("uploading file:{} convertedBackendFileName ", filename, fileNameWithpath);
        if (!ImageFileTypes.isFileTypeAllowed(extension))
            throw new BadApiRequestException("Invalid image " + "fileType: " + extension);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Files.copy(file.getInputStream(), Paths.get(fileNameWithpath));
        return uniqueFileName;
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullFileName = path + File.separator + name;
        InputStream inputStream=new FileInputStream(fullFileName);
        return inputStream;
    }
}
