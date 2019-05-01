package com.interswitchgroup.discoverpostinjectweb.service;

import com.interswitchgroup.discoverpostinjectweb.config.FileStorageProperties;
import com.interswitchgroup.discoverpostinjectweb.exception.FileStorageException;
import com.interswitchgroup.discoverpostinjectweb.exception.RequestNotValidException;
import com.interswitchgroup.discoverpostinjectweb.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;

    private Path targetLocation;

    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }

    public Path getTargetLocation() {
        return targetLocation;
    }

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("could not create the directory where the uploaded files will be stored", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence" + fileName);
            }
            if(!fileName.startsWith("INTERSO")) {
                throw new RequestNotValidException("Sorry! the file you are trying to upload is invalid");
            }
            String[] names = fileName.split(".");
            System.out.println(names);
            this.targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file" + fileName + ". Please try again");
        }

    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found" + fileName);
            }
        }catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found" + fileName, ex);
        }
    }
}
