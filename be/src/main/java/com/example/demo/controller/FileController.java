package com.example.demo.controller;

import com.example.demo.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private FileServiceImp fileServiceImp;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file){
        boolean isSuccess = fileServiceImp.uploadFile(file);
        if(isSuccess){
            return new ResponseEntity<>(file.getOriginalFilename(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Upload File Fail", HttpStatus.OK);
        }
    }

    @GetMapping("/load/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> loadFile(@PathVariable String filename) throws InterruptedException {
        Resource file = fileServiceImp.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
