package com.example.demo.service.imp;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Semaphore;

@Service
public class FileServiceImp implements FileService {
    @Value("${path.upload.file}")
    private String folderRoot;

    private Path root;
    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public boolean uploadFile(MultipartFile file) {
        boolean isSuccess = false;
        try{
            root = Paths.get(folderRoot);
            if(!Files.exists(root)){
                Files.createDirectories(root);
            }
            Files.copy(file.getInputStream(),root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isSuccess = true;
        }catch (Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public Resource loadFile(String filename) throws InterruptedException {
        semaphore.acquire();
        try{
            root = Paths.get(folderRoot).resolve(filename);
            Resource resource = new UrlResource(root.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Could not read the file!");
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }finally{
            semaphore.release();
        }
    }
}
