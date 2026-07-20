package com.forensys.backend.service.impl;

import com.forensys.backend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        String orig = file.getOriginalFilename();
        String originalFileName = StringUtils.cleanPath(orig != null ? orig : "file");
        String fileExtension = "";
        
        if (originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        
        // Generate a unique filename to prevent overwriting
        String fileName = UUID.randomUUID().toString() + fileExtension;

        try {
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName; // return relative path
        } catch (IOException ex) {
            throw new IOException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        Path targetLocation = this.fileStorageLocation.resolve(filePath).normalize();
        Files.deleteIfExists(targetLocation);
    }
}
