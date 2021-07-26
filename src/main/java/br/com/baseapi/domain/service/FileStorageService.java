package br.com.baseapi.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String storeFile(MultipartFile file, String fileNamePrefix);
    void deleteFile(String fileName) throws IOException;
}
