package com.example.demo.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    boolean uploadFile(MultipartFile file);
    Resource loadFile(String filename) throws InterruptedException;
}
