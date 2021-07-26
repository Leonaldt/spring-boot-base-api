package br.com.baseapi.domain.service.impl;

import br.com.baseapi.core.properties.FileStorageProperties;
import br.com.baseapi.domain.exceptions.FileStorageException;
import br.com.baseapi.domain.service.FileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${spring.application.name}")
    private String projectName;

    @Value("${file.upload-dir}")
    private String fileDirectory;

    private final Path fileStorageLocation;

    private static final String INVALID_PATH_MESSAGE = "Sorry! Filename contains invalid path sequence ";
    private static final String EXCEPTION_MESSAGE = "Could not store file ";
    private static final String TRY_AGAIN_MESSAGE = ". Please try again!";

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String fileNamePrefix) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName =
                StringUtils.cleanPath(projectName + "_" + fileNamePrefix + "_" + UUID.randomUUID().toString() + "." + extension);
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException(INVALID_PATH_MESSAGE + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException(EXCEPTION_MESSAGE + fileName + TRY_AGAIN_MESSAGE, ex);
        }
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        String filePath = fileDirectory + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            Path path = Paths.get(filePath);
            Files.delete(path);
        }
    }
}
